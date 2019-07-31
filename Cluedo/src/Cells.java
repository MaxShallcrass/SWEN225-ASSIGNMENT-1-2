/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 23 "model.ump"
// line 106 "model.ump"
public class Cells
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cells Associations
  private Location location;
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cells(Location aLocation, Board aBoard)
  {
    if (aLocation == null || aLocation.getCells() != null)
    {
      throw new RuntimeException("Unable to create Cells due to aLocation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    location = aLocation;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create cell due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Cells(Board aBoard)
  {
    location = new Location(this);
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create cell due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Location getLocation()
  {
    return location;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    if (aBoard == null)
    {
      return wasSet;
    }

    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      existingBoard.removeCell(this);
    }
    board.addCell(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Location existingLocation = location;
    location = null;
    if (existingLocation != null)
    {
      existingLocation.delete();
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeCell(this);
    }
  }

}