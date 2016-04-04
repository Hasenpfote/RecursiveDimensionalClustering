package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/19.
 */
public final class QQuaternion{

	private static final float SQRT2 = (float)Math.sqrt(2.0);
	private static final int INDEX_BIT_MASK = 0xC0000000;
	private static final int COMPONENT1_BIT_MASK = 0x3FF00000;
	private static final int COMPONENT2_BIT_MASK = 0x000FFC00;
	private static final int COMPONENT3_BIT_MASK = 0x000003FF;
	private static final int SCALE_FACTOR = 0x3FF;

	private enum Index{
		W, X, Y, Z;
	}

	private QQuaternion(){
	}

	/**
	 * 最大な成分の絶対値のインデクスを取得.
	 * @param q
	 * @return
	 */
	private static Index getIndexByAbsMax(Quaternion q){
		Index index = Index.W;
		float max = Math.abs(q.w);
		float abs = Math.abs(q.x);
		if(abs > max){
			index = Index.X;
			max = abs;
		}
		abs = Math.abs(q.y);
		if(abs > max){
			index = Index.Y;
			max = abs;
		}
		abs = Math.abs(q.z);
		if(abs > max){
			index = Index.Z;
		}
		return index;
	}

	/**
	 * 量子化.
	 * <pre>
	 * [30 - 31]bits: specifying the max component among X, Y, Z, W.<br>
	 * [20 - 29]bits: component1<br>
	 * [10 - 19]bits: component2<br>
	 * [ 0 -  9]bits: component3
	 * </pre>
	 * @param index	specifying the max component among X, Y, Z, W.
	 * @param c1	component1.	\f$[-\frac{1}{\sqrt{2}}, +\frac{1}{\sqrt{2}}]\f$
	 * @param c2	component2. \f$[-\frac{1}{\sqrt{2}}, +\frac{1}{\sqrt{2}}]\f$
	 * @param c3	component3. \f$[-\frac{1}{\sqrt{2}}, +\frac{1}{\sqrt{2}}]\f$
	 * @return
	 */
	private static int quantize(int index, float c1, float c2, float c3){
		return (index << 30)
				| ((int)(MathUtil.clamp(c1 * SQRT2 * 0.5f + 0.5f, 0.0f, 1.0f) * SCALE_FACTOR) << 20)
				| ((int)(MathUtil.clamp(c2 * SQRT2 * 0.5f + 0.5f, 0.0f, 1.0f) * SCALE_FACTOR) << 10)
				| ((int)(MathUtil.clamp(c3 * SQRT2 * 0.5f + 0.5f, 0.0f, 1.0f) * SCALE_FACTOR));
	}

	/**
	 * 圧縮.
	 * @param q		an unit quaternion.
	 * @return
	 */
	public static int compress(Quaternion q){
		assert(FloatComparer.almostEquals(1.0f, q.norm(), 1)): "q is not an unit quaternion.";
		int result = 0;
		Index index = getIndexByAbsMax(q);
		switch(index){
			case W:
				result = (q.w < 0.0f)? quantize(index.ordinal(), -q.x, -q.y, -q.z) : quantize(index.ordinal(), q.x, q.y, q.z);
				break;
			case X:
				result = (q.x < 0.0f)? quantize(index.ordinal(), -q.w, -q.y, -q.z) : quantize(index.ordinal(), q.w, q.y, q.z);
				break;
			case Y:
				result = (q.y < 0.0f)? quantize(index.ordinal(), -q.w, -q.x, -q.z) : quantize(index.ordinal(), q.w, q.x, q.z);
				break;
			case Z:
				result = (q.z < 0.0f)? quantize(index.ordinal(), -q.w, -q.x, -q.y) : quantize(index.ordinal(), q.w, q.x, q.y);
				break;
			default:
				break;
		}
		return result;
	}

	private static int getIndex(int qq){
		return ((qq & INDEX_BIT_MASK) >>> 30);
	}

	private static float getComponent1(int qq){
		return ((float)((qq & COMPONENT1_BIT_MASK) >> 20) / SCALE_FACTOR - 0.5f) * 2.0f / SQRT2;
	}

	private static float getComponent2(int qq){
		return ((float)((qq & COMPONENT2_BIT_MASK) >> 10) / SCALE_FACTOR - 0.5f) * 2.0f / SQRT2;
	}

	private static float getComponent3(int qq){
		return ((float)(qq & COMPONENT3_BIT_MASK) / SCALE_FACTOR - 0.5f) * 2.0f / SQRT2;
	}

	/**
	 * 解凍.
	 * @param q
	 * @param qq
	 */
	public static void decompress(Quaternion q, int qq){
		switch(Index.values()[getIndex(qq)]){
			case W:
				q.x = getComponent1(qq);
				q.y = getComponent2(qq);
				q.z = getComponent3(qq);
				q.w = (float)Math.sqrt(1.0f - q.x * q.x - q.y * q.y - q.z * q.z);
				break;
			case X:
				q.w = getComponent1(qq);
				q.y = getComponent2(qq);
				q.z = getComponent3(qq);
				q.x = (float)Math.sqrt(1.0f - q.w * q.w - q.y * q.y - q.z * q.z);
				break;
			case Y:
				q.w = getComponent1(qq);
				q.x = getComponent2(qq);
				q.z = getComponent3(qq);
				q.y = (float)Math.sqrt(1.0f - q.w * q.w - q.x * q.x - q.z * q.z);
				break;
			case Z:
				q.w = getComponent1(qq);
				q.x = getComponent2(qq);
				q.y = getComponent3(qq);
				q.z = (float)Math.sqrt(1.0f - q.w * q.w - q.x * q.x - q.y * q.y);
				break;
			default:
				break;
		}
	}
}
