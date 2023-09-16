package problemSolving;

public class Solution {
	static int max = 55;
	static int[] vis = new int[max];

	public static int reverseBits(int n) {

		int temp = n , result =0  , bit ; 
		for (int i = 0; i < 32; i++) {
			bit = (temp >> i) & 1 ; 
		result = result | (bit<<(31-i)) ; 
 		}
		return result ; 
	}

	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		System.out.println(( reverseBits(0b00000010100101000001111010011100)));
		System.out.println(System.currentTimeMillis() - start + "ms");
	}

	public static int fab(int n) throws Exception {
		if (n > max)
			throw new Exception("you exceeded the max of visit array");
		if (n <= 1)
			return 1;
		if (vis[n] != -1)
			return vis[n];
		else

			return vis[n] = fab(n - 2) + fab(n - 1);
	}

	public static int fab2(int n) throws Exception {
		if (n > max)
			throw new Exception("you exceeded the max of visit array");
		if (n <= 1)
			return 1;

		else

			return fab2(n - 2) + fab2(n - 1);
	}

	public static int fabDp(int n) {
		int fab[] = new int[max];
		fab[0] = fab[1] = 1;
		for (int i = 2; i < fab.length; i++) {

			fab[i] = fab[i - 1] + fab[i - 2];
		}
		return fab[n];
	}

}
