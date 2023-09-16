package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public class FordFulkerson<T> {
	@Getter
	private final List<List<NodeF<T>>> paths = new ArrayList<List<NodeF<T>>>();

	public int maxFlow(NodeF<T> source , NodeF<T> destenation) {
		AtomicInteger maxflow = new AtomicInteger(0) ;
		findAllPaths(source ,destenation , new ArrayList<NodeF<T>>(Collections.singleton(source)) );
		paths.stream()
		.sorted(Comparator.comparingInt(this::minEdgeInPath).reversed()).forEach(path ->
				{
					Integer min = minEdgeInPath(path) ; 
					maxflow.addAndGet(min) ;
					
					IntStream.range(0, path.size()-1).forEach(nodeId -> 
							{
								subFromPathWeights(path, nodeId , min ) ;  
							}
							);
					
				}
				);
		return maxflow.get() ; 
		
	}
	private void subFromPathWeights(List<NodeF<T>> path  ,int nodeId , int min) {
		
		path.get(nodeId).getNeighbors().put(path.get(nodeId+1), getEdgeWeight(nodeId, path) - min) ; 
	}
	private  Integer minEdgeInPath(List<NodeF<T>> path) {
		return IntStream.range(0, path.size()-1).
		mapToObj(nodeId -> getEdgeWeight(nodeId, path)).
		min(Integer::compare).orElse(0);
	}
	
	 
	private Integer getEdgeWeight(Integer nodeId , List<NodeF<T>> path) {
		return path.get(nodeId).getNeighbors().get(path.get(nodeId+1)) ; 
	}

	private void findAllPaths(NodeF<T> source, NodeF<T> destenation , List<NodeF<T>>path) {
		if (source.equals(destenation)) {
			paths.add(new ArrayList<NodeF<T>>(path)) ; 
			return;
		} 
		source.setVisited(true);
			source.getNeighbors().keySet().stream().
			filter(neighbour -> !neighbour.isVisited()).forEach(neighbour -> {
				path.add(neighbour) ; 
				findAllPaths(neighbour, destenation , path) ; 
				path.remove(neighbour) ; 
			});
		source.setVisited(false) ; 
	}
	public static void main(String[] args) {
		  NodeF<String> S = new NodeF<>("S");
	        NodeF<String> A = new NodeF<>("A");
	        NodeF<String> B = new NodeF<>("B");
	        NodeF<String> C = new NodeF<>("C");
	        NodeF<String> D = new NodeF<>("D");
	        NodeF<String> T = new NodeF<>("T");

	        S.setNeighbors(new HashMap<>(Map.of(A, 15, B, 12)));
	        A.setNeighbors(new HashMap<>(Map.of(B, 10, C, 12, D, 1)));
	        B.setNeighbors(new HashMap<>(Map.of(D, 14)));
	        C.setNeighbors(new HashMap<>(Map.of(T, 25)));
	        D.setNeighbors(new HashMap<>(Map.of(C, 10, T, 4)));
	        System.out.print("The Max Flow is: ");
	        System.out.println(new FordFulkerson<String>().maxFlow(S, T));
	        System.out.println("for real");
	}

}

@Data
class NodeF<T> {
	private final T data;
	private boolean visited;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private HashMap<NodeF<T>, Integer> neighbors = new HashMap<NodeF<T>, Integer>() ; 
}
