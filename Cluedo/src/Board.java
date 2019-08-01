

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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(List<Player> realPlayers, List<Player> nonPlayer){
	  Cell[][] board= new Cell[24][25];
	  try {
		Scanner sc= new Scanner(new File("map layout.txt"));
		while(sc.hasNext()) {
		String token=sc.next();
		}
	  
	  
	  
	  
	  } catch (FileNotFoundException e) {
		System.out.println("Error loading board");
	}
	  
	  
	  
  }
  /*
   * displays the board to text output
   */
  public void displayBoard() {
	  
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