package trees;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
 class VisualRedBlackTree<T extends Comparable<T>> extends JPanel implements TreeRB<T> {

	private RBNode<T> root;

//  ------------------- visual block -------------------------------------------------
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawNode(g, getWidth() / 2, 50, root, getWidth() / 4);
	}

	private void drawNode(Graphics g, int x, int y, RBNode<T> node, int levelWidth) {
		if (node != null) {
			g.setColor(node.getColor());

			int ovalWidth = 30;
			int ovalHeight = 30;
			int ovalX = x - ovalWidth / 2;
			int ovalY = y - ovalHeight / 2;
			g.fillRoundRect(ovalX, ovalY, ovalWidth, ovalHeight, 20, 20);
			g.setColor(node.getColor());
			g.drawString(node.getData().toString(), ovalX + 7, ovalY + ovalHeight + 15);
			// some string for debugging 
//			if (node != root)
//				g.drawString(node.getParent().getData().toString(), ovalX + 7, ovalY - 15);
//			if(node.getLeftChild()!=null)
//			g.drawString(node.getLeftChild().getData().toString(), ovalX - 30, ovalY + 15);
//			if(node.getRightChild()!= null)
//			g.drawString(node.getRightChild().getData().toString(), ovalX + 40, ovalY + 15);
			if (node.getLeftChild() != null) {
				var child = node.getLeftChild();
				int childX = x - levelWidth;
				int childY = y + 100;
				drawCurve(g, x, y, childX, childY);
				drawNode(g, childX, childY, child, levelWidth / 2);
			}
			if (node.getRightChild() != null) {
				var child = node.getRightChild();
				int childX = x + levelWidth;
				int childY = y + 100;
				drawCurve(g, x, y, childX, childY);
				drawNode(g, childX, childY, child, levelWidth / 2);
			}
		}
	}

	private void drawCurve(Graphics g, int x1, int y1, int x2, int y2) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);

		int controlX = (x1 + x2) / 2;
		int controlY = (y1 + y2) / 2 - 30;

		QuadCurve2D curve = new QuadCurve2D.Float(x1, y1, controlX, controlY, x2, y2);
		g2d.draw(curve);
	}

