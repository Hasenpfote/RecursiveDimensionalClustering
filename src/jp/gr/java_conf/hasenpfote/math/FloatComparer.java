package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/02/19.
 */
public class FloatComparer{

	/**
	 * a と b がほぼ等しいか
	 * @param a
	 * @param b
	 * @param max_ulps unit in the last place
	 * @return a ≈ b
	 */
	public static boolean almostEquals(float a, float b, int max_ulps){
		assert(max_ulps > 0);
		// Infinity check
		// FLT_MAX 近辺での比較を行わないよう単純比較に切り替える
		if(Float.isInfinite(a) || Float.isInfinite(b))
			return a == b;
		// NaN check
		// max_ulps が NaN との比較を行わないようにする
		// NaN - FLT_MAX - 1 でも代替可
		if(Float.isNaN(a) || Float.isNaN(b))
			return false;
		// Sign check
		int ia = Float.floatToIntBits(a);
		int ib = Float.floatToIntBits(b);
		if((ia & 0x80000000) != (ib & 0x80000000))
			return a == b;
		// 辞書順に並べ替え比較
		if(ia < 0){
			ia = 0x80000000 - ia;
		}
		if(ib < 0){
			ib = 0x80000000 - ib;
		}
		return (Math.abs(ia - ib) <= max_ulps);
	}
}
