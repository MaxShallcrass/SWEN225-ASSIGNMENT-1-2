import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.print.DocFlavor.URL;
import javax.swing.JPanel;

/**
 * Contains everything for displaying and making the game board and is in
 * control of all movements on the board
 * 
 * Movements and changes in the board are all done through location objects
 */
public class Board extends JPanel {
	private Cell[][] board; // data structure for containing all the board information
	
	//pathfinding
	private ArrayList<Integer> pathsFound= new ArrayList<Integer>();//Stores distances for each path found
	private Stack<Cell> pathway = new Stack<Cell>();
	private ArrayList<Cell> bestPathway = new ArrayList<Cell>();
	private int shortestPathLength;
	/**
	 * Constructs the board for the game. Loads the board from GameBoard.txt Adds
	 * players to the board Adds weapons to the board
	 * 
	 * @param realPlayers
	 * @param nonPlayer
	 */
	public Board(List<Player> realPlayers, List<Player> nonPlayer) {
		// Placing all the cells on the board
		board = new Cell[24][25];
		try {
			java.net.URL fileUrl = getClass().getResource("/GameBoard.txt");
			File file = new File(fileUrl.getFile());

			Scanner sc = new Scanner(file);
			int x = 0, y = 0;
			while (sc.hasNext()) { // scans through board txt file
				String token = sc.next();
				Location loc = new Location(x, y);
				if (token.length() > 1) { // A door
					DoorCell dc = new DoorCell(loc, token);
					board[x][y] = dc;
					token = token.substring(2);
				} else { // floor tile
					FloorCell fc = new FloorCell(loc, token.charAt(0));
					board[x][y] = fc;
				}
				// Adds the fact that a cell is in a room if it is
				addRoom(token, board[x][y]);
				if (++x == 24) {
					x = 0;
					y++;
				}
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		// adding players and weapons to the board
		addPlayers(realPlayers, nonPlayer);
	//	addWeapons();
	}

	/**
	 * Adds players/characters to the board to their allocated positions Includes
	 * real and non real players
	 * 
	 * @param realPlayers
	 * @param nonPlayer
	 */
	private void addPlayers(List<Player> realPlayers, List<Player> nonPlayer) {
		// Starter locations for players
		List<Location> playerLocs = new ArrayList<Location>();
		playerLocs.add(new Location(9, 0));
		playerLocs.add(new Location(14, 0));
		playerLocs.add(new Location(23, 6));
		playerLocs.add(new Location(23, 19));
		playerLocs.add(new Location(7, 24));
		playerLocs.add(new Location(0, 17));

		// Placing the players on the board
		for (int i = 0; i < realPlayers.size(); i++) {
			board[playerLocs.get(i).getX()][playerLocs.get(i).getY()].setPlayer(realPlayers.get(i));
			realPlayers.get(i).setLoc(playerLocs.get(i));
		}
		for (int i = realPlayers.size(); i < realPlayers.size() + nonPlayer.size(); i++) {
			board[playerLocs.get(i).getX()][playerLocs.get(i).getY()].setPlayer(nonPlayer.get(i - realPlayers.size()));
			nonPlayer.get(i - realPlayers.size()).setLoc(playerLocs.get(i));
		}
	}

	/**
	 * Adds weapons to randomly allocated rooms
	 * 
	 * Note - Locations for weapons have been set to back corners of each of the rooms
	 * to avoid player movement problems
	 */
	void addWeapons() {
		// locations for weapons - one for each room
		List<Location> wepLocations = new ArrayList<Location>() {
			{
				add(new Location(0, 1));
				add(new Location(0, 12));
				add(new Location(0, 24));
				add(new Location(12, 1));
				add(new Location(23, 1));
				add(new Location(23, 9));
				add(new Location(23, 15));
				add(new Location(23, 24));
				add(new Location(12, 24));
			}
		};
		// placing weapons on the board - random
		Collections.shuffle(wepLocations);
		Location loc = wepLocations.remove(0);
		getCellAt(loc).setWeapon("Ca");
		loc = wepLocations.remove(0);
		getCellAt(loc).setWeapon("Dg");
		loc = wepLocations.remove(0);
		getCellAt(loc).setWeapon("Lp");
		loc = wepLocations.remove(0);
		getCellAt(loc).setWeapon("Rv");
		loc = wepLocations.remove(0);
		getCellAt(loc).setWeapon("Rp");
		loc = wepLocations.remove(0);
		getCellAt(loc).setWeapon("Sp");
	}
	
	/**
	 * Makes a cell a room tile if it is It is based on the token that the cell is
	 * given on the text file
	 * 
	 * Includes doorways
	 * 
	 * @param token - Which room if it is
	 * @param cell
	 */
	private void addRoom(String token, Cell cell) {
		// Allocates room name to tiles
		if (token.equals("k")) {
			cell.setRoom("Kitchen");

		} else if (token.equals("b")) {
			cell.setRoom("Ball Room");

		} else if (token.equals("c")) {
			cell.setRoom("Conservatory");

		} else if (token.equals("B")) {
			cell.setRoom("Billiard Room");

		} else if (token.equals("l")) {
			cell.setRoom("Library");

		} else if (token.equals("s")) {
			cell.setRoom("Study");

		} else if (token.equals("h")) {
			cell.setRoom("Hall");

		} else if (token.equals("L")) {
			cell.setRoom("Lounge");

		} else if (token.equals("d")) {
			cell.setRoom("Dining Room");
		}
		// else - Not a room, either hallway tile(#) or empty tile(!)
	}

	/**
	 * Displays board as text output
	 * 
	 * All based off cell.toString() in which takes into account if the cell
	 * contains a weapon or player
	 */
	public void displayBoard() {
		System.out.println();
		System.out.println("************************************************************************");
		System.out.println("");
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				System.out.print(board[x][y].toString());
			}
			System.out.println();
		}
		System.out.println("");
		System.out.println("************************************************************************");
		System.out.println("");
	}
	
	
	/**
	 * Moves a player multiple steps if possible
	 * @param locAt
	 * @param locTo
	 * @param movesLeft
	 */
	public ArrayList<Cell> movePlayerMany(Location locAt, Location locTo, int movesLeft){
		shortestPathLength=Integer.MAX_VALUE;
		pathway.clear();
		bestPathway.clear();
		
		exploreCellAll(getCellAt(locAt), locTo, movesLeft);
		
		if(bestPathway.isEmpty() || bestPathway.size()>movesLeft)
			return null;
		
		return bestPathway;
	}
	
	

