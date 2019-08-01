/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/

// line 23 "model.ump"
// line 106 "model.ump"
public class Cell {
	private boolean hasPlayer;
	private Location loc;
	private Player player;
	private WeaponCard weapon;

	Cell(Location loc) {
		this.loc = loc;
		hasPlayer = false;
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
	
	public Player getPlayer() {
		if(!hasPlayer)
			throw new RuntimeException("Error: Cell that does not have a player - getPlayer");
		return player;
	}
	public void setWeapon(WeaponCard weapon) {
		
		
	}
	
	
	

	public String toString() {
		if (!hasPlayer)
			throw new RuntimeException("Error: Cell that does not have a player - toString - could be an inheiritance issue");

		return player.toString();
	}
}