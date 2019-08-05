/**
 * 
 * Weapon card
 *
 */
public class WeaponCard extends Card {
	/**
	 * Constructor for a Weapon Card
	 * 
	 * @param name
	 */
	public WeaponCard(String name) {
		super(name);
	}

	/**
	 * Checks if another card is the same as this card
	 */
	public boolean equals(Object other) {
		if (other != null && other.getClass().equals(this.getClass())) {
			WeaponCard o = (WeaponCard) other;
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