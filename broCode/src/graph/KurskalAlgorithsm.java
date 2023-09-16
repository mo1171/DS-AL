package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;


import lombok.Data;

public class KurskalAlgorithsm<T>{
	private Queue<Edge<T> > graph ; 
	private List<VertexU<T> > vertices ; 
	private int nVertices ; 
	KurskalAlgorithsm(List<Edge<T>> graph ) {
		this.graph = new PriorityQueue<Edge<T>>(graph) ;
		this.vertices = getVertices() ; 
		this.nVertices = this.vertices.size() ; 

	}
	public void run() { 
		List<Edge<T> > spannigTree = new ArrayList<Edge<T>>() ; 
		do {
			Edge<T> edge = graph.poll() ; 
			restoreGraph(Stream.concat(spannigTree.stream(),Stream.of(edge) )) ; 
			if(!new UnDirectedCycleDetection<T>().hasCycle(vertices)) {
				spannigTree.add(edge) ; 
			}
		} while (spannigTree.size() < nVertices -1 );
		printMinimumSpanningTree(spannigTree) ; 
	}
	
	
	
private void printMinimumSpanningTree(List<Edge<T>> spannigTree) {
	int minWeight = spannigTree.stream().map(Edge::getWeight).reduce(0, Integer::sum) ; 
		spannigTree.forEach(System.out::println);
		System.out.println("the Minimum weight Spanning tree is : " + minWeight );
		
	}
private void restoreGraph(Stream<Edge<T>> tempTree) {
		vertices.forEach(vertix ->{
			vertix.setVisited(false) ; 
			vertix.setNeighbors(new HashSet<VertexU<T>>()) ; 
		});
		tempTree.forEach(edge ->  {
		edge.getSource().addNeighbor(edge.getDestenation()) ; 
			edge.getDestenation().addNeighbor(edge.getSource()) ; 
		});
		
	}
private List<VertexU<T>> getVertices() {
	return 	Stream.concat(graph.stream().
			map(Edge::getSource), graph.stream().map(Edge::getDestenation))
		.distinct().toList() ; 
	}




public static void main(String[] args) {
	   VertexU<String> vertexA = new VertexU<>("A");
       VertexU<String> vertexB = new VertexU<>("B");
       VertexU<String> vertexC = new VertexU<>("C");
       VertexU<String> vertexD = new VertexU<>("D");
       VertexU<String> vertexE = new VertexU<>("E");
       VertexU<String> vertexF = new VertexU<>("F");
       VertexU<String> vertexG = new VertexU<>("G");

       List<Edge<String>> graph1 = Arrays.asList(
               new Edge<>(vertexA, vertexB, 4),
               new Edge<>(vertexA, vertexC, 4),
               new Edge<>(vertexB, vertexC, 2),
               new Edge<>(vertexC, vertexD, 3),
               new Edge<>(vertexC, vertexF, 4),
               new Edge<>(vertexC, vertexE, 2),
               new Edge<>(vertexD, vertexF, 3),
               new Edge<>(vertexE, vertexF, 3)
       );

       List<Edge<String>> graph2 = Arrays.asList(
               new Edge<>(vertexA, vertexB, 7),
               new Edge<>(vertexA, vertexD, 5),
               new Edge<>(vertexB, vertexC, 8),
               new Edge<>(vertexB, vertexE, 7),
               new Edge<>(vertexB, vertexD, 9),
               new Edge<>(vertexC, vertexE, 5),
               new Edge<>(vertexD, vertexE, 15),
               new Edge<>(vertexD, vertexF, 6),
               new Edge<>(vertexE, vertexF, 8),
               new Edge<>(vertexE, vertexG, 9),
               new Edge<>(vertexF, vertexG, 11)
       );

       List<Edge<String>> graph3 = Arrays.asList(
               new Edge<>(vertexA, vertexB, 5),
               new Edge<>(vertexA, vertexC, 2),
               new Edge<>(vertexB, vertexC, 3),
               new Edge<>(vertexB, vertexD, 8),
               new Edge<>(vertexB, vertexE, 7),
               new Edge<>(vertexD, vertexE, 6),
               new Edge<>(vertexC, vertexE, 3)
       );

       List<Edge<String>> graph4 = Arrays.asList(
               new Edge<>(vertexA, vertexB, 5),
               new Edge<>(vertexA, vertexC, 2),
               new Edge<>(vertexB, vertexC, 4),
               new Edge<>(vertexB, vertexD, 8),
               new Edge<>(vertexB, vertexE, 7),
               new Edge<>(vertexD, vertexE, 2),
               new Edge<>(vertexC, vertexE, 3)
       );

       new KurskalAlgorithsm<>(graph1).run();
       new KurskalAlgorithsm<>(graph2).run();
       new KurskalAlgorithsm<>(graph3).run();
       new KurskalAlgorithsm<>(graph4).run();
}
}
@Data
class Edge<T> implements Comparable<Edge<T>> {
	private final VertexU<T> destenation;
	private final VertexU<T> source;
	private final Integer weight;

	@Override
	public int compareTo(Edge<T> o) {
		return Integer.compare(this.weight, o.getWeight());
	}

}