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
  private Location loc;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(Board aBoard, Hand aHand)
  {
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create player due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aHand == null || aHand.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aHand. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    hand = aHand;
  }

  public Player(Board aBoard)
  {
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create player due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    hand = new Hand(this);
  }
  /*
   * Sets location on the board where the player is
   */
  public void setLocation(Location loc) {
	  this.loc=loc;
  }
  /*
   * Gets location of the player on the board
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

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
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
  /* Code from template association_GetOne */
  public Accusation getAccusation()
  {
    return accusation;
  }

  public boolean hasAccusation()
  {
    boolean has = accusation != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setSuggestion(Suggestion aNewSuggestion)
  {
    boolean wasSet = false;
    if (suggestion != null && !suggestion.equals(aNewSuggestion) && equals(suggestion.getPlayer()))
    {
      //Unable to setSuggestion, as existing suggestion would become an orphan
      return wasSet;
    }

    suggestion = aNewSuggestion;
    Player anOldPlayer = aNewSuggestion != null ? aNewSuggestion.getPlayer() : null;

    if (!this.equals(anOldPlayer))
    {
      if (anOldPlayer != null)
      {
        anOldPlayer.suggestion = null;
      }
      if (suggestion != null)
      {
        suggestion.setPlayer(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to player
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (6)
    if (aBoard.numberOfPlayers() >= Board.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removePlayer(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAccusation(Accusation aNewAccusation)
  {
    boolean wasSet = false;
    if (accusation != null && !accusation.equals(aNewAccusation) && equals(accusation.getPlayer()))
    {
      //Unable to setAccusation, as existing accusation would become an orphan
      return wasSet;
    }

    accusation = aNewAccusation;
    Player anOldPlayer = aNewAccusation != null ? aNewAccusation.getPlayer() : null;

    if (!this.equals(anOldPlayer))
    {
      if (anOldPlayer != null)
      {
        anOldPlayer.accusation = null;
      }
      if (accusation != null)
      {
        accusation.setPlayer(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Suggestion existingSuggestion = suggestion;
    suggestion = null;
    if (existingSuggestion != null)
    {
      existingSuggestion.delete();
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removePlayer(this);
    }
    Hand existingHand = hand;
    hand = null;
    if (existingHand != null)
    {
      existingHand.delete();
    }
    Accusation existingAccusation = accusation;
    accusation = null;
    if (existingAccusation != null)
    {
      existingAccusation.delete();
    }
  }

}