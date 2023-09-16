package algorithims;

public class AlgorithmsSortingAndSearch {

	// --------------------- Start of sort algorithms -----------------------------

	// ---- Heap sort --------
	public static void heapsort(int[] arr) {
		int n = arr.length;
		buildheap(arr, n);
		for (int i = n - 1; i >= 0; i--) {
			swap(arr, i, 0);
			heapify(arr, i, 0);
		}
	}

	private static void heapify(int[] arr, int n, int i) {

		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int max = i;
		if (l < n && arr[l] > arr[max]) {
			max = l;
		}
		if (r < n && arr[r] > arr[max]) {
			max = r;
		}
		if (max != i) {
			swap(arr, i, max);
			heapify(arr, n, max);
		}

	}

	private static void buildheap(int[] arr, int n) {
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(arr, n, i);
		}

	}

	// -------- Quick Sort -------------

	public static void quicksort(int[] arr) {
		quicksort(arr, 0, arr.length);
	}

	private static void quicksort(int[] arr, int start, int end) {
		if (start >= end) {
			return;
		}
		int pivoit = partiotion(arr, start, end);
		quicksort(arr, start, pivoit - 1);
		quicksort(arr, pivoit + 1, end);
	}

	private static int partiotion(int[] arr, int start, int end) {
		int i = start - 1;
		int pivoit = arr[end - 1];
		for (int j = start; j < end - 1; j++) {
			if (arr[j] < pivoit) {
				i++;
				swap(arr, i, j);
			}
		}
		i++;
		swap(arr, i, end - 1);
		return i;
	}

	// ----------- Merge sort ----------------

	public static void mergesort(int[] arr) {

		int size = arr.length;
		int mid = size / 2;
		if (size <= 1) {
			return;
		}
		int[] leftArr = new int[mid];
		int[] rightArr = new int[size - mid];
		int i = 0, r = 0;
		while (i < size) {
			if (i < mid)
				leftArr[i] = arr[i];

			else
				rightArr[r++] = arr[i];
			i++;
		}
		mergesort(leftArr);
		mergesort(rightArr);
		merge(leftArr, rightArr, arr);

	}

	private static void merge(int[] leftArr, int[] rightArr, int[] arr) {
		int i = 0, l = 0, r = 0;
		int leftSize = leftArr.length, rightSize = rightArr.length;
		while (l < leftSize && r < rightSize) {
			if (leftArr[l] < rightArr[r])
				arr[i++] = leftArr[l++];
			else
				arr[i++] = rightArr[r++];
		}

		while (l < leftSize) {
			arr[i++] = leftArr[l++];
		}
		while (r < rightSize) {
			arr[i++] = rightArr[r++];
		}
	}

	// --------- basic sorts ------------ { insertion , selection , bubble }
	public static void insertionsort(int[] arr) {
		for (int i = 1; i < arr.length; i++) {
			int temp = arr[i];
			int j = i - 1;
			while (j >= 0 && arr[j] > temp) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = temp;
		}

	}

	public static void selectionsort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int min = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[min] > arr[j]) {
					min = j;
				}
			}
			swap(arr, i, min);
		}

	}

	public static void bubblesort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if (arr[j] > arr[j + 1])
					swap(arr, j, j + 1);
			}
		}

	}

	private static void swap(int arr[], int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;

	}

// ------------------------------- End of sort Algorithms ----------------------------------------

// ------------------------------- Start of search Algorithms ------------------------------------
	public static int enterpolationsearch(int[] arr, int val) {

		return enterpolationsearch(arr, val, 0, arr.length - 1);
	}

	private static int enterpolationsearch(int[] arr, int val, int low, int high) {
		if (val > arr[high] || val < arr[low] || low > high) {
			return -1;
		}
		int proba = low + (high - low) * (val - arr[low]) / (arr[high] - arr[low]);
		if (val == arr[proba]) {
			return proba;
		} else {
			if (val > arr[proba]) {
				return enterpolationsearch(arr, val, proba + 1, high);
			} else {
				return enterpolationsearch(arr, val, low, proba - 1);
			}
		}
	}

	public static int linearsearch(int[] arr, int val) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == val) {
				return i;
			}
		}
		return -1;
	}

	public static int binarysearch(int[] arr, int val) {
		return binarysearch(arr, val, 0, arr.length - 1);
	}

	private static int binarysearch(int[] arr, int val, int low, int high) {
		if (low > high) {
			return -1;
		}
		int mid = (int) (low + high) / 2;

		if (val == arr[mid])
			return mid;
		else {
			if (val > arr[mid]) {
				return binarysearch(arr, val, mid + 1, high);
			} else {
				return binarysearch(arr, val, low, mid - 1);
			}
		}

	}

// ----------------------------------- End of search alogrithms ---------------------------------------------

}
