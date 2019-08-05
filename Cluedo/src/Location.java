/**
 * 
 * Contains information of where cells and players are located on the board
 *
 */
public class Location {

	private int x, y;

	/**
	 * Constructor for location
	 * 
	 * @param x
	 * @param y
	 */
	Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns x position on the board of the Location
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns y position on the board of the Location
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the x position of location for a player if they move
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y position of location for a player if they move
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * checks if a location is the same as another
	 */
	public boolean equals(Object other) {
		if (other != null && other.getClass().equals(this.getClass())) {
			Location o = (Location) other;
			if (this.x == o.getX() && this.y == o.getY())
				return true;
		}
		return false;
	}
 

}