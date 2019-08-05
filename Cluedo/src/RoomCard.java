/**
 * 
 * Room card
 *
 */
public class RoomCard extends Card {
	/**
	 * Room card constructor
	 * 
	 * @param name
	 */
	public RoomCard(String name) {
		super(name);
	}

	/**
	 * Checks if another card is the same as this card
	 */
	public boolean equals(Object other) {
		if (other != null && other.getClass().equals(this.getClass())) {
			RoomCard o = (RoomCard) other;
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