	/**
	 * Moves a player at Location locAt on the board one cell over in the direction
	 * w,a,s or d if it is a valid move
	 * 
	 * @param locAt     - position player is at
	 * @param direction - direction moving to
	 * @param movesLeft - amount of moves the player has left return 0 if player
	 *                  enters a doorway/room - may need to be
	 *                  changed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @return
	 */
	public int movePlayer(Location locAt, Location locTo) {
		
	//	if (direction.compareToIgnoreCase("w") == 0)
	//		y -= 1;
	//	else if (direction.compareToIgnoreCase("a") == 0)
	//		x -= 1;
	//	else if (direction.compareToIgnoreCase("s") == 0)
	//		y += 1;
		//else if (direction.compareToIgnoreCase("d") == 0)
		//	x += 1;
		//else
		//	throw new RuntimeException("Invalid input for a move - Game Crashed"); // Should never reach this
		// if not valid return with same number of turns left - Redo their turn and
		// displays bad move message
		if (!isValidMove(locAt, locTo))
			return -1;
		// move player, add visited locations
		Player p = getPlayerAt(locAt);
		p.addVisitedLocation(locAt);
		getCellAt(locAt).removePlayer();
		getCellAt(locTo).setPlayer(p);
		p.setLoc(locTo);
		if (getCellAt(locTo) instanceof DoorCell) // if made it into a room stop player
			if (!p.getLastSuggested().equals(getCellAt(locTo).getRoom())) {
				return 0;
			}
		//displayBoard();
		return -1;
	}

