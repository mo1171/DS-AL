package heap;

import java.util.Arrays;

public abstract class Heap<T extends Comparable<T>> implements IHeap<T> {
	protected T[] heap;
	protected int position = -1;

	@SuppressWarnings("unchecked")
	public Heap() {
		heap = (T[]) new Comparable[2];
	}

	@Override
	public IHeap<T> insert(T data) {
		if (isfull())
			resize(2 * heap.length);
		heap[++position] = data;
		fixUpWard();
		return this;
	}

	protected abstract void fixUpWard();

	protected abstract void fixDownWard(int position);

	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		System.arraycopy(heap, 0, heap = (T[]) new Comparable[capacity], 0, position + 1);

	}

	private boolean isfull() {
		return position == heap.length - 1;
	}

	@Override
	public T getRoot() {
		if (isEmpty())
			return null;
		T root = heap[0];
		heap[0] = heap[position--];
		heap[position + 1] = null;
		if (position < heap.length / 3)
			resize(heap.length / 2);
		fixDownWard(position);
		return root;

	}

	private boolean isEmpty() {
		// TODO Auto-generated method stub
		return position == -1 ? true : false;
	}

	@Override
	public void sort() {
		for (int i = 0; i <= position; i++) {
			swap(0, position - i);
			fixDownWard(position - i - 1);
		}
		print();
	}
	public void print() {
		Arrays.stream(heap).forEach(Item -> {if(Item!= null) System.out.print(Item+ " ");});
		System.out.println() ;
	}

	protected void swap(int i, int j) {
		T temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;

	}

}
