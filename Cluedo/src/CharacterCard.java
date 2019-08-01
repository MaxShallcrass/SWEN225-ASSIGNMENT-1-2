/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 17 "model.ump"
// line 100 "model.ump"
public class CharacterCard extends Card
{
	public CharacterCard(String name) {
		super(name);
	}
	
	public boolean equals(Object other) {
		if(other!=null && other.getClass().equals(this.getClass())) {
			CharacterCard o = (CharacterCard) other;
			if(o.getName().equals(getName()))
				return true;
		}
		return false;
	}
	
	public String getName() {
		return this.getName();
	}

}