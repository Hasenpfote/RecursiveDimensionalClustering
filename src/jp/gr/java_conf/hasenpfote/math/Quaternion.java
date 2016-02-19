package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/02/16.
 */
public class Quaternion{

	public double w, x, y, z;

	public static final Quaternion ZERO = new Quaternion(0.0, 0.0, 0.0, 0.0);
	public static final Quaternion IDENTITY  = new Quaternion(1.0, 0.0, 0.0, 0.0);

	public Quaternion(){
	}

	public Quaternion(Quaternion q){
		set(q);
	}

	public Quaternion(double w, double x, double y, double z){
		set(w, x, y, z);
	}

	public Quaternion(double w, Vector3d v){
		set(w, v);
	}

	public void set(Quaternion q){
		w = q.w;
		x = q.x;
		y = q.y;
		z = q.z;
	}

	public void set(double w, double x, double y, double z){
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(double w, Vector3d v){
		this.w = w;
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	/**
	 * 零
	 */
	public void zero(){
		w = x = y = z = 0.0;
	}

	/**
	 * 単位
	 */
	public void identity(){
		w = 1.0;
		x = y = z = 0.0;
	}

	/**
	 * q を加算代入
	 * @param q
	 */
	public void add(Quaternion q){
		add(this, q);
	}

	/**
	 * q1 と q2 を加算し代入
	 * @param q1
	 * @param q2
	 */
	public void add(Quaternion q1, Quaternion q2){
		this.w = q1.w + q2.w;
		this.x = q1.x + q2.x;
		this.y = q1.y + q2.y;
		this.z = q1.z + q2.z;
	}

	/**
	 * q を減算代入
	 * @param q
	 */
	public void sub(Quaternion q){
		sub(this, q);
	}

	/**
	 * q1 から q2 を減算し代入
	 * @param q1
	 * @param q2
	 */
	public void sub(Quaternion q1, Quaternion q2){
		this.w = q1.w - q2.w;
		this.x = q1.x - q2.x;
		this.y = q1.y - q2.y;
		this.z = q1.z - q2.z;
	}

	/**
	 * 積
	 * @param q
	 */
	public void mul(Quaternion q){
		double _w = w * q.w - (x * q.x + y * q.y + z * q.z);
		double _x = w * q.x + q.w * x + (y * q.z - z * q.y);
		double _y = w * q.y + q.w * y + (z * q.x - x * q.z);
		double _z = w * q.z + q.w * z + (x * q.y - y * q.x);
		set(_w, _x, _y, _z);
	}

	/**
	 * 乗算代入
	 * @param s
	 */
	public void mul(double s){
		mul(this, s);
	}

	/**
	 * q と s を乗算し代入
	 * @param q
	 * @param s
	 */
	public void mul(Quaternion q, double s){
		w = q.w * s;
		x = q.x * s;
		y = q.y * s;
		z = q.z * s;
	}

	/**
	 * 除算代入
	 * @param s
	 */
	public void div(double s){
		div(this, s);
	}

	/**
	 * q を s で除算し代入
	 * @param q
	 * @param s
	 */
	public void div(Quaternion q, double s){
		assert(Math.abs(s) > 0.0): "division by zero";
		mul(q, 1.0 / s);
	}

	/**
	 * ノルムの二乗
	 * @return ‖q‖^2 = q*q
	 */
	public double norm_squared(){
		return w * w + x * x + y * y + z * z;
	}

	/**
	 * ノルム
	 * @return ‖q‖
	 */
	public double norm(){
		return Math.sqrt(norm_squared());
	}

	/**
	 * 共役(q*)
	 */
	public void conjugate(){
		conjugate(this);
	}

	/**
	 * q の共役を代入
	 * @param q
	 */
	public void conjugate(Quaternion q){
		w =  q.w;
		x = -q.x;
		y = -q.y;
		z = -q.z;
	}

	/**
	 * 積の逆元(q* / ‖q‖^2)
	 *
	 */
	public void inverse(){
		inverse(this);
	}

	/**
	 * q の積の逆元を代入
	 * @param q
	 */
	public void inverse(Quaternion q){
		double nsq = norm_squared();
		assert(nsq > 0.0): "division by zero";
		nsq = 1.0 / nsq;
		w =  q.w * nsq;
		x = -q.x * nsq;
		y = -q.y * nsq;
		z = -q.z * nsq;
	}

	/**
	 * 正規化
	 */
	public void normalize(){
		normalize(this);
	}

	/**
	 * q を正規化し代入
	 * @param q
	 */
	public void normalize(Quaternion q){
		final double n = q.norm();
		assert(n > 0.0): "division by zero";
		mul(q, 1.0 / n);
	}


	/**
	 * 和の逆元
	 */
	public void negate(){
		negate(this);
	}

	/**
	 * q の和の逆元を代入
	 * @param q
	 */
	public void negate(Quaternion q){
		w = -q.w;
		x = -q.x;
		y = -q.y;
		z = -q.z;
	}

	/**
	 * 内積
	 * @param q
	 * @return
	 */
	public double inner(Quaternion q){
		return w * q.w + x * q.x + y * q.y + z * q.z;
	}

	/**
	 * q1 と q2 を線形補間し代入
	 * @param q1
	 * @param q2
	 * @param t		[0,1]
	 */
	public void lerp(Quaternion q1, Quaternion q2, double t){
		w = q1.w + t * (q2.w - q1.w);
		x = q1.x + t * (q2.x - q1.x);
		y = q1.y + t * (q2.y - q1.y);
		z = q1.z + t * (q2.z - q1.z);
	}

	/**
	 * q1 と q2 を球面線形補間し代入
	 * @param q1	‖q1‖=1
	 * @param q2	‖q2‖=1
	 * @param t		[0,1]
	 */
	public void slerp(Quaternion q1, Quaternion q2, double t){
		final double c = q1.inner(q2);
		if(c < 1.0){
			final double theta = Math.acos(c);
			final double fx = Math.sin(theta * (1.0 - t));
			final double fy = Math.sin(theta * t);
			final double cosec = 1.0 / Math.sin(theta);
			w = (fx * q1.w + fy * q2.w) * cosec;
			x = (fx * q1.x + fy * q2.x) * cosec;
			y = (fx * q1.y + fy * q2.y) * cosec;
			z = (fx * q1.z + fy * q2.z) * cosec;
		}
		else{
			set(q1);
		}
	}

	/**
	 * 回転
	 * @param axis	回転軸
	 * @param theta	回転角
	 */
	public void rotation(Vector3d axis, double theta){
		final double t = theta * 0.5;
		final double s = Math.sin(t);
		w = Math.cos(t);
		x = axis.x * s;
		y = axis.y * s;
		z = axis.z * s;
	}

	/**
	 * 最小弧回転
	 * @param v1 ‖v1‖=1
	 * @param v2 ‖v2‖=1
	 */
	public void rotationArc(Vector3d v1, Vector3d v2){
		final double d = v1.inner(v2);
		final double s = Math.sqrt((1.0 + d) * 2.0);
		w = s * 0.5;
		x = (v1.y * v2.z - v1.z * v2.y) / s;
		y = (v1.z * v2.x - v1.x * v2.z) / s;
		z = (v1.x * v2.y - v1.y * v2.x) / s;
	}

	@Override
	public String toString() {
		return "Quaternion{" + "w=" + w + ", x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
