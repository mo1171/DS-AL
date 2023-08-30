package trees;

import lombok.Data;
import lombok.NonNull;

@Data
public class AVlNode <T extends Comparable<T>> {
 
	@NonNull private T data ;
	private int heigth  =1 ; 
	private AVlNode<T> leftChild ; 
	AVlNode<T> rightChild ; 
}
