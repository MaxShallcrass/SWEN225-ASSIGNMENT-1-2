



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
  public ArrayList<Card> getCards() {
	  return cards;
  }
  
  /**
   * adder to add card to hand
   * @param Card 
   */
  public void add(Card c) {
	  cards.add(c);
  }
  
  public ArrayList<String> toList() {
	  ArrayList<String> list = new ArrayList<String>();
	  for(Card c : cards) {
		  list.add(c.getName());
	  }
	  return list;
  
  }
  
}