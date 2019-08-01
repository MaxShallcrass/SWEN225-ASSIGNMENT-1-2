
public class DoorCell extends Cell {

	private char room;
	private char direction;
	private Location entry;

	public DoorCell(Location aLocation, String id) {
		super(aLocation);
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

	public String toString() {
		if (super.hasPlayer()) {
			return super.toString();
		}
		return "D" + direction + " ";
	}

	public char getDirection() {
		return direction;
	}

	public Location getEntryLoc() {
		return entry;
	}

	@Override
	public char getId() {
		// TODO Auto-generated method stub
		return 'z';
	}

}