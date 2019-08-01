import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.print.DocFlavor.URL;

public class Board {

	private Cell[][] board;

	/*
	 * Constructs the board for the game. Loads the board from map layout.txt Adds
	 * players to the board
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
					
				} else { // floor tile
					FloorCell fc = new FloorCell(loc, token.charAt(0));
					board[x][y] = fc;
				}
				// Adds the fact that a cell is a room if it is
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
		addWeapons();
	}

	/*
	 * adds players to the board
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
			realPlayers.get(i).setLocation(playerLocs.get(i));
		}
		for (int i = realPlayers.size(); i < realPlayers.size() + nonPlayer.size(); i++) {
			board[playerLocs.get(i).getX()][playerLocs.get(i).getY()].setPlayer(nonPlayer.get(i-realPlayers.size()));
			nonPlayer.get(i-realPlayers.size()).setLocation(playerLocs.get(i));
		}
	}

	/*
	 * Adds weapons onto the board
	 */
	private void addWeapons() {
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
	
	/*
	 * If the cell is a room tile adds the room name
	 */
	private void addRoom(String token, Cell cell) {
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
	}

	/*
	 * displays the board to text output
	 */
	public void displayBoard() {
		System.out.println("\n\n\n\n");
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				System.out.print(board[x][y].toString());
			}
			System.out.println();
		}
	}

	/*
	 * Moves a player one place on the board up, down, left, right
	 */
	public int movePlayer(Location locAt, String direction, int movesLeft) {
		if(movesLeft==0)
			throw new RuntimeException("Player has no moves left");
		int x = locAt.getX();
		int y = locAt.getY();
		if (direction.compareToIgnoreCase("w") == 0)
			y -= 1;
		if (direction.compareToIgnoreCase("a") == 0)
			x -= 1;
		if (direction.compareToIgnoreCase("s") == 0)
			y += 1;
		if (direction.compareToIgnoreCase("d") == 0)
			x += 1;
		Location locTo = new Location(x, y);
		// if not valid return with same number of turns left - redo their turn
		if (!isValidMove(locAt, locTo))
			return movesLeft;
		// move player, add visited locations
		Player p = getPlayerAt(locAt);
		p.addVisitedLocation(locAt);
		getCellAt(locAt).removePlayer();
		getCellAt(locTo).setPlayer(p);
		p.setLocation(locTo);
		if(getCellAt(locTo) instanceof DoorCell) //if made it into a room
			return 0;
		return --movesLeft;
	}

	/*
	 * Checks if its valid to move a player in the direction
	 */
	private boolean isValidMove(Location at, Location to) {
		int x = to.getX();
		int y = to.getY();
		// Checks error bounds
		if (x < 0 || x >= 24 || y < 0 || y >= 24) {
			System.out.println("Invalid move - Out of bounds: Retry again");
			return false;
		}

		// checks that player hasnt moved to that cell this turn
		List<Location> prevsLocs = getPlayerAt(at).getVisitedLocations();
		if(!prevsLocs.isEmpty())
		for (Location loc : prevsLocs) {
			if (loc.equals(to)) {
				System.out.println("Invalid move - Already visited tile this turn: Retry again");
				return false;
			}
		}
		Cell moveTo = getCellAt(to);
		Cell from = getCellAt(at);
		// checks that there is not already a player or weapon on that tile
		if (moveTo.hasPlayer() || moveTo.hasWeapon()) {
			System.out.println("Invalid move - Player already on that tile: Retry again");
			return false;
		}
		// checks it's not an empty tile
		if (moveTo.toString().compareToIgnoreCase("e") == 0) {
			System.out.println("Invalid move - Cannot move to an empty tile(e): Retry again");
			return false;
		}

		// checks that either both locations are hallways, or room cell or at least one
		// is a door
		// note cannot move from hallway cell to a room cell - has to transition trough
		// a door cell
		if (moveTo.getId()!=from.getId() && !(moveTo instanceof DoorCell)
				&& !(from instanceof DoorCell)) {
			System.out.println("Invalid move - Cannot move through a wall: Retry again");
			return false;
		}
		// checks if tile has a weapon on it
		if (moveTo.hasWeapon()) {
			System.out.println("Invalid move - Cannot move onto a weapon: Retry again");
			return false;
		}
		//Checking that if moving into a door cell, that its from the right direction
		if(moveTo instanceof DoorCell&& !from.isRoom()) {
			if(!(((DoorCell) moveTo).getEntryLoc().equals(from.getLocation()))){
				System.out.println("Invalid move - Cannot move to a door from this direction: Retry again");
				return false;
			}
		}
		if(from instanceof DoorCell&& !moveTo.isRoom()) {
			if(!(((DoorCell) from).getEntryLoc().equals(moveTo.getLocation()))){
				System.out.println("Invalid move - Cannot move from a door to that tile from this direction: Retry again");
				return false;
			}
		}	
		return true;
	}
	
	/*
	 * Moves a player and to a room after having been in a suggestion
	 */
	public void movePlayerWeaponToRoom(Player p, String roomNameTo, String weaponName) {
		List<Cell> cells = getCellsFromRoom(roomNameTo);
		Collections.shuffle(cells);
		//moving player if nessicary
		if(!getCellAt(p.getLocation()).isRoom() ||!getCellAt(p.getLocation()).getRoom().equalsIgnoreCase(roomNameTo) ) {
			for(Cell cell: cells)
				if(!cell.hasPlayer() && !cell.hasWeapon() && !(cell instanceof DoorCell)) {
					getCellAt(p.getLocation()).removePlayer();
					cell.setPlayer(p);
					p.setLocation(cell.getLocation());
					break;
				}
		}
		
		if(weaponName.equals("Candlestick"))
			weaponName="Ca";
		else if(weaponName.equals("Dagger"))
			weaponName="Dg";
		else if(weaponName.equals("Lead Pipe"))
			weaponName="Lp";
		else if(weaponName.equals("Revolver"))
			weaponName="Rv";
		else if(weaponName.equals("Rope"))
			weaponName="Rp";
		else if(weaponName.equals("Spanner"))
			weaponName="Sp";
		//Moving weapon if nessicary
		Cell weaponLoc = getCellAt(weaponLocation(weaponName));
		if(!weaponLoc.isRoom() || !weaponLoc.getRoom().equals(roomNameTo)) {
			for(Cell cell: cells)
				if(!cell.hasPlayer() && !cell.hasWeapon() && !(cell instanceof DoorCell)) {
					getCellAt(weaponLoc.getLocation()).removeWeapon();
					cell.setWeapon(weaponName);
					break;
				}
		}	
	}
	
	/*
	 * Gets all the cells that are within the given room
	 */
	private List<Cell> getCellsFromRoom(String roomName){
		List<Cell> cells = new ArrayList<Cell>();
		
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				if(board[x][y].isRoom()&& board[x][y].getRoom().equals(roomName))
					cells.add(board[x][y]);
			}
		}
		return cells;
	}
	
	

	/// HELPER METHODS\\\
	/*
	 * Gets player at location on the board
	 */
	private Player getPlayerAt(Location loc) {
		Player p = board[loc.getX()][loc.getY()].getPlayer();
		return p;
	}
	
	private Location weaponLocation(String name){
		for (int y = 0; y < 25; y++) {
			for (int x = 0; x < 24; x++) {
				if(board[x][y].hasWeapon() && board[x][y].getWeapon().equals(name))
					return board[x][y].getLocation();
			}
		}
		throw new RuntimeException("Weapon not found on the board");
	}

	/*
	 * Returns a cell at the Location, x,y
	 */
	public Cell getCellAt(Location loc) {
		return board[loc.getX()][loc.getY()];
	}
	/*
	 * Clears the console
	 */
	private static void clearScreen() {  
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	   }

}