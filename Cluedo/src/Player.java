import java.util.ArrayList;
import java.util.List;

/**
 * Stores all player information - real or not
 */
public class Player {
	// private Suggestion suggestion;
	// private Accusation accusation;

	// Player information
	private String character;
	private String name;
	private Location loc;
	private int displayNumber;
	private Hand hand;
	private boolean isGameOver;
	// Cells the player has visited this turn
	// -For movement as cannot move through same cell twice on the same turn
	private List<Location> visitedLocsThisTurn;
	//Suggestion information - Player cannot make suggestion from same room two turns in a room
	private boolean suggestedLastTurn;
	private boolean suggestedThisturn;
	private String lastRoomSuggested;

  public Player(int dn) {
	  displayNumber = dn;
	  hand = new Hand();
	  isGameOver = false;
	  suggestedLastTurn=false;
  }

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
  public boolean hasLost() {
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
   * Returns the players hand - Contains all the cards
   */
  public Hand getHand() {
	  return hand;
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
  
	/**
	 * Stores information if a suggestion was made by a player last turn
	 * 
	 * @param room
	 */
	public void makesSsuggestion(String room) {
		suggestedThisturn = true;
		lastRoomSuggested = room;
	}

	/**
	 * Returns true if a player can make a suggestion Returns false if player has
	 * made a suggestion in the same room last turn
	 * 
	 * @param roomName
	 * @return
	 */
	public boolean canSuggest(String roomName) {
		if (!suggestedLastTurn || !roomName.equals(lastRoomSuggested))
			return true;

		return false;
	}

	/**
	 * Records if suggestion was made this turn
	 */
	public void resetSuggestion() {
		if (suggestedThisturn)
			suggestedLastTurn = true;
		else
			suggestedLastTurn = true;

		suggestedThisturn = false;
	}
  
  /*
   * Resets a player for a new turn
   * Creates a new list of moved locations
   */
  public void newTurn() {
	  visitedLocsThisTurn=new ArrayList<Location>();
  }
  /*
   * Returns player number
   */
  public String getNumber() {
	  return "("+ displayNumber + ")";
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
  
//  public Suggestion getSuggestion()
 // {
  //  return suggestion;
 // }

  //public boolean hasSuggestion()
  //{
   // boolean has = suggestion != null;
    //return has;
 // }
}