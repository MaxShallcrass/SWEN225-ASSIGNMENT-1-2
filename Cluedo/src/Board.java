

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Associations
  private Cluedo cluedo;
  private List<Player> players;
  private List<Cell> cells;
  private Cell[][] board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(List<Player> realPlayers, List<Player> nonPlayer){
	  //Placing all the cells on the board
	  board= new Cell[24][25];
	  try {
		Scanner sc= new Scanner(new File("map layout.txt"));
		
		int x=0, y=0;
		while(sc.hasNext()) { //scans through board txt file
		String token=sc.next();
		Location loc=new Location(x++, y++);
		if(token.length()>1) { // A door
			DoorCell dc = new DoorCell(loc, token);
			board[x][y]=dc;
			
		}else { //floor tile
			FloorCell fc= new FloorCell(loc, token.charAt(0));
			board[x][y]= fc;	
		}
		}
	  } catch (FileNotFoundException e) {
		System.out.println("Error loading board");
	}
	  List<Location> playerLocs= new ArrayList<Location>(); //Starter locations for players
	  playerLocs.add(new Location(0, 9)); playerLocs.add(new Location(0, 14));
	  playerLocs.add(new Location(23, 6)); playerLocs.add(new Location(23, 19));
	  playerLocs.add(new Location(7, 24)); playerLocs.add(new Location(0, 17));
	  
	  
	  //Placing the players on the board
	  for(int i=0; i<realPlayers.size(); i++) {
		  board[playerLocs.get(i).getX()][playerLocs.get(i).getY()].setPlayer(realPlayers.get(i));
		  realPlayers.get(i).setLocation(playerLocs.get(i));
	  }
	 for(int i= realPlayers.size();i< realPlayers.size()+nonPlayer.size(); i++) {
		 board[playerLocs.get(i).getX()][playerLocs.get(i).getY()].setPlayer(nonPlayer.get(i));
		  nonPlayer.get(i).setLocation(playerLocs.get(i));
	 } 
  }
  
  /*
   * displays the board to text output
   */
  public void displayBoard() {
	  for(int x=0; x<24; x++) {
		  for(int y=0; y<25; y++) {
			  System.out.print(board[x][y].toString());
		  }
		  System.out.println();
	  }
  }
  /*
   * Moves a player one place on the board
   * up, down, left, right
   */
  public void movePlayer() {
	  
  }
  
  /*
   * Checks if its valid to move a player in the direction
   */
  private boolean isValidMove() {
	  return false;
  }
  
  
  /*
   * Returns a cell at the Location, x,y
   */
  public Cell getCellAt(Location loc) {
	  
	  return null;
  }






}