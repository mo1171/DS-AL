package trees;



public interface TreeRB <T extends Comparable<T>> {

	
	public TreeRB<T> insert(T data ) ; 
	public TreeRB<T> delete (T data ) ; 
	public void traverse() ; 
	public RBNode<T> getMax() ; 
	public RBNode<T> getMin () ; 
	public boolean isEmpty () ; 
	
		
}
