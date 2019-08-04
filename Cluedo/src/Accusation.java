/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/

// line 64 "model.ump"
// line 153 "model.ump"
public class Accusation {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Accusation Associations
	private RoomCard room;
	private WeaponCard weapon;
	private CharacterCard character;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Accusation(RoomCard r, WeaponCard w, CharacterCard c) {
		room = r;
		weapon = w;
		character = c;
	}
	
	/**
	 * tests if accusation is correct
	 * @param Envelope e to test accusation against
	 * @return returns true if accusation correct, false otherwise
	 */
	public boolean testAccusation(Envelope e) {
		if(room.equals(e.getRoom())) {
			if(weapon.equals(e.getWeapon())) {
				if(character.equals(e.getCharacter())) {
					return true;
				}
			}
		}
		return false;
	}

}

// ------------------------
// INTERFACE
// ------------------------
/* Code from template association_GetOne */
