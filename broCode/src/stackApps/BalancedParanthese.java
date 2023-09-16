package stackApps;

import java.util.Stack;

public class BalancedParanthese {
public static boolean isBalanced(String exp) {
	Stack<Character> stack = new Stack<Character>(); 
	for (int i = 0; i < exp.length(); i++) {
		if(exp.charAt(i) == '(' || exp.charAt(i) == '{' || exp.charAt(i) == '[') {
			stack.push(exp.charAt(i)) ; 
		}
		else if(exp.charAt(i) == ')' || exp.charAt(i) == '}' || exp.charAt(i) == ']') {
			if (!stack.isEmpty() && isPair(stack.peek(), exp.charAt(i)  )) {
				stack.pop() ;
			}
			else 
				return false ; 
		}
	}
	
	
	
	return stack.isEmpty() ; 
}

private static boolean isPair(char peek, Character charat) {
	
	return peek == '(' && charat == ')' ||
			peek == '{' && charat == '}' ||
					peek == '[' && charat == ']';
}
public static void main(String[] args) {
	System.out.println(isBalanced("(3+3}"));
}
}
