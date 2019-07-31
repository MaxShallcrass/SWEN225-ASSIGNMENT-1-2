/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 17 "model.ump"
// line 100 "model.ump"
public class CharacterCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CharacterCard Associations
  private Envelope envelope;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CharacterCard(Hand aHand, Envelope aEnvelope)
  {
    super(aHand);
    if (aEnvelope == null || aEnvelope.getCharacterCard() != null)
    {
      throw new RuntimeException("Unable to create CharacterCard due to aEnvelope. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    envelope = aEnvelope;
  }

  public CharacterCard(Hand aHand, RoomCard aRoomCardForEnvelope, WeaponCard aWeaponCardForEnvelope, Cluedo aCluedoForEnvelope)
  {
    super(aHand);
    envelope = new Envelope(aRoomCardForEnvelope, aWeaponCardForEnvelope, this, aCluedoForEnvelope);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Envelope getEnvelope()
  {
    return envelope;
  }

  public void delete()
  {
    Envelope existingEnvelope = envelope;
    envelope = null;
    if (existingEnvelope != null)
    {
      existingEnvelope.delete();
    }
    super.delete();
  }

}