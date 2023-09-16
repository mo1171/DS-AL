package graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class UnDirectedCycleDetection<T> {
	public boolean hasCycle(List<VertexU<T>> nodes) {
		for (VertexU<T> node : nodes) {
			if (!node.isVisited() && hasCycle(node, null))
				return true;
		}

		return false;

	}

	public boolean hasCycle(VertexU<T> source, VertexU<T> caller) {
		source.setVisited(true);
		for (VertexU<T> neighbour : source.getNeighbors()) {
			if (neighbour.isVisited() && !neighbour.equals(caller)
					|| !neighbour.isVisited() && hasCycle(neighbour, source)) {
//				System.out.println(neighbour);
				return true;
			}
		}

		return false;

	}

	public static void main(String[] args) {
		VertexU<String> vertexA = new VertexU<>("A");
		VertexU<String> vertexB = new VertexU<>("B");
		VertexU<String> vertexC = new VertexU<>("C");
		VertexU<String> vertexD = new VertexU<>("D");
		VertexU<String> vertexE = new VertexU<>("E");
		VertexU<String> vertexF = new VertexU<>("F");
		vertexA.addNeighbor(vertexB);
		vertexB.addNeighbor(vertexA);		vertexA.addNeighbor(vertexC);
		vertexC.addNeighbor(vertexA);
		vertexB.addNeighbor(vertexE);
		vertexE.addNeighbor(vertexB);
		vertexC.addNeighbor(vertexD);
		vertexD.addNeighbor(vertexC);
		vertexD.addNeighbor(vertexE);
		vertexE.addNeighbor(vertexD);
		vertexE.addNeighbor(vertexF);
		vertexF.addNeighbor(vertexE);

		List<VertexU<String>> graph = Arrays.asList(vertexA, vertexB, vertexC, vertexD, vertexE, vertexF);
		System.out.println(new UnDirectedCycleDetection<String>().hasCycle(graph));

	}
}

@Data
class VertexU<T> {
	private final T data;
	private boolean visited;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private HashSet<VertexU<T>> neighbors = new HashSet<>();

	public void addNeighbor(VertexU<T> node) {
		neighbors.add(node);
	}
}