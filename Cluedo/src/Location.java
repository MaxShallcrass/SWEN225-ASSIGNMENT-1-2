/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 36 "model.ump"
// line 122 "model.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Associations
  private Cells cells;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(Cells aCells)
  {
    if (aCells == null || aCells.getLocation() != null)
    {
      throw new RuntimeException("Unable to create Location due to aCells. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    cells = aCells;
  }

  public Location(Board aBoardForCells)
  {
    cells = new Cells(this, aBoardForCells);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Cells getCells()
  {
    return cells;
  }

  public void delete()
  {
    Cells existingCells = cells;
    cells = null;
    if (existingCells != null)
    {
      existingCells.delete();
    }
  }

}