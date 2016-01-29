package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public final class Util{

	/**
	 * 順列
	 * @param n
	 * @param r
	 * @return nPr = n! / (n-r)!
	 */
	public static long permutation(int n, int r){
		assert((n >= 0) && (r >= 0) && (n >= r));
		long npr = 1;
		for(int i = 0; i < r; i++){
			npr = npr * (n - i);
		}
		return npr;
	}

	/**
	 * 組合せ
	 * @param	n
	 * @param	r
	 * @return	nCr = nPr / r!
	 */
	public static long combination(int n, int r){
		assert((n >= 0) && (r >= 0) && (n >= r));
		long ncr = 1;
		for(int i = 1; i <= r; i++){
			ncr = ncr * (n - i + 1) / i;
		}
		return ncr;
	}
}
