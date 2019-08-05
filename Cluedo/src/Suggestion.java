import java.util.ArrayList;

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
  private RoomCard room;
  private WeaponCard weapon;
  private CharacterCard character;
  

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Suggestion(RoomCard r, WeaponCard w, CharacterCard c) {
	  room = r;
	  weapon = w;
	  character = c;
	  
  }
  
  //------------------------
  // INTERFACE
  //------------------------
  
  /**
   * 
   * @param s
   * @return boolean
   */
  public Boolean refutedBy(String s) {
	  if(room.getName().equals(s)) {
		  return true; 
	  }
	  if(weapon.getName().equals(s)) {
		  return true; 
	  }
	  if(character.getName().equals(s)) {
		  return true; 
	  }
	  return false;
	  
  }
  
  public String toString() {
	  return "Room: " + room.getName() +"\nWeapon: "+weapon.getName()+"\nCharacter: "+character.getName();
  }
  
  
  public ArrayList<Card> getCards(){
	  ArrayList<Card> cards = new ArrayList<Card>();
	  cards.add(room);
	  cards.add(weapon);
	  cards.add(character);
	  return cards;
  }
}