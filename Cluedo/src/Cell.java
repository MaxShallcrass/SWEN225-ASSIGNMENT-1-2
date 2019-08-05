/**
 * Contains all board display and location information
 * 
 * Each cell is either a Floor cell or a door cell
 */
public abstract class Cell {
	// Where on the board it is represented
	private Location loc;
	// For display and movements of weapons and players
	private boolean hasWeapon;
	private boolean hasPlayer;
	private Player player;
	private String weapon;
	// Room information if it is
	private boolean isRoom;
	private String roomName;

	/**
	 * Creates a new cell at Location loc
	 * 
	 * @param loc
	 */
	Cell(Location loc) {
		this.loc = loc;
		hasPlayer = false;
		hasWeapon = false;
		isRoom = false;
	}

	/**
	 * Sets the cell a room and stores the room name
	 * 
	 * @param room name of room
	 */
	public void setRoom(String room) {
		roomName = room;
		isRoom = true;
	}

	/**
	 * Returns whether is a room or not
	 * 
	 * @return True if its a room
	 */
	public boolean isRoom() {
		return isRoom;
	}

	/**
	 * Gets the name of the room if it is
	 * 
	 * @return String room name
	 */
	public String getRoom() {
		if (isRoom)
			return roomName;
		else
			throw new RuntimeException("Error: cell is not a room");
	}

	/**
	 * Sets the cell to contain a weapon
	 * 
	 * @param wep
	 */
	public void setWeapon(String wep) {
		hasWeapon = true;
		weapon = wep;
	}

	/**
	 * Removes weapon from cell
	 */
	public void removeWeapon() {
		hasWeapon = false;
		weapon = "";
	}

	/**
	 * Gets weapon name of stored weapon
	 * 
	 * @return
	 */
	public String getWeapon() {
		return weapon;
	}

	/**
	 * Sets the cell to contain a player
	 * 
	 * @param p
	 */
	public void setPlayer(Player p) {
		this.player = p;
		hasPlayer = true;
	}

	/**
	 * Remoces player from cell
	 */
	public void removePlayer() {
		player = null;
		hasPlayer = false;
	}

	/**
	 * Checsk to see if the cell contains a player
	 * 
	 * @return
	 */
	public boolean hasPlayer() {
		return hasPlayer;
	}

	/**
	 * Checks to see if cell contains a weapon
	 * 
	 * @return
	 */
	public boolean hasWeapon() {
		return hasWeapon;
	}

	/**
	 * Returns Player player that is in the cell
	 * 
	 * @return
	 */
	public Player getPlayer() {
		if (!hasPlayer)
			throw new RuntimeException("Error: Cell that does not have a player - getPlayer");

		return player;
	}

	/**
	 * Returns location on the board of where the cell is
	 * 
	 * @return
	 */
	public Location getLocation() {
		return loc;
	}

	/**
	 * Gets the cell id - its representation on the board
	 * 
	 * @return
	 */
	public abstract char getId();

	/**
	 * Returns the information of the cell to display on the board if it contains a
	 * player or weapon
	 */
	public String toString() {
		if (!hasPlayer && !hasWeapon)
			throw new RuntimeException("Error: Cell that does not have a player - toString");

		if (hasPlayer)
			return player.toString();
		else
			return weapon + " ";
	}
}