//	------------------------ end of visual block ----------------------------------
	@Override
	public TreeRB<T> insert(T data) {
		RBNode<T> node = new RBNode<>(data);
		root = insert(root, node);
		fixInsertionProcess(node);
		return this;
	}

	private RBNode<T> insert(RBNode<T> node, RBNode<T> nodeToInsert) {
		if (node == null) {
			return nodeToInsert;
		}
		if (nodeToInsert.getData().compareTo(node.getData()) < 0) {
			node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
			node.getLeftChild().setParent(node);
		} else if (nodeToInsert.getData().compareTo(node.getData()) > 0) {
			node.setRightChild(insert(node.getRightChild(), nodeToInsert));
			node.getRightChild().setParent(node);
		}
		return node;
	}

	private void fixInsertionProcess(RBNode<T> node) {
		RBNode<T> parent = node.getParent();
		if (node != root && parent.getColor() == RED) {
			RBNode<T> grandParent = node.getParent().getParent();
			RBNode<T> uncle = parent.isLeftChild() ? grandParent.getRightChild() : grandParent.getLeftChild();
			if (uncle != null && uncle.getColor() == RED) { // Recoloring
				handleRecoloring(parent, uncle, grandParent);
			} else if (parent.isLeftChild()) { // Left-Left or Left-Right situation
				handleLeftSituations(node, parent, grandParent);
			} else if (!parent.isLeftChild()) { // Right-Right or Right-Left situation
				handleRightSituations(node, parent, grandParent);
			}
		}
		root.setColor(BLACK); // Color the root node black
	}

	private void handleRightSituations(RBNode<T> node, RBNode<T> parent, RBNode<T> grandParent) {
		if (node.isLeftChild()) {
			parent = rotateRight(parent);
		}
		parent.flipColor();
		grandParent.flipColor();
		rotateLeft(grandParent);
//        if(node.isLeftChild())
//        	recolorAndRotate(grandParent);
//        recolorAndRotate(node.isLeftChild() ? grandParent : parent);
	}

	private void handleLeftSituations(RBNode<T> node, RBNode<T> parent, RBNode<T> grandParent) {
		if (!node.isLeftChild()) {
			parent = rotateLeft(parent);
		}
		parent.flipColor();
		grandParent.flipColor();
		rotateRight(grandParent);
//        if(!node.isLeftChild())
//        	recolorAndRotate(grandParent);
//        recolorAndRotate(node.isLeftChild() ? parent : grandParent);
	}

	private void handleRecoloring(RBNode<T> parent, RBNode<T> uncle, RBNode<T> grandParent) {
		uncle.flipColor();
		parent.flipColor();
		grandParent.flipColor();
		fixInsertionProcess(grandParent);
	}

	private RBNode<T> rotateRight(RBNode<T> node) {
		RBNode<T> leftNode = node.getLeftChild();
		node.setLeftChild(leftNode.getRightChild());
		if (node.getLeftChild() != null) {
			node.getLeftChild().setParent(node);
		}
		leftNode.setRightChild(node);
		leftNode.setParent(node.getParent());
		updateChildrenOfParentNode(node, leftNode);
		node.setParent(leftNode);
		return leftNode;
	}

	private RBNode<T> rotateLeft(RBNode<T> node) {
		RBNode<T> rightNode = node.getRightChild();
		node.setRightChild(rightNode.getLeftChild());
		if (node.getRightChild() != null) {
			node.getRightChild().setParent(node);
		}
		rightNode.setLeftChild(node);
		updateChildrenOfParentNode(node, rightNode);
		node.setParent(rightNode);
		return rightNode;
	}

	private void updateChildrenOfParentNode(RBNode<T> node, RBNode<T> tempNode) {
		if (node.getParent() == null) {
			root = tempNode;
		} else if (node.isLeftChild()) {
			node.getParent().setLeftChild(tempNode);
		} else {
			node.getParent().setRightChild(tempNode);
		}
		if (tempNode != null) {
			tempNode.setParent(node.getParent());
		}
	}

	@Override
	public void traverse() {
		traverseInOrder(root);
	}

	private void traverseInOrder(RBNode<T> node) {
		if (node != null) {
			traverseInOrder(node.getLeftChild());
			System.out.println(node);
			traverseInOrder(node.getRightChild());
		}
	}

	@Override
	public RBNode<T> getMax() {
		if (isEmpty()) {
			return null;
		}
		return getMax(root);
	}

	private RBNode<T> getMax(RBNode<T> node) {
		if (node.getRightChild() != null) {
			return getMax(node.getRightChild());
		}
		return node;
	}

	@Override
	public RBNode<T> getMin() {
		if (isEmpty()) {
			return null;
		}
		return getMin(root);
	}

	private RBNode<T> getMin(RBNode<T> node) {
		if (node.getLeftChild() != null) {
			return getMin(node.getLeftChild());
		}
		return node;
	}

	@Override
	public boolean isEmpty() {
		return root == null;
	}

	private RBNode<T> movedUpNode = null;
	private Color deletedNodeColor = null;
