package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

public class PrimAlgorithsm<T> {
	private final List<VertexP<T>> graph;

	public PrimAlgorithsm(List<VertexP<T>> graph) {
		this.graph = graph;
	}

	public void run() {
		if (!graph.isEmpty())
			graph.get(0).setVisited(true);
		while (graph.stream().anyMatch(vertex -> !vertex.isVisited())) {
			graph.stream().filter(vertex -> vertex.isVisited()).map(VertexP::getNeighbours)
					.flatMap(list -> list.stream()).filter(neighbour -> !neighbour.isVisited())
					.min(Comparator.comparingInt(neighbour -> neighbour.getEdge().getWeight())).ifPresent(candidate -> {
						candidate.getVertex().setVisited(true);
						candidate.getEdge().setIncluded(true);
						candidate.getEdge().setSourceData(candidate.getVertex().getData()) ;  
					});
		}
		printMinimunSpanningTree();

	}

	public void printMinimunSpanningTree() {
		graph.stream().forEach(vertex -> {
			System.out.print(vertex.getData()+"-> ");
			System.out.printf("%s", vertex.getNeighbours().stream().filter(neighbour -> neighbour.getVertex().isVisited()&& neighbour.getEdge().isIncluded())
			.map(neighbour -> neighbour.getVertex().getData()+"|" + neighbour.getEdge().getWeight())
			.collect(Collectors.joining(" -- ")));
			System.out.println();

	});
		 
		 int minWeight = graph.stream().map(VertexP::getNeighbours).
				flatMap(Collection::stream).map(Neighbour::getEdge)
				.filter(edge -> edge.isIncluded()).distinct().map(EdgeP :: getWeight).reduce(0 , Integer::sum) ; 
		 
		System.out.println("the Minimum spannig tree weight is : " + minWeight);
	}
	public static void main(String[] args) {
		VertexP<String> vertexA = new VertexP<>("A");
        VertexP<String> vertexB = new VertexP<>("B");
        VertexP<String> vertexC = new VertexP<>("C");
        VertexP<String> vertexD = new VertexP<>("D");
        VertexP<String> vertexE = new VertexP<>("E");
        VertexP<String> vertexF = new VertexP<>("F");

        EdgeP<String> edgeAB = new EdgeP<String>(4);
        EdgeP<String> edgeAC = new EdgeP<String>(4);
        EdgeP<String> edgeBC = new EdgeP<String>(2);
        EdgeP<String> edgeCD = new EdgeP<String>(3);
        EdgeP<String> edgeCF = new EdgeP<String>(4);
        EdgeP<String> edgeCE = new EdgeP<String>(2);
        EdgeP<String> edgeDF = new EdgeP<String>(3);
        EdgeP<String> edgeEF = new EdgeP<String>(3);

        vertexA.addNeighbor(vertexB, edgeAB);
        vertexA.addNeighbor(vertexC, edgeAC);

        vertexB.addNeighbor(vertexA, edgeAB);
        vertexB.addNeighbor(vertexC, edgeBC);

        vertexC.addNeighbor(vertexA, edgeAC);
        vertexC.addNeighbor(vertexB, edgeBC);
        vertexC.addNeighbor(vertexE, edgeCE);
        vertexC.addNeighbor(vertexD, edgeCD);
        vertexC.addNeighbor(vertexF, edgeCF);

        vertexD.addNeighbor(vertexC, edgeCD);
        vertexD.addNeighbor(vertexF, edgeDF);

        vertexE.addNeighbor(vertexC, edgeCE);
        vertexE.addNeighbor(vertexF, edgeEF);

        vertexF.addNeighbor(vertexD, edgeDF);
        vertexF.addNeighbor(vertexC, edgeCF);
        vertexF.addNeighbor(vertexE, edgeEF);

        new PrimAlgorithsm<>(Arrays.asList(vertexA, vertexB, vertexC, vertexD, vertexE, vertexF)).run();
	}
}

@Data
class VertexP<T> {
	private final T data;
	private boolean visited;
	private List<Neighbour<T>> neighbours = new ArrayList<Neighbour<T>>();

	public void addNeighbor(VertexP<T> vertexP , EdgeP<T> edge) {
		neighbours.add(new Neighbour<T>(vertexP, edge));
	}
}

@Data
class Neighbour<T> {
	private final VertexP<T> vertex;
	private final EdgeP<T> edge;

	public boolean isVisited() {
		return vertex.isVisited() || edge.isIncluded();
	}
}

@Data
class EdgeP<T> {
	private boolean isIncluded;
	
	private T sourceData ;  
//	private VertexP<T> destenation ; 
	
	private final Integer weight;
}