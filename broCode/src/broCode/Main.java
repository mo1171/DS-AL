package broCode;

import java.util.HashMap;

import lombok.val;

public class Main { 
	public static void main(String[] args) {
		char a = 'r' ;
		char b = 'h' ; 
		a^= b ; 
		b^= a ; 
		a^=b ; 
		System.out.println(b);
		}

}
