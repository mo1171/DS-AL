package dataStructuresAlgorithims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class GraphList<E> {
	private ArrayList<LinkedList<GNode<E>>> nodesArrayList;
	private HashMap<E, Integer> hashMap;
	private int size = 0;

	public int getSize() {
		return size;
	}

	public GraphList() {
		nodesArrayList = new ArrayList<LinkedList<GNode<E>>>();
		hashMap = new HashMap<E, Integer>();
	}

	public void addNode(E data) {
		LinkedList<GNode<E>> uniqueNode = new LinkedList<GNode<E>>();
		uniqueNode.add(new GNode<E>(data, size));
		nodesArrayList.add(uniqueNode);
		this.hashMap.put(data, size++);
	}

	public void addEdge(Integer src, Integer dst) {
		nodesArrayList.get(src).add(nodesArrayList.get(dst).get(0));
	}

	public void addEdge(E src, E dst) {

		int s = hashMap.get(src);
		int d = hashMap.get(dst);
		addEdge(s, d);
	}

	public boolean checkEdge(Integer src, Integer dst) {
		LinkedList<GNode<E>> srcLinkedList = nodesArrayList.get(src);
		GNode<E> dstGNode = nodesArrayList.get(dst).get(0);
		for (GNode<E> node : srcLinkedList) {
			if (node.equals(dstGNode)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkEdge(E src, E dst) {
		int s = hashMap.get(src);
		int d = hashMap.get(dst);
		return checkEdge(s, d);
	}

	public void printGraph() {
		for (Iterator<LinkedList<GNode<E>>> iterator = nodesArrayList.iterator(); iterator.hasNext();) {
			LinkedList<GNode<E>> linkedList = (LinkedList<GNode<E>>) iterator.next();
			for (Iterator<GNode<E>> iterator2 = linkedList.iterator(); iterator2.hasNext();) {
				GNode<E> gNode = (GNode<E>) iterator2.next();
				System.out.print(gNode.data + " --> ");
			}
			System.out.println();
		}
	}

	public void depthFirstSearch(E src) {
		int s = this.hashMap.get(src);
		depthFirstSearch(s);
	}

	public void depthFirstSearch(Integer src) {
		boolean[] vis = new boolean[nodesArrayList.size()];
		dfsHelper(src, vis);
	}

	private void dfsHelper(int src, boolean[] vis) {
		if (vis[src])
			return;
		vis[src] = true;
		System.out.println(nodesArrayList.get(src).get(0).data + " is visited");
		LinkedList<GNode<E>> curLinkedList = nodesArrayList.get(src);
		for (GNode<E> gNode : curLinkedList) {
			dfsHelper(gNode.idx, vis);
		}

		return;
	}

	public void breadthFirstSearch(E src) {
		int s = this.hashMap.get(src);
		breadthFirstSearch(s);
	}

	public void breadthFirstSearch(Integer src) {
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] vis = new boolean[nodesArrayList.size()];
		queue.offer(src);
		vis[src] = true;
		while (!queue.isEmpty()) {

			src = queue.poll();
			System.out.println(nodesArrayList.get(src).get(0).data + " is visited ");
			LinkedList<GNode<E>> curLinkedList = nodesArrayList.get(src);
			for (GNode<E> node : curLinkedList) {
				if (!vis[node.idx]) {
					queue.offer(node.idx);
					vis[node.idx] = true;
				}
			}

		}

	}

}
