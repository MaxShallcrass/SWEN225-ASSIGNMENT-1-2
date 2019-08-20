import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Contains all board display and location information
 * 
 * Each cell is either a Floor cell or a door cell
 */
public abstract class Cell extends JButton  implements ActionListener{
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
	
	
	
	
	//Pathfinding
	boolean visited=false;
	private static Cell selectedCell=null;

	// UI
	private ImageIcon tile;
	private ImageIcon weaponImg;
	private ImageIcon charImg;
	private String toolTip;

	private boolean switchh = true;

	// NEW
	public void actionPerformed(ActionEvent e){
		selectedCell=this;
		
		
		System.out.println("touched");
	 }//

	/**
	 * Creates a new cell at Location loc
	 * 
	 * @param loc
	 */
	Cell(Location loc, String imageName) {
		this.addActionListener(this);
		 setFocusPainted(false);
	        setMargin(new Insets(0, 0, 0, 0));
	        setContentAreaFilled(false);
	        setBorderPainted(false);
	        setOpaque(false);
		
		
		
		this.loc = loc;
		hasPlayer = false;
		hasWeapon = false;
		isRoom = false;
		// Setting cell icon
		// new
		switch (imageName) {
		case "#":
			imageName = "corridor";
			break;
		case "!":
			imageName = "empty";
			break;
		case "E":
			imageName = "dooreast";
			break;
		case "S":
			imageName = "doorsouth";
			break;
		case "N":
			imageName = "doornorth";
			break;
		case "W":
			imageName = "doorwest";
			break;
		default:
			imageName = "room";
		}
		setVisible(true);
		this.setToolTipText(imageName);
		toolTip=imageName;	
		tile = new ImageIcon("resource/boardtiles/" + imageName + ".jpg");
		int size = CluedoUI.getGuiSize();
		int sizeConst = 750/30;
		setIcon(tile);
		revalidate();
		repaint();
		setMaximumSize(new Dimension(size/sizeConst,size/sizeConst));
		setPreferredSize(new Dimension(size/sizeConst,size/sizeConst));
	}

	/**
	 * Sets the cell a room, stores the room name and sets hover message to the room
	 * name
	 * 
	 * @param room name of room
	 */
	public void setRoom(String room) {
		setToolTipText(room);
		toolTip = room;
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
		weapon = wep;
		switch (wep) {
		case "Ca":
			wep = "candlestick";
			break;
		case "Dg":
			wep = "dagger";
			break;
		case "Lp":
			wep = "leadpipe";
			break;
		case "Rp":
			wep = "rope";
			break;
		case "Sp":
			wep = "wrench";
			break;
		case "Rv":
			wep = "revolver";
			break;
		default:
			throw new RuntimeException("Incorrect weapon name for tile");
		}
		setToolTipText(wep);
		weaponImg = new ImageIcon("resource/boardtiles/" + wep + ".jpg");
		setIcon(weaponImg);
		hasWeapon = true;
	}

	/**
	 * Removes weapon from cell
	 */
	public void removeWeapon() {
		hasWeapon = false;
		weapon = "";
		setToolTipText(toolTip);
		setIcon(tile);
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
		System.out.println(p.getCharacter());
		//switch (p.getCharacter()) {
	//	case "Mrs. White":
	//		pName = "candlestick";
	//		break;
	//	case "Mr. Green":
	//		pName = "dagger";
	//		break;
	//	case "Lp":
	//		pName = "Mrs. Peacock";
	//		break;
	//	case "Professor Plum":
	//		pName = "rope";
//			break;
	//	case "Miss Scarlett":
	//		pName = "wrench";
	//		break;
	//	case "Rv":
	//		pName = "Colonel Mustard";
	//		break;
	//	default:
	//		throw new RuntimeException("Incorrect player name for tile");
	//	}
		 charImg= new ImageIcon("resource/boardtiles/" + p.getCharacter() + ".PNG");
		setToolTipText(p.getCharacter());
		setIcon(charImg);
		this.player = p;
		hasPlayer = true;
	}

	/**
	 * Remoces player from cell
	 */
	public void removePlayer() {
		player = null;
		hasPlayer = false;
		setToolTipText(toolTip);
		setIcon(tile);
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
	public Location getLoc() {
		return loc;
	}

	/**
	 * Gets the cell id - its representation on the board
	 * 
	 * @return
	 */
	public abstract char getId();
	
	/**
	 * Returns if the cell has been visited in the current path find
	 * @return
	 */
	public boolean isVisited() {
		return visited;
	}
	
	/**
	 * Sets the cell visited or not visted in the pathfind
	 * @param b
	 */
	public void setIsVisited(boolean b) {
		visited=b;
	}
	
	/**
	 * Gets selected cell for path finding
	 */
	Cell getSelected() {
		return selectedCell;
	}
	
	public void resetSelectedCell() {
		selectedCell=null;
	}

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