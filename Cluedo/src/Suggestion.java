/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 69 "model.ump"
// line 159 "model.ump"
public class Suggestion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Suggestion Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Suggestion(Player aPlayer)
  {
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create suggestion due to player. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (aNewPlayer == null)
    {
      //Unable to setPlayer to null, as suggestion must always be associated to a player
      return wasSet;
    }
    
    Suggestion existingSuggestion = aNewPlayer.getSuggestion();
    if (existingSuggestion != null && !equals(existingSuggestion))
    {
      //Unable to setPlayer, the current player already has a suggestion, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Player anOldPlayer = player;
    player = aNewPlayer;
    player.setSuggestion(this);

    if (anOldPlayer != null)
    {
      anOldPlayer.setSuggestion(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.setSuggestion(null);
    }
  }

}