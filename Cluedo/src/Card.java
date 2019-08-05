/**
 * Abstract card
 * Cards for game play
 * Can be a room, character or weapon
 * Identified by the name
 */
public abstract class Card
{
	private String name;
	
	/**
	 * Constructs a new card
	 * @param name
	 */
	Card(String name){
		this.name=name;
	}
	
	/**
	 * Returns the identifier(String name) of a card
	 */
	public String getName() {
		return name;
	}
}