import java.util.ArrayList;
import java.util.List;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 52 "model.ump"
// line 141 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Associations
  private Suggestion suggestion;
  private Board board;
  private Accusation accusation;
  
  private String character; 
  private String name;
  private Location loc;
  private int displayNumber;
  private Hand hand;
  private boolean isGameOver;
  
  private List<Location> visitedLocsThisTurn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(int dn) {
	  displayNumber = dn;
	  hand = new Hand();
	  isGameOver = false;
	//  visitedLocsThisTurn= new ArrayList<Location>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  
  /**
   * setter to set character
   * @param String - character name 
   */
  public void setCharacter(String s) {
	  character = s;
  }
  
  /**
   * sets players isGameOver state to true
   */
  public void losesGame() {
	  isGameOver = true;
  }
  
  /**
   * checks if players game is over yet
   */
  public boolean isGameOver() {
	  return isGameOver;
  }
  
  /**
   * getter for character
   */
  public String getCharacter() {
	  return character;
  }
  
  /**
   * set name 
   */
  public void setName(String s) {
	  name = s;
  }
  
  /**
   * get name 
   */
  public String getName() {
	  return name;
  }
  
  /**
   * setter to set name
   * @param String -  name 
   */
  public void setLocation(Location loc) {
	  this.loc=loc;
  }
  
  /**
   * getter for name of player
   * @return String - name 
   */
  public Location getLocation(){
	  return this.loc;
  }
  
  /*
   * Resets a player for a new turn
   * Creates a new list of moved locations
   */
  public void newTurn() {
	  visitedLocsThisTurn=new ArrayList<Location>();
  }
  
  /*
   * Adds locations for visited cells
   * this turn
   */
  public void addVisitedLocation(Location loc) {
	  visitedLocsThisTurn.add(loc);
  }
  /*
   * Returns all visited locations this turn
   */
  public List<Location> getVisitedLocations(){
	  return visitedLocsThisTurn;
  }
  
  /*
   * To string for board visuals
   */
  public String toString() {
	  return displayNumber + "  ";
  }
  
  public Suggestion getSuggestion()
  {
    return suggestion;
  }

  public boolean hasSuggestion()
  {
    boolean has = suggestion != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public Hand getHand()
  {
    return hand;
  }



}