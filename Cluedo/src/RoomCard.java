/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 12 "model.ump"
// line 95 "model.ump"
public class RoomCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomCard Associations
  private Envelope envelope;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RoomCard(Hand aHand, Envelope aEnvelope)
  {
    super(aHand);
    if (aEnvelope == null || aEnvelope.getRoomCard() != null)
    {
      throw new RuntimeException("Unable to create RoomCard due to aEnvelope. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    envelope = aEnvelope;
  }

  public RoomCard(Hand aHand, WeaponCard aWeaponCardForEnvelope, CharacterCard aCharacterCardForEnvelope, Cluedo aCluedoForEnvelope)
  {
    super(aHand);
    envelope = new Envelope(this, aWeaponCardForEnvelope, aCharacterCardForEnvelope, aCluedoForEnvelope);
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