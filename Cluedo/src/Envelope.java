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

  

  public Envelope(RoomCard room, WeaponCard weapon, CharacterCard character){
	  this.roomCard=room;
	  this.weaponCard=weapon;
	  this.characterCard=character;
  }
  public RoomCard getRoom() {
	  return roomCard;
  }
  public WeaponCard getWeapon() {
	  return weaponCard;
  }
  public CharacterCard getCharacter() {
	  return characterCard;
  }
  public String toString() {
	  return roomCard.getName() +" "+weaponCard.getName()+" "+characterCard.getName();
  }
  
  
  
}