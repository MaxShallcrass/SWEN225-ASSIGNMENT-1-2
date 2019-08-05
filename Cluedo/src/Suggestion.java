import java.util.ArrayList;

/**
 * Suggestion contains a room, weapon and player card that is suggested to be
 * the murder circumstances
 */
public class Suggestion {
	private RoomCard room;
	private WeaponCard weapon;
	private CharacterCard character;

	/**
	 * Constructor for a suggestion
	 * 
	 * @param r
	 * @param w
	 * @param c
	 */
	public Suggestion(RoomCard r, WeaponCard w, CharacterCard c) {
		room = r;
		weapon = w;
		character = c;
	}

	/**
	 * Returns true for card names that a player contains that can be used to refute
	 * a suggestion with
	 * 
	 * @param s
	 * @return boolean
	 */
	public Boolean refutedBy(String s) {
		if (room.getName().equals(s)) {
			return true;
		}
		if (weapon.getName().equals(s)) {
			return true;
		}
		if (character.getName().equals(s)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a string of all the cards in a suggestion for display
	 */
	public String toString() {
		return "Room: " + room.getName() + "\nWeapon: " + weapon.getName() + "\nCharacter: " + character.getName();
	}

	/**
	 * Returns a list of cards in the suggestion
	 * 
	 * @return
	 */
	public ArrayList<Card> getCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(room);
		cards.add(weapon);
		cards.add(character);
		return cards;
	}
}