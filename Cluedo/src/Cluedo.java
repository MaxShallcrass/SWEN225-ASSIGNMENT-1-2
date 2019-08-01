import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 47 "model.ump"
// line 135 "model.ump"
public class Cluedo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cluedo Associations
  private Envelope envelope;
  private Board board;
  
  //

  //------------------------
  // CONSTRUCTOR
  //------------------------
  /**
   * Constructor for Cluedo. Runs the main game mechanics. 
   */
  public Cluedo() {
	  //Get num players and create players 
	  int numPlayers = getNumPlayers();
	  HashMap<Integer,Player> players = new HashMap<Integer,Player>();
	  //choose characters 
	  chooseCharacters();
	  //New Board
	 // Board board = new Board(numPlayers);
	  
	  //Turns : dice -> move -> suggestion(in room) or accusation
  }
  
  public ArrayList<String> chooseCharacters() {
	  return null;
  }
  
  /**
   * Method to get the number of players.
   * Has checking to make sure they give good inputs 
   * @return int
   */
  public int getNumPlayers() {
	  boolean foundNum = false;
	  int numPlayers = -1;
	  while(!foundNum) {
		  String strNumPlayers = askPlayer("How many people are playing? ");
		  int numErrorMsg = 0;
		  try {
			 foundNum = true;
		     numPlayers = Integer.parseInt(strNumPlayers);
		  }
		  catch (NumberFormatException e) {
		     foundNum = false;
		     numPlayers = -1;
		     System.out.println("Input error, please enter 2 , 3 or 4");
		     numErrorMsg++;
		  }
		  if(numPlayers < 2 || numPlayers > 4) {
			  if(numErrorMsg == 0) {
				  System.out.println("Input error, please enter 2 , 3 or 4");
			  }
			  numPlayers = -1;
			  foundNum = false;
		  }
	  }
	  return numPlayers;
  }


  //------------------------
  // INTERFACE
  //------------------------
  /**
   * Getter for envelope 
   * @return Envelope
   */
  public Envelope getEnvelope() {
    return envelope;
  }
  /**
   * getter for Board 
   * @return Board 
   */
  public Board getBoard() {
    return board;
  }
  /**
   * Asks the player for input 
   * @param args
   */
  public String askPlayer(String s) {
	  System.out.print(s);
	  Scanner sc = new Scanner(System.in);
	  String ans = sc.next();
	  return ans;
  }
  
  
  public static void main(String args[]) {
	  new Cluedo();
  }
  
  
  

}

















