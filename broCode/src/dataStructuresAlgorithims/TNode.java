package dataStructuresAlgorithims;

public class TNode<E extends Number> {
	E data;
	TNode<E> lefTNode;
	TNode<E> righTNode;

	public TNode(E data) {
		this.data = data;
		lefTNode = null;
		righTNode = null;
	}
}
