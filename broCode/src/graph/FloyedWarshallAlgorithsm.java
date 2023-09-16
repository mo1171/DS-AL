package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class FloyedWarshallAlgorithsm<T> {
	public static final int INF = Integer.MAX_VALUE;
	private final int nOfVerteces;
	private int[][] transitiveMatrix;
	private String[][] successorMatrix;
	private Map<Integer, Character> workingMap = new HashMap<Integer, Character>();

	public FloyedWarshallAlgorithsm(int[][] transitiveMatrix, String[][] successorMatrix) {
		this.nOfVerteces = transitiveMatrix.length;
		this.transitiveMatrix = transitiveMatrix;
		this.successorMatrix = successorMatrix;
		for (int i = 0; i < nOfVerteces; i++) {
			this.workingMap.put(i, (char) (i + 65));
		}
	}

	public void run() {
		for (int intermediate = 0; intermediate < nOfVerteces; intermediate++) {
			for (int start = 0; start < nOfVerteces; start++) {
				for (int end = 0; end < nOfVerteces; end++) {
					int newWeight = getNewWeight(transitiveMatrix, start, intermediate, end);
					if (newWeight < transitiveMatrix[start][end]) {
						transitiveMatrix[start][end] = newWeight;
						successorMatrix[start][end] = successorMatrix[start][intermediate];
					}
				}
			}
		}
		for (int i = 0; i < nOfVerteces; i++) {
			if (transitiveMatrix[i][i] < 0)
				throw new RuntimeException("You have negative cycle ");
		}
		printTransitiveMatrix();
		System.out.println();
		printSuccessorMatrix();
	}

	private void printSuccessorMatrix() {
		IntStream.range(0, nOfVerteces).forEach(start -> {
			IntStream.range(0, nOfVerteces).forEach(end -> {
				System.out.print(successorMatrix[start][end] + "  ");
			});
			System.out.println();
		});

	}

	public void printTransitiveMatrix() {
		IntStream.range(0, nOfVerteces).forEach(start -> {
			IntStream.range(0, nOfVerteces).forEach(end -> {
				System.out
						.print(transitiveMatrix[start][end] != INF ? transitiveMatrix[start][end] + "  " : "oo" + "  ");
			});
			System.out.println();
		});
	}

	private int getNewWeight(int[][] transitiveMatrix, int start, int intermediate, int end) {
		return transitiveMatrix[start][intermediate] == INF || transitiveMatrix[intermediate][end] == INF ? INF
				: transitiveMatrix[start][intermediate] + transitiveMatrix[intermediate][end];

	}

	public void getShortesPath(char src, char des) throws Exception {
		if(successorMatrix[(int) src - 65 ][(int) des -65] == "-")
			throw new Exception("there is no path ") ; 
		System.out.print(src + " -> ");
		getShortestPath((int) (src) - 65, (int) (des) - 65);
		System.out.println(des);
	}

	private void getShortestPath(int src, int des) {
		if (Character.valueOf(successorMatrix[src][des].charAt(0)).equals(workingMap.get(des))) {

			return;
		}
		System.out.print(successorMatrix[src][des] + " -> ");
		getShortestPath((int) successorMatrix[src][des].charAt(0) - 65, des);

	}

	public static void main(String[] args) throws Exception {

		int[][] weightsMatrix = { { 0, 3, 2, INF, INF }, { INF, 0, INF, 7, INF }, { INF, INF, 0, INF, 4 },
				{ INF, INF, INF, 0, 6 }, { INF, 4, 5, INF, 0 } };
		String[][] successorsMatrix = { { "-", "B", "C", "-", "-" }, { "-", "-", "-", "D", "-" },
				{ "-", "-", "-", "-", "E" }, { "-", "-", "-", "-", "E" }, { "-", "B", "C", "-", "-" } };
		FloyedWarshallAlgorithsm<String> obj = new FloyedWarshallAlgorithsm<String>(weightsMatrix, successorsMatrix);
		obj.run();
		System.out.println();
		obj.getShortesPath('A', 'B');
	}

}
