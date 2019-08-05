/**
 * Contains all board display and movement information
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
	
	
	public void setRoom(String room) {
		roomName=room;
		isRoom=true;
	}
	public boolean isRoom() {
		return isRoom;
	}
	
	public String getRoom(){
		if(isRoom)
		return roomName;
		else
			throw new RuntimeException("Error: cell is not a room");
	}
	
	public void setWeapon(String wep) {
		hasWeapon=true;
		weapon=wep;
	}
	
	public void removeWeapon() {
		hasWeapon=false;
		weapon="";
	}
	
	public String getWeapon() {
		return weapon;
	}

	public void setPlayer(Player p) {
		this.player = p;
		hasPlayer = true;
	}

	public void removePlayer() {
		player = null;
		hasPlayer = false;
	}

	public boolean hasPlayer() {
		return hasPlayer;
	}
	
	public boolean hasWeapon() {
		return hasWeapon;
	}
	
	public Player getPlayer() {
		if(!hasPlayer)
			throw new RuntimeException("Error: Cell that does not have a player - getPlayer");
			
		return player;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public abstract char getId();


	public String toString() {
		if (!hasPlayer && !hasWeapon)
			throw new RuntimeException("Error: Cell that does not have a player - toString - could be an inheiritance issue");

		if(hasPlayer)
		return player.toString();
		else
			return weapon+ " ";
	}
}