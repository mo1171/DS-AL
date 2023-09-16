package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class DijkstraAlgorithim<T> {

	void calculateShortestPath(NodeD<T> source) {
		source.setDistance(0);
		HashSet<NodeD<T>> settledNodes = new HashSet<>();
		Queue<NodeD<T>> unSettledNodes = new PriorityQueue<>();
		unSettledNodes.add(source);
		while (!unSettledNodes.isEmpty()) {
			NodeD<T> currentNode = unSettledNodes.poll();
			currentNode.getAdjecentNodes().entrySet().stream().filter(entry -> !settledNodes.contains(entry.getKey()))
					.forEach(entry -> {
						updatePath(entry.getKey(), entry.getValue(), currentNode);
						unSettledNodes.add(entry.getKey());
					});
			settledNodes.add(currentNode);
		}

	}

	private void updatePath(NodeD<T> des, Integer distance, NodeD<T> src) {
		Integer newDistance = src.getDistance() + distance;
		if (des.getDistance() > newDistance) {
			des.setDistance(newDistance);
			des.setShortestPath(Stream.concat(src.getShortestPath()
					.stream(),Stream.of(src)).collect(Collectors.toList()));
		}

	}

	public void printPaths(List<NodeD<T>> list) {
		
		
		list.forEach(node -> {
		String path = node.getShortestPath().stream().map(NodeD::getName)
				.collect(Collectors.joining(" -> ")) ; 
		
			if (path.isBlank())
				System.out.printf("%s : %s \n", node.getName(), node.getDistance());
			else
				System.out.printf("%s -> %s : %s\n", path, node.getName(), node.getDistance());

		}

		);

	}
	
	public static void main(String[] args) {

		NodeD<String> nodeA = new NodeD<>("A");
		NodeD<String> nodeB = new NodeD<>("B");
		NodeD<String> nodeC = new NodeD<>("C");
		NodeD<String> nodeD = new NodeD<>("D");
		NodeD<String> nodeE = new NodeD<>("E");
		NodeD<String> nodeF = new NodeD<>("F");

		nodeA.addAdjacentNode(nodeB, 2);
		nodeA.addAdjacentNode(nodeC, 4);

		nodeB.addAdjacentNode(nodeC, 3);
		nodeB.addAdjacentNode(nodeD, 1);
		nodeB.addAdjacentNode(nodeE, 5);

		nodeC.addAdjacentNode(nodeD, 2);

		nodeD.addAdjacentNode(nodeE, 1);
		nodeD.addAdjacentNode(nodeF, 4);

		nodeE.addAdjacentNode(nodeF, 2);

		DijkstraAlgorithim<String> dijkstra = new DijkstraAlgorithim<>();
		dijkstra.calculateShortestPath(nodeA);
		dijkstra.printPaths(List.of(nodeA, nodeB, nodeC, nodeD, nodeE, nodeF));

	}

}

@Getter
@RequiredArgsConstructor
class NodeD<T> implements Comparable<NodeD<T>> {
	private final String name ;
	@NonNull
	private  T data;
	@Setter
	private int distance = Integer.MAX_VALUE;
@Setter
	private List<NodeD<T>> shortestPath = new LinkedList<>();
//	@lombok.experimental.Tolerate
	public NodeD(String name) {
		this.name = name ; 
	}

	public void addPath(NodeD<T> node) {
		shortestPath.add(node);
	}

	private HashMap<NodeD<T>, Integer> adjecentNodes = new HashMap<>();

	public void addAdjacentNode(NodeD<T> node, Integer distance) {
		this.adjecentNodes.put(node, distance);
	}

	@Override
	public int compareTo(NodeD<T> o) {

		return Integer.compare(this.distance, o.getDistance());
	}

}
