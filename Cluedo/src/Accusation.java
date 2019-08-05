/**
 * An accusation comprises a character, a weapon, and a room (which can be any
 * room, not just the one the player making the accusation may be in). If the
 * accusation made by a player exactly matches the actual murder circumstances
 * (only the accusing player is allowed to see the solution) the player wins,
 * otherwise the player is excluded from making further suggestions or
 * accusations. This means the player will continue to refute suggestions by
 * others but cannot win the game anymore.
 */
public class Accusation {

	private RoomCard room;
	private WeaponCard weapon;
	private CharacterCard character;

	/**
	 * Creates a new acusation Contains a card for each of a room, weapon and
	 * character
	 */
	public Accusation(RoomCard r, WeaponCard w, CharacterCard c) {
		room = r;
		weapon = w;
		character = c;
	}

	/**
	 * Tests if accusation is correct
	 * 
	 * @param Envelope e to test accusation against
	 * @return returns true if accusation correct, false otherwise
	 */
	public boolean testAccusation(Envelope e) {
		if (room.equals(e.getRoom())) {
			if (weapon.equals(e.getWeapon())) {
				if (character.equals(e.getCharacter())) {
					return true;
				}
			}
		}
		return false;
	}
}
