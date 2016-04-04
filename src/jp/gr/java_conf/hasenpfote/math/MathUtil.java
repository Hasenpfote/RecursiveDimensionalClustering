package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public final class MathUtil{

	private MathUtil(){}

	/**
	 * clamp
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float clamp(float value, float min, float max){
		if(value < min)
			return min;
		if(value > max)
			return max;
		return value;
	}

	/**
	 * clamp
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clamp(double value, double min, double max){
		if(value < min)
			return min;
		if(value > max)
			return max;
		return value;
	}

	/**
	 * 閉区間に含まれるか.
	 * @param value
	 * @param lower	下限
	 * @param upper	上限
	 * @return
	 */
	public static boolean containsClosed(float value, float lower, float upper){
		if(value < lower || value > upper)
			return false;
		return true;
	}

	/**
	 * 開区間に含まれるか.
	 * @param value
	 * @param lower	下限
	 * @param upper	上限
	 * @return
	 */
	public static boolean containsOpen(float value, float lower, float upper){
		if(value <= lower || value >= upper)
			return false;
		return true;
	}

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

	public enum QuadraticDiscriminant{
		ComplexRoots,	// two complex roots.
		EqualRoots,		// two equal real roots.
		RealRoots;		// two different real roots.
	}

	/**
	 * 二次方程式の求根.
	 * @param roots	根.(サイズ 2 を必要とする)
	 * @param a
	 * @param b
	 * @param c
	 * @return -1 なら虚根、0 なら重根、1 なら実数根.
	 */
	public static QuadraticDiscriminant solveQuadratic(float[] roots, float a, float b, float c){
		final float discriminant = b * b - 4.0f * a * c;
		if(discriminant < 0.0f)	// equation has imaginary roots.
			return QuadraticDiscriminant.ComplexRoots;

		final float x = (Math.abs(b) + (float)Math.sqrt(discriminant)) / (2.0f * a);
		if(discriminant > 0.0f){// equation has 2 roots.
			if(b < 0.0f){	// negative sign
				// x1 = (-b - √D) / 2a = (|b| - √D) / 2a ... When b is larger, There is a possibility to underflow.
				// x2 = (-b + √D) / 2a = (|b| + √D) / 2a = x
				roots[0] = c / (a * x);	// rationalize the numerator.(Muller's method)
				roots[1] = x;
			}
			else{			// positive sign
				// x1 = (- b - √D) / 2a = (-|b| - √D) / 2a = -x
				// x2 = (- b + √D) / 2a = (-|b| + √D) / 2a ... When b is larger, There is a possibility to underflow.
				roots[0] = -x;
				roots[1] = -c / (a * x);// rationalize the numerator.(Muller's method)
			}
			return QuadraticDiscriminant.RealRoots;
		}
		// equation has 1 root
		roots[0] = (b < 0.0f)? x : -x;
		return QuadraticDiscriminant.EqualRoots;
	}
}
