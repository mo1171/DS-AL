package broCode;

import java.io.Serializable;

public class GenericClass   <T extends Number , U>   implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private T val  ; 
private U val2 ; 
public GenericClass(T val , U val2) {
	this.val = val ; 
	this.val2 = val2 ; 
	
}
public U getval2() {
	return this.val2 ; 
}
public T getVale() {
	return this.val ; 
}


}
