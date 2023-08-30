package trees;



public interface Tree <T extends Comparable<T>> {

	
	public Tree<T> insert(T data ) ; 
	public Tree<T> delete (T data ) ; 
	public void traverse() ; 
	public RBNode<T> getMax() ; 
	public RBNode<T> getMin () ; 
	public boolean isEmpty () ; 
	
		
}
