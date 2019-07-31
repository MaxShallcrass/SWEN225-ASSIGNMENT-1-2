/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 73 "model.ump"
// line 164 "model.ump"
public class Envelope
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Envelope Associations
  private RoomCard roomCard;
  private WeaponCard weaponCard;
  private CharacterCard characterCard;
  private Cluedo cluedo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Envelope(RoomCard aRoomCard, WeaponCard aWeaponCard, CharacterCard aCharacterCard, Cluedo aCluedo)
  {
    if (aRoomCard == null || aRoomCard.getEnvelope() != null)
    {
      throw new RuntimeException("Unable to create Envelope due to aRoomCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    roomCard = aRoomCard;
    if (aWeaponCard == null || aWeaponCard.getEnvelope() != null)
    {
      throw new RuntimeException("Unable to create Envelope due to aWeaponCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    weaponCard = aWeaponCard;
    if (aCharacterCard == null || aCharacterCard.getEnvelope() != null)
    {
      throw new RuntimeException("Unable to create Envelope due to aCharacterCard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    characterCard = aCharacterCard;
    if (aCluedo == null || aCluedo.getEnvelope() != null)
    {
      throw new RuntimeException("Unable to create Envelope due to aCluedo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cluedo = aCluedo;
  }

  public Envelope(Hand aHandForRoomCard, Hand aHandForWeaponCard, Hand aHandForCharacterCard, Board aBoardForCluedo)
  {
    roomCard = new RoomCard(aHandForRoomCard, this);
    weaponCard = new WeaponCard(aHandForWeaponCard, this);
    characterCard = new CharacterCard(aHandForCharacterCard, this);
    cluedo = new Cluedo(this, aBoardForCluedo);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public RoomCard getRoomCard()
  {
    return roomCard;
  }
  /* Code from template association_GetOne */
  public WeaponCard getWeaponCard()
  {
    return weaponCard;
  }
  /* Code from template association_GetOne */
  public CharacterCard getCharacterCard()
  {
    return characterCard;
  }
  /* Code from template association_GetOne */
  public Cluedo getCluedo()
  {
    return cluedo;
  }

  public void delete()
  {
    RoomCard existingRoomCard = roomCard;
    roomCard = null;
    if (existingRoomCard != null)
    {
      existingRoomCard.delete();
    }
    WeaponCard existingWeaponCard = weaponCard;
    weaponCard = null;
    if (existingWeaponCard != null)
    {
      existingWeaponCard.delete();
    }
    CharacterCard existingCharacterCard = characterCard;
    characterCard = null;
    if (existingCharacterCard != null)
    {
      existingCharacterCard.delete();
    }
    Cluedo existingCluedo = cluedo;
    cluedo = null;
    if (existingCluedo != null)
    {
      existingCluedo.delete();
    }
  }

}