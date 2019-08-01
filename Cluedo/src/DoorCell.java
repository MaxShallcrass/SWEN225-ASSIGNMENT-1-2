/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 33 "model.ump"
// line 117 "model.ump"
public class DoorCell extends Cell
{
	private char room;
	private char direction;
	

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DoorCell(Location aLocation, String id)
  {
    super(aLocation);
	  this.room=id.charAt(2);
	  this.direction=id.charAt(1);
	  
  }
  
  public String toString() {
	  if(super.hasPlayer()) {
		  return super.toString();
	  }
	  return "D" + direction + " ";
  }

 

}