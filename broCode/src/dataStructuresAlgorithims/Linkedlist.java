package dataStructuresAlgorithims;

public class Linkedlist<E> {
	private LNode<E> currNode;
	private LNode<E> head;

	public Linkedlist() {
		this.currNode = new LNode<E>(null, null);
		this.head = new LNode<E>(null, null);
	}

	public void addLast(E data) {
		if (head.nextNode == null) {
			currNode.data = data;
			head.nextNode = currNode;
		} else {

			LNode<E> tempNode = currNode;
			LNode<E> node = new LNode<E>(data, null);
			tempNode.nextNode = node;
			currNode = node;

		}

	}

	public void reverse() {
		LNode<E> prevNode = null ; 
		currNode = head.nextNode ; 
		while (currNode != null) {
			LNode<E> tempNode = currNode.nextNode ; 
			currNode.nextNode = prevNode ; 
			prevNode = currNode  ; 
			currNode = tempNode ; 
		}
		currNode = head.nextNode ; 
		head.nextNode = prevNode;
	}

	@Override
	public String toString() {
		String sBuilder = "" ; 
		sBuilder+="[ " ; 
		LNode<E> tempNode = currNode;
		currNode = head.nextNode;
		while (currNode.nextNode != null) {
			sBuilder+= currNode.data + " , ";
			currNode = currNode.nextNode;
		}

		sBuilder+= currNode.data + " ]";
		currNode = tempNode;
		return sBuilder;
	}
}
