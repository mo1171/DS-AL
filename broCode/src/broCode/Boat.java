package broCode;

class Boat extends Vechile {
private String color ; 
 
	Boat(String color ,String date) {
		super(date);
		this.color = color ; 
		// TODO Auto-generated constructor stub
	}
	@Override
		public void drive() {
			// TODO Auto-generated method stub
			super.drive();
			System.out.println("child boat drive");
		}
	@Override
	public String toString() {
		return "Boat [color=" + color +" "+ super.toString();
	}
	


}
