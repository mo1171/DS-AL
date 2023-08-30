package heap;

public class MainApp {
	
	
	public static void main(String[] args) {
	Heap<Integer> heap = new MaxHeap<Integer>() ; 
	heap.insert(3).insert(1).insert(6).insert(2) ;
	var val = heap.getRoot();
	System.out.println(val);
	heap.print();
	
			
		
	}
}
