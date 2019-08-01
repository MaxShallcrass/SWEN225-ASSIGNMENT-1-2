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
  private Hand hand;
  private Accusation accusation;
  
  private String character; 
  private String name;
  private Location loc;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player() {
  
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
   * To string for board visuals
   */
  public String toString() {
	  return "gotta fix";
  }
  
  
  
  /////////////////
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