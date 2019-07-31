/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 7 "model.ump"
// line 90 "model.ump"
public class WeaponCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Associations
  private Envelope envelope;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WeaponCard(Hand aHand, Envelope aEnvelope)
  {
    super(aHand);
    if (aEnvelope == null || aEnvelope.getWeaponCard() != null)
    {
      throw new RuntimeException("Unable to create WeaponCard due to aEnvelope. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    envelope = aEnvelope;
  }

  public WeaponCard(Hand aHand, RoomCard aRoomCardForEnvelope, CharacterCard aCharacterCardForEnvelope, Cluedo aCluedoForEnvelope)
  {
    super(aHand);
    envelope = new Envelope(aRoomCardForEnvelope, this, aCharacterCardForEnvelope, aCluedoForEnvelope);
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