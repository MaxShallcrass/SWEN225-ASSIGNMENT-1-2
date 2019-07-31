/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 59 "model.ump"
// line 147 "model.ump"
public class Hand
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Hand Associations
  private Player player;
  private List<Card> cards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Hand(Player aPlayer)
  {
    if (aPlayer == null || aPlayer.getHand() != null)
    {
      throw new RuntimeException("Unable to create Hand due to aPlayer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    player = aPlayer;
    cards = new ArrayList<Card>();
  }

  public Hand(Board aBoardForPlayer)
  {
    player = new Player(aBoardForPlayer, this);
    cards = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetMany */
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfCardsValid()
  {
    boolean isValid = numberOfCards() >= minimumNumberOfCards();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 3;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Card addCard()
  {
    Card aNewCard = new Card(this);
    return aNewCard;
  }

  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    Hand existingHand = aCard.getHand();
    boolean isNewHand = existingHand != null && !this.equals(existingHand);

    if (isNewHand && existingHand.numberOfCards() <= minimumNumberOfCards())
    {
      return wasAdded;
    }
    if (isNewHand)
    {
      aCard.setHand(this);
    }
    else
    {
      cards.add(aCard);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    //Unable to remove aCard, as it must always have a hand
    if (this.equals(aCard.getHand()))
    {
      return wasRemoved;
    }

    //hand already at minimum (3)
    if (numberOfCards() <= minimumNumberOfCards())
    {
      return wasRemoved;
    }

    cards.remove(aCard);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCardAt(Card aCard, int index)
  {  
    boolean wasAdded = false;
    if(addCard(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCardAt(Card aCard, int index)
  {
    boolean wasAdded = false;
    if(cards.contains(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCardAt(aCard, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
    for(int i=cards.size(); i > 0; i--)
    {
      Card aCard = cards.get(i - 1);
      aCard.delete();
    }
  }

}