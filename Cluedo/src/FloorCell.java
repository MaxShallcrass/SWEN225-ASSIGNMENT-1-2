/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 28 "model.ump"
// line 112 "model.ump"
public class FloorCell extends Cell
{

char id;

  public FloorCell(Location aLocation, char id)
  {
    super(aLocation);
    this.id=id;
    
  }

  public String toString() {
	  if(super.hasPlayer()) {
		  return super.toString();
	  }
	  return id+ "  ";
  }

}