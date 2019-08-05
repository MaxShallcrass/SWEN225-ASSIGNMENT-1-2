/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 36 "model.ump"
// line 122 "model.ump"
public class Location
{

private int x, y;

 Location(int x, int y){
this.x=x;
this.y=y;
	 
 }
 public int getX() {
	 return x;
 }
 public int getY() {
	 return y;
 }
 
 public void setX(int x) {
	 this.x=x;
 }
 public void setY(int y) {
	 this.y=y;
 }
 
 public boolean equals(Object other) {
	 if(other!=null && other.getClass().equals(this.getClass())) {
			Location o = (Location) other;
			if(this.x==o.getX() && this.y==o.getY())
				return true;
		}
		return false;
 }
 

}