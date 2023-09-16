package trees;

import java.util.LinkedList;
import java.util.Queue;

public class BSTree<E extends Number> {

	TNode<E> rooTNode;

	public BSTree() {
		rooTNode = null;
	}

	public void insert(E data) {
		rooTNode = insertHelper(rooTNode, data);

	}

	private TNode<E> insertHelper(TNode<E> node, E data) {

		if (node == null) {
			node = new TNode<E>(data);
		} else {
			if (data.doubleValue() < node.data.doubleValue())
				node.lefTNode = insertHelper(node.lefTNode, data);
			else
				node.righTNode = insertHelper(node.righTNode, data);
		}

		return node;
	}

	public void displaylevels() {
		displaylevelsHelper(rooTNode);
	}

	private void displaylevelsHelper(TNode<E> root) {
		if (root == null)
			return;
		Queue<TNode<E>> queue = new LinkedList<TNode<E>>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TNode<E> currNode = queue.poll();
			System.out.print(currNode.data + " ");
			if (currNode.lefTNode != null) {
				queue.offer(currNode.lefTNode);
			}
			if (currNode.righTNode != null) {
				queue.offer(currNode.righTNode);
			}
		}
		System.out.println();
	}

	public void display() {
		displayHelper(rooTNode);
	}

	private void displayHelper(TNode<E> node) {
		if (node != null) {
			displayHelper(node.lefTNode);
			System.out.print(node.data + " ");
			displayHelper(node.righTNode);
		}
	}

	public boolean search(E data) {
		return searchHelper(rooTNode, data);
	}

	private boolean searchHelper(TNode<E> node, E data) {
		if (node == null)
			return false;

		else if (data.doubleValue() < node.data.doubleValue())
			return searchHelper(node.lefTNode, data);

		else if (data.doubleValue() > node.data.doubleValue())
			return searchHelper(node.righTNode, data);

		else
			return true;

	}

	public void remove(E data) {
		if (search(data))
			rooTNode = removeHelper(rooTNode, data);
		else {
			System.err.println(data + " isn't there ");
		}
	}

	private TNode<E> removeHelper(TNode<E> node, E data) {
		if (node == null)
			return node;
		else if (data.doubleValue() < node.data.doubleValue())
			node.lefTNode = removeHelper(node.lefTNode, data);
		else if (data.doubleValue() > node.data.doubleValue())
			node.righTNode = removeHelper(node.righTNode, data);
		else {
			if (node.lefTNode == null && node.righTNode == null) {
				node = null;
			} else if (node.righTNode != null) {
				node.data = successor(node, data);
				node.righTNode = removeHelper(node.righTNode, node.data);
			} else {
				node.data = predecessor(node, data);
				node.lefTNode = removeHelper(node.lefTNode, node.data);
			}

		}
		return node;
	}

	public E maximum() {
		return maximumHelper(rooTNode);
	}

	private E maximumHelper(TNode<E> node) {
		while (node.righTNode != null)
			node = node.righTNode;
		return node.data;
	}

	public E minmum() {
		return minmumHelper(rooTNode);
	}

	private E minmumHelper(TNode<E> node) {
		while (node.lefTNode != null)
			node = node.lefTNode;
		return node.data;
	}

	private E predecessor(TNode<E> node, E data) {
		node = node.lefTNode;
		return maximumHelper(node);
	}

	private E successor(TNode<E> node, E data) {
		node = node.righTNode;
		return minmumHelper(node);
	}

	public int treeHeight() {
		return height(rooTNode);
	}

	public int treeNodeCount() {
		return nodeCount(rooTNode);
	}

	public int treeLeavesCount() {
		return leavesCount(rooTNode);
	}

	private int height(TNode<E> root) {
		if (root == null)
			return 0;
		else
			return 1 + Math.max(height(root.lefTNode), height(root.righTNode));
	}

	private int nodeCount(TNode<E> root) {
		if (root == null)
			return 0;
		else
			return 1 + nodeCount(root.lefTNode) + nodeCount(root.righTNode);
	}

	private int leavesCount(TNode<E> root) {
		if (root == null)
			return 0;
		else if ((root.lefTNode == null) && (root.righTNode == null))
			return 1;
		else
			return leavesCount(root.lefTNode) + leavesCount(root.righTNode);
	}
}
