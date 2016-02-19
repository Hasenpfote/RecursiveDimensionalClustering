package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/02/19.
 */
public class DoubleComparer{

	/**
	 * a と b がほぼ等しいか
	 * @param a
	 * @param b
	 * @param max_ulps unit in the last place
	 * @return a ≈ b
	 */
	public static boolean almostEquals(double a, double b, int max_ulps){
		assert(max_ulps > 0);
		// Infinity check
		// DBL_MAX 近辺での比較を行わないよう単純比較に切り替える
		if(Double.isInfinite(a) || Double.isInfinite(b))
			return a == b;
		// NaN check
		// max_ulps が NaN との比較を行わないようにする
		// NaN - DBL_MAX - 1 でも代替可
		if(Double.isNaN(a) || Double.isNaN(b))
			return false;
		// Sign check
		long la = Double.doubleToLongBits(a);
		long lb = Double.doubleToLongBits(b);
		if((la & 0x8000000000000000L) != (lb & 0x8000000000000000L))
			return a == b;
		// 辞書順に並べ替え比較
		if(la < 0){
			la = 0x8000000000000000L - la;
		}
		if(lb < 0){
			lb = 0x8000000000000000L - lb;
		}
		return (Math.abs(la - lb) <= max_ulps);
	}
}
