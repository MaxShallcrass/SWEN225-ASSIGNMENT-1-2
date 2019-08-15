/**
 * 
 * Before the game starts, one character, one weapon, and one room card are
 * blindly selected at random. This selection represents the murder
 * circumstances, i.e., the solution that players need to figure out during
 * game play. The respective cards make an envelope to be hidden from view
 *
 */
public class Envelope {
	private RoomCard roomCard;
	private WeaponCard weaponCard;
	private CharacterCard characterCard;

	/**
	 * Constructor for envelope
	 * 
	 * @param room
	 * @param weapon
	 * @param character
	 */
	public Envelope(RoomCard room, WeaponCard weapon, CharacterCard character) {
		this.roomCard = room;
		this.weaponCard = weapon;
		this.characterCard = character;
	}

	/**
	 * @return room card
	 */
	public RoomCard getRoom() {
		return roomCard;
	}

	/**
	 * @return weapon card
	 */
	public WeaponCard getWeapon() {
		return weaponCard;
	}

	/**
	 * @return character card
	 */
	public CharacterCard getCharacter() {
		return characterCard;
	}

	/**
	 * Returns the contents of the envelope as a string
	 */
	public String toString() {
		return "Room: " + roomCard.getName() + "\nWeapon: " + weaponCard.getName() + "\nCharacter: "
				+ characterCard.getName();
	}
}