/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 7 "model.ump"
// line 90 "model.ump"
public class WeaponCard extends Card
{

	public WeaponCard(String name) {
		super(name);
	}
	
	public boolean equals(Object other) {
		if(other!=null && other.getClass().equals(this.getClass())) {
			WeaponCard o = (WeaponCard) other;
			if(o.getName().equals(getName()))
				return true;
		}
		return false;
	}
	
	public String getName() {
		return super.getName();
	}

}