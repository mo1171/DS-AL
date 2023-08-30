package broCode;

public record Student(String nameString , int id) {
	public Student{
		if(id < 0 ) {
			throw new IllegalArgumentException("no negative numbers ") ; 
		}
		
	}
	public Student(String nameString ) {
		this(nameString, 19) ;
	}
	
}
