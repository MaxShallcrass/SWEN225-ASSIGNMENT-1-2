import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;


/**
 * Abstract card
 * Cards for game play
 * Can be a room, character or weapon
 * Identified by the name
 */
public abstract class Card extends JLabel
{
	private String name;
	private final int intX = 14;
	private final int intY = 22;
	
	/**
	 * Constructs a new card
	 * @param name
	 */
	Card(String name){
		int size = CluedoUI.getGuiSize();
		//get resized card 
		ImageIcon ii = new ImageIcon("resource/cards/" + name + ".PNG");
		Image image = ii.getImage().getScaledInstance(intX*5,intY*5,Image.SCALE_SMOOTH); 
		ii = new ImageIcon(image);
		this.setIcon(ii);
		this.setToolTipText(name);
		
	//	this.setSize(new Dimension(10,10));
	//	this.setIcon(new ImageIcon("resource/cards/" + name + ".PNG"));
		this.name=name;
	}
	
	/**
	 * Returns the identifier(String name) of a card
	 */
	public String getName() {
		return name;
	}
}