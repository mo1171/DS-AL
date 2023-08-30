package heap;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

	@Override
	protected void fixUpWard() {
		int childIdx = position;
		int parentIdx = (childIdx - 1 )/ 2;
		while (parentIdx >= 0) {
			if (!(heap[parentIdx].compareTo(heap[childIdx]) < 0))
				return  ; 
			swap(childIdx, parentIdx);
			childIdx = parentIdx;
			parentIdx  = (childIdx -1) /2 ; 
		}

	}

	@Override
	protected void fixDownWard(int endIdx) {
		int parenIdx = 0;
		if (endIdx == -1)
			return;
		while (parenIdx <= endIdx) {
			int leftChildIdx = parenIdx * 2 + 1;
			int rightChildIdx = parenIdx * 2 + 2;
			if (leftChildIdx > endIdx)
				return;
			int choosedChild = rightChildIdx > endIdx ? leftChildIdx
					: heap[leftChildIdx].compareTo(heap[rightChildIdx]) > 0 ? leftChildIdx : rightChildIdx;
			if (heap[parenIdx].compareTo(heap[choosedChild]) > 0)
				return;
			swap(parenIdx, choosedChild); 
			parenIdx = choosedChild ; 
		}

	}

}
