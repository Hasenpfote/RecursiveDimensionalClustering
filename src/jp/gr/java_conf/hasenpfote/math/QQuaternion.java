package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/19.
 */
public final class QQuaternion{

	private static final double SQRT2 = Math.sqrt(2.0);

	/**
	 * 最大な成分の絶対値のインデクスを取得.
	 * @param q
	 * @return
	 */
	private static int getIndexByAbsMax(Quaternion q){
		int index = 0;
		double max = Math.abs(q.w);
		double abs = Math.abs(q.x);
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
	 * [62 - 63]bits: unuse<br>
	 * [60 - 61]bits: specifying the max component among X, Y, Z, W.
	 * [40 - 59]bits: component1
	 * [20 - 39]bits: component2
	 * [ 0 - 19]bits: component3
	 * </pre>
	 * @param index	specifying the max component among X, Y, Z, W.
	 * @param c1	component1.
	 * @param c2	component2.
	 * @param c3	component3.
	 * @return
	 */
	private static long quantize(int index, double c1, double c2, double c3){
		return ((long)index << 60)
				| ((long)(Util.clamp(c1 * SQRT2 * 0.5 + 0.5, 0.0, 1.0) * 0xfffff) << 40)
				| ((long)(Util.clamp(c2 * SQRT2 * 0.5 + 0.5, 0.0, 1.0) * 0xfffff) << 20)
				| ((long)(Util.clamp(c3 * SQRT2 * 0.5 + 0.5, 0.0, 1.0) * 0xfffff));
	}

	/**
	 * 圧縮.
	 * @param q		an unit quaternion.
	 * @return
	 */
	public static long compress(Quaternion q){
		assert(DoubleComparer.almostEquals(1.0, q.norm(), 1)): "q is not an unit quaternion.";
		long result = 0L;
		int index = getIndexByAbsMax(q);
		switch(index){
			case 0:
				result = (q.w < 0)? quantize(index, -q.x, -q.y, -q.z) : quantize(index, q.x, q.y, q.z);
				break;
			case 1:
				result = (q.x < 0)? quantize(index, -q.w, -q.y, -q.z) : quantize(index, q.w, q.y, q.z);
				break;
			case 2:
				result = (q.y < 0)? quantize(index, -q.w, -q.x, -q.z) : quantize(index, q.w, q.x, q.z);
				break;
			case 3:
				result = (q.z < 0)? quantize(index, -q.w, -q.x, -q.y) : quantize(index, q.w, q.x, q.y);
				break;
			default:
				break;
		}
		return result;
	}

	private static int getIndex(long qq){
		return (int)((qq & 0x3000000000000000L) >> 60);
	}

	private static double getComponent1(long qq){
		return ((double)((qq & 0x0FFFFF0000000000L) >> 40) / 0xfffff - 0.5) * 2.0 / SQRT2;
	}

	private static double getComponent2(long qq){
		return ((double)((qq & 0x000000FFFFF00000L) >> 20) / 0xfffff - 0.5) * 2.0 / SQRT2;
	}

	private static double getComponent3(long qq){
		return ((double)(qq & 0x00000000000FFFFFL) / 0xfffff - 0.5) * 2.0 / SQRT2;
	}

	/**
	 * 解凍.
	 * @param q
	 * @param qq
	 */
	public static void decompress(Quaternion q, long qq){
		int index  = getIndex(qq);
		switch(index){
			case 0:
				q.x = getComponent1(qq);
				q.y = getComponent2(qq);
				q.z = getComponent3(qq);
				q.w = Math.sqrt(1.0 - q.x * q.x - q.y * q.y - q.z * q.z);
				break;
			case 1:
				q.w = getComponent1(qq);
				q.y = getComponent2(qq);
				q.z = getComponent3(qq);
				q.x = Math.sqrt(1.0 - q.w * q.w - q.y * q.y - q.z * q.z);
				break;
			case 2:
				q.w = getComponent1(qq);
				q.x = getComponent2(qq);
				q.z = getComponent3(qq);
				q.y = Math.sqrt(1.0 - q.w * q.w - q.x * q.x - q.z * q.z);
				break;
			case 3:
				q.w = getComponent1(qq);
				q.x = getComponent2(qq);
				q.y = getComponent3(qq);
				q.z = Math.sqrt(1.0 - q.w * q.w - q.x * q.x - q.y * q.y);
				break;
			default:
				break;
		}
	}
}
