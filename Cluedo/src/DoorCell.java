/**
 * A door cell that is the entry to a room Contains which direction and cell
 * that it can be entered from
 *
 */
public class DoorCell extends Cell {

	private char room;
	private char direction;
	private Location entry;

	/**
	 * Constructor for a door cell
	 * @param aLocation Location of the door
	 * @param id String Board representation of a door
	 */
	public DoorCell(Location aLocation, String id) {
		super(aLocation, ""+id.charAt(1));
		this.room = id.charAt(2);
		this.direction = id.charAt(1);
		// setting entry cell location
		int x = aLocation.getX();
		int y = aLocation.getY();
		if (direction == 'N')
			y--;
		if (direction == 'S')
			y++;
		if (direction == 'E')
			x++;
		if (direction == 'W')
			x--;
		entry = new Location(x, y);
	}

	/**
	 * Returns board representation of door for display
	 */
	public String toString() {
		if (super.hasPlayer()) {
			return super.toString();
		}
		return "D" + direction + " ";
	}

	/**
	 * Gets directions of where door faces
	 * 
	 * @return
	 */
	public char getDirection() {
		return direction;
	}

	/**
	 * Returns Location of the cell that a player has to be to enter a door
	 * 
	 * @return
	 */
	public Location getEntryLoc() {
		return entry;
	}

	@Override
	public char getId() {
		// TODO Auto-generated method stub
		return 'z';
	}

}