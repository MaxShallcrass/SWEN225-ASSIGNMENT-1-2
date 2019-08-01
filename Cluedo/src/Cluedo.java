
public class Cluedo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cluedo Associations
  private Envelope envelope;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cluedo(Envelope aEnvelope, Board aBoard)
  {
    if (aEnvelope == null || aEnvelope.getCluedo() != null)
    {
      throw new RuntimeException("Unable to create Cluedo due to aEnvelope. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    envelope = aEnvelope;
    if (aBoard == null || aBoard.getCluedo() != null)
    {
      throw new RuntimeException("Unable to create Cluedo due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    board = aBoard;
  }

  public Cluedo(RoomCard aRoomCardForEnvelope, WeaponCard aWeaponCardForEnvelope, CharacterCard aCharacterCardForEnvelope)
  {
    envelope = new Envelope(aRoomCardForEnvelope, aWeaponCardForEnvelope, aCharacterCardForEnvelope, this);
    board = new Board(this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Envelope getEnvelope()
  {
    return envelope;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }

  public void delete()
  {
    Envelope existingEnvelope = envelope;
    envelope = null;
    if (existingEnvelope != null)
    {
      existingEnvelope.delete();
    }
    Board existingBoard = board;
    board = null;
    if (existingBoard != null)
    {
      existingBoard.delete();
    }
  }

}
