/**
 * Abstract card
 * Cards for game play
 *Can be a room, character or weapon
 *Identified by the name
 */
public abstract class Card
{
	private String name;
	
	Card(String name){
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