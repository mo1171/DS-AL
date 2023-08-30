package dataStructuresAlgorithims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.naming.SizeLimitExceededException;

public class GraphMatrix<E> {
	private ArrayList<GNode<E>> nodes;
	private HashMap<E, Integer> hashMap;
	private int size = 0;

	public int getSize() {
		return size;
	}

	private int[][] adjMatrix;

	public GraphMatrix(int capacity) {
		this.hashMap = new HashMap<E, Integer>();
		this.nodes = new ArrayList<GNode<E>>();
		this.adjMatrix = new int[capacity][capacity];
	}

	public GraphMatrix() {
		this(10);
	}

	public void addNode(E data) throws SizeLimitExceededException {
		if (size >= adjMatrix.length)
			throw new SizeLimitExceededException("U are out of size for the gragh ");
		this.nodes.add(new GNode<E>(data));
		this.hashMap.put(data, size++);
	}

	public void addEdge(Integer src, Integer dst) {
		this.adjMatrix[src][dst] = 1;
	}

	public void addEdge(E src, E dst) {
		int s = this.hashMap.get(src);
		int d = this.hashMap.get(dst);
		addEdge(s, d);
	}

	public boolean checkEdge(Integer src, Integer dst) {
		return this.adjMatrix[src][dst] == 1 ? true : false;
	}

	public boolean checkEdge(E src, E dst) {
		int s = this.hashMap.get(src);
		int d = this.hashMap.get(dst);
		return checkEdge(s, d);
	}

	public void printGraph() {
		System.out.print(" ");
		for (GNode<E> val : nodes) {

			System.out.print(" " + val.data);
		}
		Iterator<GNode<E>> iterator = nodes.iterator();
		System.out.println();
		for (int i = 0; i < adjMatrix.length && iterator.hasNext(); i++) {
			GNode<E> data = iterator.next();
			System.out.print(data.data);
			for (int j = 0; j < adjMatrix[i].length; j++) {
				System.out.print(" " + this.adjMatrix[i][j]);
			}
			System.out.println();
		}
	}

	public void depthFirstSearch(E src) {
		int s = this.hashMap.get(src);
		depthFirstSearch(s);
	}

	public void depthFirstSearch(Integer src) {
		boolean[] vis = new boolean[adjMatrix.length];
		dfsHelper(src, vis);
	}

	private void dfsHelper(int src, boolean[] vis) {
		if (vis[src])
			return;
		vis[src] = true;
		System.out.println(nodes.get(src).data + " is visited");
		for (int i = 0; i < vis.length; i++) {
			if (adjMatrix[src][i] == 1)
				dfsHelper(i, vis);
		}
		return;
	}

	public void breadthFirstSearch(E src) {
		int s = this.hashMap.get(src);
		breadthFirstSearch(s);
	}

	public void breadthFirstSearch(Integer src) {
		Queue<Integer> queue = new LinkedList<Integer>();
		boolean[] vis = new boolean[adjMatrix.length];
		queue.offer(src);
		vis[src] = true;
		while (!queue.isEmpty()) {

			src = queue.poll();
			System.out.println(nodes.get(src).data + " is visited ");
			for (int i = 0; i < vis.length; i++) {
				if (adjMatrix[src][i] == 1 && !vis[i]) {
					queue.offer(i);
					vis[i] = true;
				}

			}

		}

	}

}