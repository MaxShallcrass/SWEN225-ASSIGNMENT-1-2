import java.util.*;

/**
 * Hand of a player. Contains all the cards dealt to a player
 * 
 */
public class Hand {
	private ArrayList<Card> cards;

	/**
	 * Constructor for hand
	 */
	public Hand() {
		cards = new ArrayList<Card>();
	}

	/**
	 * getter for hand
	 * 
	 * @return arraylist of card
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * adder to add card to hand
	 * 
	 * @param Card
	 */
	public void add(Card c) {
		cards.add(c);
	}

	/**
	 * Returns a list of a all the names of cards a player has
	 * 
	 * @return
	 */
	public ArrayList<String> toList() {
		ArrayList<String> list = new ArrayList<String>();
		for (Card c : cards) {
			list.add(c.getName());
		}
		return list;
	}
}