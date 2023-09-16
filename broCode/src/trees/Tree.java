package trees;



public interface Tree <T extends Comparable<T>> {

	
	public Tree<T> insert(T data ) ; 
	public void delete (T data ) ; 
	public void traverse() ; 
	public T getMax() ; 
	public T getMin () ; 
	public boolean isEmpty () ; 
	
		
}
