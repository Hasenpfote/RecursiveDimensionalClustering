package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/19.
 */
public final class QQuaternion{

	private static final float SQRT2 = (float)Math.sqrt(2.0);

	/**
	 * 最大な成分の絶対値のインデクスを取得.
	 * @param q
	 * @return
	 */
	private static int getIndexByAbsMax(Quaternion q){
		int index = 0;
		float max = Math.abs(q.w);
		float abs = Math.abs(q.x);
		if(abs > max){
			index = 1;
			max = abs;
		}
		abs = Math.abs(q.y);
		if(abs > max){
			index = 2;
			max = abs;
		}
		abs = Math.abs(q.z);
		if(abs > max){
			index = 3;
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
	 * @param c1	component1.
	 * @param c2	component2.
	 * @param c3	component3.
	 * @return
	 */
	private static int quantize(int index, float c1, float c2, float c3){
		return (index << 30)
				| ((int)(Util.clamp(c1 * SQRT2 * 0.5f + 0.5f, 0.0f, 1.0f) * 0x3FF) << 20)
				| ((int)(Util.clamp(c2 * SQRT2 * 0.5f + 0.5f, 0.0f, 1.0f) * 0x3FF) << 10)
				| ((int)(Util.clamp(c3 * SQRT2 * 0.5f + 0.5f, 0.0f, 1.0f) * 0x3FF));
	}

	/**
	 * 圧縮.
	 * @param q		an unit quaternion.
	 * @return
	 */
	public static int compress(Quaternion q){
		assert(FloatComparer.almostEquals(1.0f, q.norm(), 1)): "q is not an unit quaternion.";
		int result = 0;
		int index = getIndexByAbsMax(q);
		switch(index){
			case 0:
				result = (q.w < 0.0f)? quantize(index, -q.x, -q.y, -q.z) : quantize(index, q.x, q.y, q.z);
				break;
			case 1:
				result = (q.x < 0.0f)? quantize(index, -q.w, -q.y, -q.z) : quantize(index, q.w, q.y, q.z);
				break;
			case 2:
				result = (q.y < 0.0f)? quantize(index, -q.w, -q.x, -q.z) : quantize(index, q.w, q.x, q.z);
				break;
			case 3:
				result = (q.z < 0.0f)? quantize(index, -q.w, -q.x, -q.y) : quantize(index, q.w, q.x, q.y);
				break;
			default:
				break;
		}
		return result;
	}

	private static int getIndex(int qq){
		return ((qq & 0xC0000000) >>> 30);
	}

	private static float getComponent1(int qq){
		return ((float)((qq & 0x3FF00000) >> 20) / 0x3FF - 0.5f) * 2.0f / SQRT2;
	}

	private static float getComponent2(int qq){
		return ((float)((qq & 0x000FFC00) >> 10) / 0x3FF - 0.5f) * 2.0f / SQRT2;
	}

	private static float getComponent3(int qq){
		return ((float)(qq & 0x000003FF) / 0x3FF - 0.5f) * 2.0f / SQRT2;
	}

	/**
	 * 解凍.
	 * @param q
	 * @param qq
	 */
	public static void decompress(Quaternion q, int qq){
		int index  = getIndex(qq);
		switch(index){
			case 0:
				q.x = getComponent1(qq);
				q.y = getComponent2(qq);
				q.z = getComponent3(qq);
				q.w = (float)Math.sqrt(1.0f - q.x * q.x - q.y * q.y - q.z * q.z);
				break;
			case 1:
				q.w = getComponent1(qq);
				q.y = getComponent2(qq);
				q.z = getComponent3(qq);
				q.x = (float)Math.sqrt(1.0f - q.w * q.w - q.y * q.y - q.z * q.z);
				break;
			case 2:
				q.w = getComponent1(qq);
				q.x = getComponent2(qq);
				q.z = getComponent3(qq);
				q.y = (float)Math.sqrt(1.0f - q.w * q.w - q.x * q.x - q.z * q.z);
				break;
			case 3:
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
