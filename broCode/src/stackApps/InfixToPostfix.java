package stackApps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class InfixToPostfix {

	private static Stack<Character> operators = new Stack<Character>() ; 
	
	public static String  toPostfix(String infix) {
		List<String> postfix = new ArrayList<String>() ; 
		for (String current : infix.split(" ")) {
			if(isOperator(current)) { 
				if(current.equals("(")) {
					operators.push(current.charAt(0)) ;
					continue ; 
				}
				else if (current.equals(")")) {
					do {
						postfix.add(operators.pop().toString() );
					}
					while(!operators.peek().equals('(')) ;
					operators.pop()	   ; 
					continue ; 
					
				}
					
				while (!operators.isEmpty() && hasLowerPresedence(current , operators.peek())) {
					postfix.add(operators.pop().toString()) ; 
				}
				operators.push(current.charAt(0)) ; 
			}
			else {
				postfix.add(current) ; 
			}
				
		}
		while (!operators.isEmpty()) {
			postfix.add(operators.pop().toString()) ; 
		}
		return String.join(" " , postfix) ; 
		
	}
	public static double evaluatePostfix(String postfix) {
		Stack<Double> nums = new Stack<Double>() ;
		for (String current : postfix.split(" ")) {
			if(!isOperator(current)) {
				nums.push(Double.parseDouble(current)) ; 
			}
			else {
				double num1 = nums.pop() ; 
				double num2 = nums.pop() ;
				switch (current)	 {
				case "+" -> nums.push(num2+num1)  ; 
				case "-" -> nums.push(num2-num1)  ; 
				case "*" -> nums.push(num2*num1)  ; 
				case "/" -> nums.push(num2/num1)  ;
				case "%" -> nums.push(num2 % num1) ; 
				
				}
				
			}
		}
		return nums.pop();
		
	}

	private static boolean hasLowerPresedence(String current, Character peek) {
		
		return presedence(current) <= presedence(peek.toString()) ; 
	}

	private static int presedence(String op) {
		return switch (op) {
		case "(" , ")" -> 0 ;  
		case "+" , "-" -> 1 ;
		case "*" , "/" -> 2 ; 	
		case "%" -> 3 ; 
		default -> throw new IllegalArgumentException("Unexpected value: " + op);
			
		}; 
	}

	private static boolean isOperator(String current) {
		return Arrays.asList("+" , "-" , "/" , "*" , "%" , "(" , ")").contains(current) ; 
	}
	public static void main(String[] args) {
//		System.out.println(toPostfix("10 * ( 2 + 1 + 5 ) / 2 - 1"));
		System.out.println(toPostfix("2 + ( ( 8 + 2 * 3 ) / 2 ) - 1 "));
		
	}
}
