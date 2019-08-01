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
   * setter to set name
   * @param String -  name 
   */
  public void setName(String s) {
	  name = s;
  }
  
  /**
   * getter for name of player
   * @return String - name 
   */
  public String getName() {
	  return name;
  }
  
  /**
   * getter for character this player is playing 
   * @return String - character
   */
  public String getCharacter() {
	  return character;
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