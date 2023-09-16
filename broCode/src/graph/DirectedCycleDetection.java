package graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class DirectedCycleDetection<T> {
	public boolean hasCycle(List<Vertex<T>> nodes) {
		for (Vertex<T> node : nodes) {
			if (!node.isVisited() && hasCycle(node))
			return true;
		}

		return false;

	}

	public boolean hasCycle(Vertex<T> source) {
		source.setBeingVisited(true);
		for (Vertex<T> neighbour : source.getNeighbors()) {
			if (neighbour.isBeingVisited() || !neighbour.isVisited() && hasCycle(neighbour)) {
//				System.out.println(neighbour);
				return true;
			}
		}
		source.setBeingVisited(false);
		source.setVisited(true);
		return false;

	}

	public static void main(String[] args) {
		Vertex<String> vertexA = new Vertex<>("A");
		Vertex<String> vertexB = new Vertex<>("B");
		Vertex<String> vertexC = new Vertex<>("C");
		Vertex<String> vertexD = new Vertex<>("D");
		Vertex<String> vertexE = new Vertex<>("E");
		Vertex<String> vertexF = new Vertex<>("F");
		vertexA.addNeighbor(vertexB);
		vertexA.addNeighbor(vertexC);
		vertexB.addNeighbor(vertexC);
		vertexB.addNeighbor(vertexD);
		vertexB.addNeighbor(vertexE);
		vertexC.addNeighbor(vertexD);
		vertexD.addNeighbor(vertexE);
		vertexE.addNeighbor(vertexF);
		vertexF.addNeighbor(vertexD);
		List<Vertex<String>> graph = Arrays.asList(vertexA, vertexB, vertexC, vertexD, vertexE, vertexF);
		System.out.println(new DirectedCycleDetection<String>().hasCycle(graph));

	}
}

@Data
class Vertex<T> {
	private final T data;
	private boolean beingVisited;
	private boolean visited;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private HashSet<Vertex<T>> neighbors = new HashSet<>();

	public void addNeighbor(Vertex<T> node) {
		neighbors.add(node);
	}
}