@Override
	public TreeRB<T> delete(T data) {

		root = delete(data, root);
		if (deletedNodeColor == BLACK) {
			fixRedBlackPropertiesAfterDelete(movedUpNode);
		}
		if (movedUpNode != null && movedUpNode.getClass() == NilNode.class) {
			updateChildrenOfParentNode(movedUpNode, null);
		}
		return this ; 
	}
	private RBNode<T> delete(T data, RBNode<T> node) {
		if (node == null) {
			return null;
		}
		if (data.compareTo(node.getData()) < 0) {
			node.setLeftChild(delete(data, node.getLeftChild()));
			if (node.getLeftChild() != null)
				node.getLeftChild().setParent(node);
		} else if (data.compareTo(node.getData()) > 0) {
			node.setRightChild(delete(data, node.getRightChild()));
			if (node.getRightChild() != null)
				node.getRightChild().setParent(node);
		} else {
			deletedNodeColor = node.getColor();
			// One Child or Leaf Node (no children)
			if (node.getLeftChild() == null & node.getRightChild() == null) {
				
				movedUpNode = node.getColor() == BLACK ? new NilNode<T>() : null;
				return movedUpNode;
			} else if (node.getLeftChild() == null) {
				
				movedUpNode = node.getRightChild();
				return movedUpNode;
			} else if (node.getRightChild() == null) {
				
				movedUpNode = node.getLeftChild();
				return movedUpNode;
			}
			// Two Children
//            Color nodeColor = node.getColor() ; 
			RBNode<T> predecessorNode = getMax(node.getLeftChild());
			node.setData(predecessorNode.getData());
			deletedNodeColor = predecessorNode.getColor();
			node.setLeftChild(delete(node.getData(), node.getLeftChild()));
			if (node.getLeftChild() != null)
				node.getLeftChild().setParent(node);
			movedUpNode = node.getLeftChild(); 
//			updateChildrenOfParentNode(predecessorNode, movedUpNode);
//            node.setColor(nodeColor); 
		}
		return node ; 
	}


	private void fixRedBlackPropertiesAfterDelete(RBNode<T> node) {
		// Case 1: Examined node is root, end of recursion
		if (node == root) {
			// Uncomment the following line if you want to enforce black roots (rule 2):
			 node.setColor(BLACK); 
			return;
		}

		RBNode<T> sibling = node.getSibling();

		// Case 2: Red sibling
		if (sibling.getColor() == RED) {
			handleRedSibling(node, sibling);
			sibling = node.getSibling(); // Get new sibling for fall-through to cases 3-6
		}

		// Cases 3+4: Black sibling with two black children
		if (isBlack(sibling.getLeftChild()) && isBlack(sibling.getRightChild())) {
			sibling.setColor(RED);

			// Case 3: Black sibling with two black children + red parent
			if (node.getParent().getColor() == RED) {
				node.getParent().setColor(BLACK);
			}

			// Case 4: Black sibling with two black children + black parent
			else {
				fixRedBlackPropertiesAfterDelete(node.getParent());
			}
		}

		// Case 5+6: Black sibling with at least one red child
		else {
			handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
		}
	}
	private void handleRedSibling(RBNode<T> node, RBNode<T> sibling) {
		  // Recolor...
		  sibling.setColor(BLACK);
		  node.getParent().setColor(RED);

		  // ... and rotate
		  if (node == node.getParent().getLeftChild()) {
		    rotateLeft(node.getParent());
		  } else {
		    rotateRight(node.getParent());
		  }
		}
	private void handleBlackSiblingWithAtLeastOneRedChild(RBNode<T> node, RBNode<T> sibling) {
		  boolean nodeIsLeftChild = node.isLeftChild() ; 

		  // Case 5: Black sibling with at least one red child + "outer nephew" is black
		  // --> Recolor sibling and its child, and rotate around sibling
		  if (nodeIsLeftChild &&isBlack( sibling.getRightChild())) {
		    sibling.getLeftChild().setColor(BLACK);
		    sibling.setColor(RED);
		    rotateRight(sibling);
		    sibling = node.getParent().getRightChild();
		  } else if (!nodeIsLeftChild && isBlack(sibling.getLeftChild())) {
		    sibling.getRightChild().setColor(BLACK); 
		    sibling.setColor(RED);
		    rotateLeft(sibling);
		    sibling = node.getParent().getLeftChild() ;
		  }

		  // Fall-through to case 6...

		  // Case 6: Black sibling with at least one red child + "outer nephew" is red
		  // --> Recolor sibling + parent + sibling's child, and rotate around parent
//		  sibling.setColor(node.getParent().getColor());
		  sibling.setColor(node.getParent().getColor());
		  node.getParent().setColor(BLACK);
		  if (nodeIsLeftChild) {
		    sibling.getRightChild().setColor(BLACK);
		    rotateLeft(node.getParent());
		  } else {
		    sibling.getLeftChild().setColor(BLACK);
		    rotateRight(node.getParent());
		  }
		}

	private static class NilNode<T extends Comparable<T>> extends RBNode<T> {
		private NilNode() {
			super(null);
			this.setColor(BLACK);
		}
	}

	
	public RBNode<T> search(T data) {
		return searchHelper(root, data);
	}

	private RBNode<T> searchHelper(RBNode<T> node, T data) {
		if (node == null)
			return null;

		else if (data.compareTo(node.getData()) < 0)
			return searchHelper(node.getLeftChild(), data);

		else if (data.compareTo(node.getData()) > 0)
			return searchHelper(node.getRightChild(), data);

		else
			return node;

	}
	public  boolean isBlack(RBNode<T> node) {
		return node == null || node.getColor() == BLACK;
	}
}

public class RedBlackTree {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> createAndShowGUI());
	}

	private static void createAndShowGUI() {
		TreeRB<Integer> redBlackTree = new VisualRedBlackTree<>();

		redBlackTree.insert(10).insert(20).insert(40).insert(50).insert(25).insert(60).insert(80).insert(85)
        .insert(90).insert(30).insert(15).insert(75).insert(100).insert(55).insert(45).insert(26) ; 
		
		redBlackTree.delete(85).delete(10);
		JFrame frame = new JFrame("Tree Visualization");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);

		frame.add((Component) redBlackTree);

		frame.setVisible(true);
	}
}
