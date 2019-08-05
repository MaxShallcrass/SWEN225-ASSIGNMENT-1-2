/**
 * 
 * Floor cell - every cell in the game that is not a door cell
 *
 */
public class FloorCell extends Cell {
	char id;

	/**
	 * Floor cell constructor
	 * 
	 * @param aLocation Location of where is on the board
	 * @param id
	 */
	public FloorCell(Location aLocation, char id) {
		super(aLocation);
		this.id = id;

	}

	/**
	 * Returns representation of the cell to board for display based on if it has a
	 * weapon or player
	 */
	public String toString() {
		if (super.hasPlayer() || super.hasWeapon()) {
			return super.toString();
		}
		return id + "  ";
	}

	@Override
	public char getId() {
		return id;
	}

}