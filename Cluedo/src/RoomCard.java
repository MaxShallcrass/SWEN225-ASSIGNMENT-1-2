/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 12 "model.ump"
// line 95 "model.ump"
public class RoomCard extends Card
{

	public RoomCard(String name) {
		super(name);
	}
	
	public boolean equals(Object other) {
		if(other!=null && other.getClass().equals(this.getClass())) {
			RoomCard o = (RoomCard) other;
			if(o.getName().equals(getName()))
				return true;
		}
		return false;
	}
	
	public String getName() {
		return super.getName();
	}

  

}