	/**
	 * Checks if it valid for a player to move from Location at to Location to
	 * Moves it checks for:
	 * -Out of bounds
	 * -Already visited tiles
	 * -Tiles that have a player or weapon on it
	 * -Moving to empty tiles
	 * -Moving through the "invisible" walls
	 * -Correct door movement - Can only move in and out of from one tile
	 * @param at Location player is at
	 * @param to Location player is to move to
	 * @return true if valid - false if not
	 */
	private boolean isValidMove(Location at, Location to) {
		int x = to.getX();
		int y = to.getY();
		// Checks error bounds
		if (x < 0 || x >= 24 || y < 0 || y >= 25) {
		//	System.out.println("Invalid move - Out of bounds: Retry again");
			return false;
		}

		// checks that player hasn't moved to that cell this turn
		if(getCellAt(at).hasPlayer()) {
		List<Location> prevsLocs = getPlayerAt(at).getVisitedLocations();
		if (!prevsLocs.isEmpty())
			for (Location loc : prevsLocs) {
				if (loc.equals(to)) {
				//	System.out.println("Invalid move - Already visited tile this turn: Retry again");
					return false;
				}
			}}
		Cell moveTo = getCellAt(to);
		Cell from = getCellAt(at);
		// checks that there is not already a player
		if (moveTo.hasPlayer()) {
			//System.out.println("Invalid move - Player already on that tile: Retry again");
			return false;
		}
		// checks it's not an empty tile
		if (moveTo.toString().compareToIgnoreCase("e") == 0) {
		//	System.out.println("Invalid move - Cannot move to an empty tile(e): Retry again");
			return false;
		}

		// checks that either both locations are hallways, or room cell or at least one
		// is a door
		// note cannot move from hallway cell to a room cell - has to transition trough
		// a door cell
		if (moveTo.getId() != from.getId() && !(moveTo instanceof DoorCell) && !(from instanceof DoorCell)) {
			//System.out.println("Invalid move - Cannot move through a wall: Retry again");
			return false;
		}
		// checks if tile has a weapon on it
		if (moveTo.hasWeapon()) {
			//System.out.println("Invalid move - Cannot move onto a weapon: Retry again");
			return false;
		}
		// Checking that if moving into a door cell, that its from the right direction
		if (moveTo instanceof DoorCell && !from.isRoom()) {
			if (!(((DoorCell) moveTo).getEntryLoc().equals(from.getLoc()))) {
			//	System.out.println("Invalid move - Cannot move to a door from this direction: Retry again");
				return false;
			}
		}
		if (from instanceof DoorCell && !moveTo.isRoom()) {
			if (!(((DoorCell) from).getEntryLoc().equals(moveTo.getLoc()))) {
				//System.out.println(
					//	"Invalid move - Cannot move from a door to that tile from this direction: Retry again");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if a path to find to a cell is a valid move. It finds the shortest
	 * path from the players location to the Location to. This checks if the path is
	 * less than the amount of turns a player has left 
	 * 
	 * Returns null if not valid,
	 * Returns a list of cells on the path if valid
	 */
	public ArrayList<Cell> isValidBigMove(Player p, Location to, int playersTurns){
		pathway.clear();
		bestPathway.clear();
		exploreCellAll(getCellAt(p.getLoc()), to, playersTurns);
		if(bestPathway.isEmpty() || bestPathway.size()>playersTurns)
			return null;
		
		return bestPathway;
	}
	
	/**
	 * Searches for all paths from a cell If it reaches the goal cell then we have
	 * found a complete path and adds the number of steps to an arrayList
	 * 
	 * Breath first search algorithm
	 * @param cell
	 */
	public void exploreCellAll(Cell current, Location goal, int longestAllowedLength) {
		pathway.push(current);
		if(pathway.size() <= longestAllowedLength+1)
		if( current.getLoc().equals(goal)) { //reached goal cell
			if(pathway.size()<shortestPathLength) { //Found smaller pathway
				bestPathway.clear();
				bestPathway.addAll(pathway);
				System.out.println("new best path length: " +bestPathway.size());
				shortestPathLength=bestPathway.size();
			}
		}else {
			current.setIsVisited(true);
			Location currentLoc = current.getLoc();
			int currentX=currentLoc.getX();
			int currentY=currentLoc.getY();
			Location down = new Location(currentX, currentY-1);
			Location up = new Location(currentX, currentY+1);
			Location left = new Location(currentX-1, currentY);
			Location right = new Location(currentX+1, currentY);
			//x < 0 || x >= 24 || y < 0 || y >= 24
			//Visits surrounding cells
			if(currentY-1 >-1 && currentY-1 < 25 && !getCellAt(down).isVisited() && isValidMove(currentLoc, down)) exploreCellAll(getCellAt(down), goal,longestAllowedLength);
			if(currentY+1 >-1 && currentY+1 < 25 && !getCellAt(up).isVisited() && isValidMove(currentLoc, up)) exploreCellAll(getCellAt(up), goal,longestAllowedLength);
			if(currentX-1 > -1 && currentX-1< 24 && !getCellAt(left).isVisited() &&isValidMove(currentLoc, left)) exploreCellAll(getCellAt(left), goal,longestAllowedLength);
			if(currentX+1 > -1 && currentX+1< 24 &&!getCellAt(right).isVisited()&&isValidMove(currentLoc, right)) exploreCellAll(getCellAt(right), goal,longestAllowedLength);
		}
		current.setIsVisited(false);
		pathway.pop();
	}
	
	
	
	/**
	 * Moves character and weapon to a random room cell that is in the room -
	 * roomNameTo
	 * 
	 * Moves only if they are not already located in that room Does this by: 
	 * Getting all the cells of the specified room(not including door cells) 
	 * and randomly picks one for each of player and weapon to move to
	 * 
	 * @param p          Player/character to move
	 * @param weaponName String name of weapon to move
	 * @param roomNameTo String of room name to move to
	 */
	public void movePlayerWeaponToRoom(Player p, String roomNameTo, String weaponName) {
		List<Cell> cells = getCellsFromRoom(roomNameTo);
		Collections.shuffle(cells);
		// moving player if necessary
		if (!getCellAt(p.getLoc()).isRoom()
				|| !getCellAt(p.getLoc()).getRoom().equalsIgnoreCase(roomNameTo)) {
			for (Cell cell : cells)
				if (!cell.hasPlayer() && !cell.hasWeapon() && !(cell instanceof DoorCell)) {
					getCellAt(p.getLoc()).removePlayer();
					cell.setPlayer(p);
					p.setLoc(cell.getLoc());
					break;
				}
		}
		if (weaponName.equals("Candlestick"))
			weaponName = "Ca";
		else if (weaponName.equals("Dagger"))
			weaponName = "Dg";
		else if (weaponName.equals("Lead Pipe"))
			weaponName = "Lp";
		else if (weaponName.equals("Revolver"))
			weaponName = "Rv";
		else if (weaponName.equals("Rope"))
			weaponName = "Rp";
		else if (weaponName.equals("Spanner"))
			weaponName = "Sp";
		// Moving weapon if necessary
		Cell weaponLoc = getCellAt(weaponLocation(weaponName));
		if (!weaponLoc.isRoom() || !weaponLoc.getRoom().equals(roomNameTo)) {
			for (Cell cell : cells)
				if (!cell.hasPlayer() && !cell.hasWeapon() && !(cell instanceof DoorCell)) {
					getCellAt(weaponLoc.getLoc()).removeWeapon();
					cell.setWeapon(weaponName);
					break;
				}
		}
	}

	/// HELPER METHODS\\\

	/**
	 * Gets all cells for specified room - roomName
	 * 
	 * @param roomName - Name of cells in that room
	 * @return
	 */
	private List<Cell> getCellsFromRoom(String roomName) {
		List<Cell> cells = new ArrayList<Cell>();

		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				if (board[x][y].isRoom() && board[x][y].getRoom().equals(roomName))
					cells.add(board[x][y]);
			}
		}
		return cells;
	}

	/**
	 * Gets player at specified location
	 * 
	 * @param loc
	 * @return
	 */
	private Player getPlayerAt(Location loc) {
		Player p = board[loc.getX()][loc.getY()].getPlayer();
		return p;
	}

	/**
	 * Gets the Location of where a weapon is
	 * 
	 * @param name - name of weapon
	 * @return Location of weapon
	 */
	private Location weaponLocation(String name) {
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				if (board[x][y].hasWeapon() && board[x][y].getWeapon().equals(name))
					return board[x][y].getLoc();
			}
		}
		throw new RuntimeException("Weapon not found on the board");
	}

	/**
	 * Gets cell at Location loc
	 * 
	 * @param loc - Location
	 * @return Cell
	 */
	public Cell getCellAt(Location loc) {
		return board[loc.getX()][loc.getY()];
	}
}