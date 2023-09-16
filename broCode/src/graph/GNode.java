package graph;

class GNode<E> {
	E data ; 
	int idx ; 
	GNode(E data, int idx) {
		this.data = data;
		this.idx = idx;
	}
	public GNode(E data ){
		this.data = data  ; 
	}

}
