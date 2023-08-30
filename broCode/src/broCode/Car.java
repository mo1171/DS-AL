package broCode;


import java.util.Objects;

class Car extends Vechile implements Cloneable  {
	 String color;
	 int vehileId;
	static int cnt = 0 ;
	 
	Car(String color, int vehileId,String date) {
		 super(date) ; 
		this.color = color;
		this.vehileId = vehileId;
		cnt++ ; 
	}


	void Break() {
		super.showinfo();
		System.out.println("break");
	}

	

	@Override
	public String toString() {
		return "Car [color=" + color + ", vehileId=" + vehileId +" "+ super.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, vehileId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		return Objects.equals(color, other.color) && vehileId == other.vehileId;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	String getColor() {
		return color;
	}

	void setColor(String color) {
		this.color = color;
	}

	int getVehileId() {
		return vehileId;
	}

	void setVehileId(int vehileId) {
		this.vehileId = vehileId;
	}




	  public void drive() {
		System.out.println("child car drive");
		
	}


}
