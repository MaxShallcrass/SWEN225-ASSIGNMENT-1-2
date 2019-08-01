/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 23 "model.ump"
// line 106 "model.ump"
public class Cell
{
private boolean hasPlayer;
private Location loc;
private Player player;

Cell(Location loc){
	this.loc=loc;
	hasPlayer=false;
}

public void setPlayer(Player p) {
	this.player=p;
	hasPlayer=true;	
}
public void removePlayer() {
	player=null;
	hasPlayer=false;
}

public boolean hasPlayer() {
	return hasPlayer;
}

public String toString() {
	return player.toString();
}

}