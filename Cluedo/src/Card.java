import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Abstract card
 * Cards for game play
 * Can be a room, character or weapon
 * Identified by the name
 */
public abstract class Card extends JLabel
{
	private String name;
	
	/**
	 * Constructs a new card
	 * @param name
	 */
	Card(String name){
		this.setSize(new Dimension(140, 220));
		this.setIcon(new ImageIcon("resource/cards/" + name + ".PNG"));
		System.out.println(name);
		this.name=name;
	}
	
	/**
	 * Returns the identifier(String name) of a card
	 */
	public String getName() {
		return name;
	}
}