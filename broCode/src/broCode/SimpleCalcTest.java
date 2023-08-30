package broCode;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
 




class SimpleCalcTest {

	@Test
	void twoPlusTwoShouldEqualFour() {
		var obj = new SimpleCalc() ; 
		assertEquals(4,obj.add(2, 2)); 
//		fail("Not yet implemented");
	}
	@Test 
	void threePlusFourEqualsSeven() {
		var obj = new SimpleCalc() ;
		assertThrows(IllegalArgumentException.class,() -> {obj.add(4, 1) ; }) ;
		
		assertEquals(4, obj.add(3, 1));
	}

}
