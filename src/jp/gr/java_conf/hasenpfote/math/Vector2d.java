package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public final class Vector2d{

	public static final Vector2d ZERO = new Vector2d(0.0, 0.0);
	public static final Vector2d E1 = new Vector2d(1.0, 0.0);
	public static final Vector2d E2 = new Vector2d(0.0, 1.0);

	public double x, y;

	public Vector2d(){
	}

	public Vector2d(Vector2d v){
		set(v);
	}

	public Vector2d(double x, double y){
		set(x, y);
	}

	public Vector2d(double[] v){
		set(v);
	}

	public void set(Vector2d v){
		x = v.x;
		y = v.y;
	}

	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}

	public void set(double[] v){
		x = v[0];
		y = v[1];
	}

	/**
	 * v を加算代入
	 * @param v
	 */
	public void add(Vector2d v){
		x += v.x;
		y += v.y;
	}

	/**
	 * v1 と v2 を加算し代入
	 * @param v1
	 * @param v2
	 */
	public void add(Vector2d v1, Vector2d v2){
		x = v1.x + v2.x;
		y = v1.y + v2.y;
	}

	/**
	 * 積和
	 * @param v
	 * @param s
	 */
	public void madd(Vector2d v, double s){
		x += v.x * s;
		y += v.y * s;
	}

	/**
	 * 減算代入
	 * @param v
	 */
	public void sub(Vector2d v){
		x -= v.x;
		y -= v.y;
	}

	/**
	 * v1 から v2 を減算し代入
	 * @param v1
	 * @param v2
	 */
	public void sub(Vector2d v1, Vector2d v2){
		x = v1.x - v2.x;
		y = v1.y - v2.y;
	}

	/**
	 * 積差
	 * @param v
	 * @param s
	 */
	public void msub(Vector2d v, double s){
		x -= v.x * s;
		y -= v.y * s;
	}

	/**
	 * 乗算代入
	 * @param s
	 */
	public void mul(double s){
		x *= s;
		y *= s;
	}

	/**
	 * v と s を乗算し代入
	 * @param v
	 * @param s
	 */
	public void mul(Vector2d v, double s){
		x = v.x * s;
		y = v.y * s;
	}

	/**
	 * 除算代入
	 * @param s
	 */
	public void div(double s){
		mul(1.0 / s);
	}

	/**
	 * v を s で除算し代入
	 * @param v
	 * @param s
	 */
	public void div(Vector2d v, double s){
		mul(v, s);
	}

	/**
	 * 内積
	 * @param v
	 * @return
	 */
	public double inner(Vector2d v){
		return x * v.x + y * v.y;
	}

	/**
	 * 長さの二乗
	 * @return
	 */
	public double length_squared(){
		return x * x + y * y;
	}

	/**
	 * 長さ
	 * @return
	 */
	public double length(){
		return Math.sqrt(length_squared());
	}

	/**
	 * 正規化
	 */
	public void normalize(){
		double sl = length_squared();
		assert(Math.abs(sl) > 0.0): "division by zero";
		mul(1.0 / Math.sqrt(sl));
	}

	/**
	 * v を正規化し代入
	 * @param v
	 */
	public void normalize(Vector2d v){
		set(v);
		normalize();
	}

	/**
	 * 方向を反転
	 */
	public void negate(){
		x = -x;
		y = -y;
	}

	/**
	 * v の方向を反転し代入
	 * @param v
	 */
	public void negate(Vector2d v){
		x = -v.x;
		y = -v.y;
	}

	/**
	 * 垂直か？
	 * @param v
	 * @return
	 */
	public boolean isPerpendicular(Vector2d v){
		return !(Math.abs(inner(v)) > 0.0);
	}

	/**
	 * 平行か？
	 * @param v
	 * @return
	 */
	public boolean isParallel(Vector2d v){
		return !(Math.abs(inner(v)) < 1.0);
	}

	/**
	 * ベクトル間の角度
	 * @param v
	 * @return The angle in radians
	 */
	public double angle(Vector2d v){
		return Math.acos(inner(v));
	}
}