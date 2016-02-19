package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/02/16.
 */
public class Vector3d{

	public static final Vector3d ZERO = new Vector3d(0.0, 0.0, 0.0);
	public static final Vector3d E1 = new Vector3d(1.0, 0.0, 0.0);
	public static final Vector3d E2 = new Vector3d(0.0, 1.0, 0.0);
	public static final Vector3d E3 = new Vector3d(0.0, 0.0, 1.0);

	public double x, y, z;

	public Vector3d(){
	}

	public Vector3d(Vector3d v){
		set(v);
	}

	public Vector3d(double x, double y, double z){
		set(x, y, z);
	}

	public Vector3d(double[] v){
		set(v);
	}

	public void set(Vector3d v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public void set(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(double[] v){
		this.x = v[0];
		this.y = v[1];
		this.z = v[2];
	}

	/**
	 * v を加算代入
	 * @param v
	 */
	public void add(Vector3d v){
		add(this, v);
	}

	/**
	 * v1 と v2 を加算し代入
	 * @param v1
	 * @param v2
	 */
	public void add(Vector3d v1, Vector3d v2){
		x = v1.x + v2.x;
		y = v1.y + v2.y;
		z = v1.z + v2.z;
	}

	/**
	 * 積和
	 * @param v
	 * @param s
	 */
	public void madd(Vector3d v, double s){
		x += v.x * s;
		y += v.y * s;
		z += v.z * s;
	}

	/**
	 * 減算代入
	 * @param v
	 */
	public void sub(Vector3d v){
		sub(this, v);
	}

	/**
	 * v1 から v2 を減算し代入
	 * @param v1
	 * @param v2
	 */
	public void sub(Vector3d v1, Vector3d v2){
		x = v1.x - v2.x;
		y = v1.y - v2.y;
		z = v1.z - v2.z;
	}

	/**
	 * 積差
	 * @param v
	 * @param s
	 */
	public void msub(Vector3d v, double s){
		x -= v.x * s;
		y -= v.y * s;
		z -= v.z * s;
	}

	/**
	 * 乗算代入
	 * @param s
	 */
	public void mul(double s){
		mul(this, s);
	}

	/**
	 * v と s を乗算し代入
	 * @param v
	 * @param s
	 */
	public void mul(Vector3d v, double s){
		x = v.x * s;
		y = v.y * s;
		z = v.z * s;
	}

	/**
	 * 除算代入
	 * @param s
	 */
	public void div(double s){
		div(this, s);
	}

	/**
	 * v を s で除算し代入
	 * @param v
	 * @param s
	 */
	public void div(Vector3d v, double s){
		assert(Math.abs(s) > 0.0): "division by zero";
		mul(v, 1.0 / s);
	}

	/**
	 * 内積
	 * @param v
	 * @return
	 */
	public double inner(Vector3d v){
		return x * v.x + y * v.y + z * v.z;
	}

	/**
	 * 外積代入
	 * @param v
	 */
	public void cross(Vector3d v){
		final double x = this.x;
		final double y = this.y;
		final double z = this.z;
		this.x = y * v.z - z * v.y;
		this.y = z * v.x - x * v.z;
		this.z = x * v.y - y * v.x;
	}

	/**
	 * v1 と v2 の外積を代入
	 * @param v1
	 * @param v2
	 */
	public void cross(Vector3d v1, Vector3d v2){
		x = v1.y * v2.z - v1.z * v2.y;
		y = v1.z * v2.x - v1.x * v2.z;
		z = v1.x * v2.y - v1.y * v2.x;
	}

	/**
	 * 長さの二乗
	 * @return
	 */
	public double length_squared(){
		return x * x + y * y + z * z;
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
		normalize(this);
	}

	/**
	 * v を正規化し代入
	 * @param v
	 */
	public void normalize(Vector3d v){
		final double l = v.length();
		assert(l > 0.0): "division by zero";
		mul(v, 1.0 / l);
	}

	/**
	 * 方向を反転
	 */
	public void negate(){
		negate(this);
	}

	/**
	 * v の方向を反転し代入
	 * @param v
	 */
	public void negate(Vector3d v){
		x = -v.x;
		y = -v.y;
		z = -v.z;
	}

	/**
	 * 垂直か？
	 * @param v
	 * @return
	 */
	public boolean isPerpendicular(Vector3d v){
		return !(Math.abs(inner(v)) > 0.0);
	}

	/**
	 * 平行か？
	 * @param v
	 * @return
	 */
	public boolean isParallel(Vector3d v){
		return !(Math.abs(inner(v)) < 1.0);
	}

	/**
	 * ベクトル間の角度
	 * @param v
	 * @return The angle in radians
	 */
	public double angle(Vector3d v){
		return Math.acos(inner(v));
	}

	/**
	 * v1 と v2 を線形補間し代入
	 * @param v1
	 * @param v2
	 * @param t		[0,1]
	 */
	public void lerp(Vector3d v1, Vector3d v2, double t){
		x = v1.x + (v2.x - v1.x) * t;
		y = v1.y + (v2.y - v1.y) * t;
		z = v1.z + (v2.z - v1.z) * t;
	}

	/**
	 * v1 と v2 を球面線形補間し代入
	 * @param v1	‖v1‖ = 1
	 * @param v2	‖v2‖ = 1
	 * @param t		[0,1]
	 */
	public void slerp(Vector3d v1, Vector3d v2, double t){
		final double theta = v1.angle(v2);
		if(theta > 0.0){
			final double fx = Math.sin(theta * (1.0 - t));
			final double fy = Math.sin(theta * t);
			final double cosec = 1.0 / Math.sin(theta);
			x = (fx * v1.x + fy * v2.x) * cosec;
			y = (fx * v1.y + fy * v2.y) * cosec;
			z = (fx * v1.z + fy * v2.z) * cosec;
		}
		else{
			set(v1);
		}
	}

	@Override
	public String toString(){
		return "Vector3d{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
