



import java.util.*;


public class Hand
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private ArrayList<Card> cards;
	
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Hand() {
	  cards = new ArrayList<Card>();
  }
  
  /**
   * getter for hand
   * @return arraylist of card
   */
  public ArrayList<Card> getHand() {
	  return cards;
  }
  
  /**
   * adder to add card to hand
   * @param Card 
   */
  public void add(Card c) {
	  cards.add(c);
  }
  
}