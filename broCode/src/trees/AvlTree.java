package trees;


public class AvlTree<T extends Comparable<T>> implements Tree<T> {

	private AVlNode<T> root;

	@Override
	public Tree<T> insert(T data) {
		root = insert(data, root);
		return this;
	}

	private AVlNode<T> insert(T data, AVlNode<T> node) {
		if (node == null) {
			return new AVlNode<>(data);
		}
		if (data.compareTo(node.getData()) < 0) {
			node.setLeftChild(insert(data, node.getLeftChild()));
		} else if (data.compareTo(node.getData()) > 0) {
			node.setRightChild(insert(data, node.getRightChild()));
		} else
			return node;
		updateHeight(node);
		return applyRotation(node);
	}

	private AVlNode<T> applyRotation(AVlNode<T> node) {
		int balance = balance(node);
		if (balance > 1) {
			if (balance(node.getLeftChild()) < 0)
				node.setLeftChild(leftRotation(node.getLeftChild()));
			return rigthRotation(node);
		} else if (balance < -1) {
			if (balance(node.getRightChild()) > 0)
				node.setRightChild(rigthRotation(node.getLeftChild()));
			return leftRotation(node);
		}
		return node ; 
	}

	private AVlNode<T> rigthRotation(AVlNode<T> node) {
		AVlNode<T> leftchild  =  node.getLeftChild() ; 
		AVlNode<T> centerNode = leftchild.getRightChild() ; 
		leftchild.setRightChild(node); 
		node.setLeftChild(centerNode); 
		updateHeight(node); 
		updateHeight(leftchild);
		return leftchild ; 
	}

	private AVlNode<T> leftRotation(AVlNode<T> node) {
		AVlNode<T> rigthchild  =  node.getRightChild() ; 
		AVlNode<T> centerNode = rigthchild.getLeftChild() ; 
		rigthchild.setLeftChild(node); 
		node.setRightChild(centerNode); 
		updateHeight(node); 
		updateHeight(rigthchild);
		return rigthchild ; 
	}

	private int balance(AVlNode<T> node) {
		return node != null ? heigth(node.getLeftChild()) - heigth(node.getRightChild()) : 0;

	}

	private void updateHeight(AVlNode<T> node) {
		int childHeigth = Math.max(heigth(node.getLeftChild()), heigth(node.getRightChild()));
		node.setHeigth(childHeigth + 1);
	}

	private int heigth(AVlNode<T> node) {

		return node != null ? node.getHeigth() : 0;
	}

	
	public void delete(T data) {
		root = delete(data, root);
	}

	private AVlNode<T> delete(T data, AVlNode<T> node) {
		if (node == null) {
			return null;
		}
		if (data.compareTo(node.getData()) < 0) {
			node.setLeftChild(delete(data, node.getLeftChild()));
		} else if (data.compareTo(node.getData()) > 0) {
			node.setRightChild(delete(data, node.getRightChild()));
		} else {
			// One child or No children
			if (node.getLeftChild() == null) {
				return node.getRightChild();
			} else if (node.getRightChild() == null) {
				return node.getLeftChild();
			}
			// Two children
			node.setData(getMax(node.getLeftChild()));
			node.setLeftChild(delete(node.getData(), node.getLeftChild()));
		}
		updateHeight(node);
		return applyRotation(node);
		 
		 
	}

	@Override
	public void traverse() {
		traverseInOrder(root);
	}

	private void traverseInOrder(AVlNode<T> node) {
		if (node == null) {
			return;
		}
		traverseInOrder(node.getLeftChild());
		System.out.println(node);
		traverseInOrder(node.getRightChild());
	}

	 @Override
	    public T getMax() {
	        if (isEmpty()) {
	            return null;
	        }
	        return getMax(root);
	    }

	    private T getMax(AVlNode<T> node) {
	        if (node.getRightChild() != null) {
	            return getMax(node.getRightChild());
	        }
	        return node.getData();
	    }

	    @Override
	    public T getMin() {
	        if (isEmpty()) {
	            return null;
	        }
	        return getMin(root);
	    }

	    private T getMin(AVlNode<T> node) {
	        if (node.getLeftChild() != null) {
	            return getMin(node.getLeftChild());
	        }
	        return node.getData();
	    }

	@Override
	public boolean isEmpty() {
		return root == null;
	}

}
