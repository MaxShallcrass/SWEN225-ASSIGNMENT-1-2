/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 2 "model.ump"
// line 84 "model.ump"
public class Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Associations
  private Hand hand;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(Hand aHand)
  {
    boolean didAddHand = setHand(aHand);
    if (!didAddHand)
    {
      throw new RuntimeException("Unable to create card due to hand. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Hand getHand()
  {
    return hand;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setHand(Hand aHand)
  {
    boolean wasSet = false;
    //Must provide hand to card
    if (aHand == null)
    {
      return wasSet;
    }

    if (hand != null && hand.numberOfCards() <= Hand.minimumNumberOfCards())
    {
      return wasSet;
    }

    Hand existingHand = hand;
    hand = aHand;
    if (existingHand != null && !existingHand.equals(aHand))
    {
      boolean didRemove = existingHand.removeCard(this);
      if (!didRemove)
      {
        hand = existingHand;
        return wasSet;
      }
    }
    hand.addCard(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Hand placeholderHand = hand;
    this.hand = null;
    if(placeholderHand != null)
    {
      placeholderHand.removeCard(this);
    }
  }

}