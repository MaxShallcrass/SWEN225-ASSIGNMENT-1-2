/**
 * 
 * Character card
 *
 */
public class CharacterCard extends Card {
	/**
	 * Card constructor
	 * @param name
	 */
	public CharacterCard(String name) {
		super(name);
	}

	/**
	 * Checks if another card is the same as this card
	 */
	public boolean equals(Object other) {
		if (other != null && other.getClass().equals(this.getClass())) {
			CharacterCard o = (CharacterCard) other;
			if (o.getName().equals(getName()))
				return true;
		}
		return false;
	}

	/**
	 * Returns name of card
	 */
	public String getName() {
		return super.getName();
	}

}