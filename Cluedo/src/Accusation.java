/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 64 "model.ump"
// line 153 "model.ump"
public class Accusation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Accusation Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Accusation(Player aPlayer)
  {
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create accusation due to player. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
      //Unable to setPlayer to null, as accusation must always be associated to a player
      return wasSet;
    }
    
    Accusation existingAccusation = aNewPlayer.getAccusation();
    if (existingAccusation != null && !equals(existingAccusation))
    {
      //Unable to setPlayer, the current player already has a accusation, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Player anOldPlayer = player;
    player = aNewPlayer;
    player.setAccusation(this);

    if (anOldPlayer != null)
    {
      anOldPlayer.setAccusation(null);
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
      existingPlayer.setAccusation(null);
    }
  }

}