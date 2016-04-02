package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/02/16.
 */
public class Vector3 {

	public static final Vector3 ZERO = new Vector3(0.0f, 0.0f, 0.0f);
	public static final Vector3 E1 = new Vector3(1.0f, 0.0f, 0.0f);
	public static final Vector3 E2 = new Vector3(0.0f, 1.0f, 0.0f);
	public static final Vector3 E3 = new Vector3(0.0f, 0.0f, 1.0f);

	public float x;
	public float y;
	public float z;

	public Vector3(){
	}

	public Vector3(Vector3 v){
		set(v);
	}

	public Vector3(float x, float y, float z){
		set(x, y, z);
	}

	public Vector3(float[] v){
		set(v);
	}

	public void set(Vector3 v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}

	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float[] v){
		this.x = v[0];
		this.y = v[1];
		this.z = v[2];
	}

	/**
	 * v を加算代入
	 * @param v
	 */
	public void add(Vector3 v){
		add(this, v);
	}

	/**
	 * v1 と v2 を加算し代入
	 * @param v1
	 * @param v2
	 */
	public void add(Vector3 v1, Vector3 v2){
		x = v1.x + v2.x;
		y = v1.y + v2.y;
		z = v1.z + v2.z;
	}

	/**
	 * 積和
	 * @param v
	 * @param s
	 */
	public void madd(Vector3 v, float s){
		x += v.x * s;
		y += v.y * s;
		z += v.z * s;
	}

	/**
	 * 減算代入
	 * @param v
	 */
	public void subtract(Vector3 v){
		subtract(this, v);
	}

	/**
	 * v1 から v2 を減算し代入
	 * @param v1
	 * @param v2
	 */
	public void subtract(Vector3 v1, Vector3 v2){
		x = v1.x - v2.x;
		y = v1.y - v2.y;
		z = v1.z - v2.z;
	}

	/**
	 * 積差
	 * @param v
	 * @param s
	 */
	public void msub(Vector3 v, float s){
		x -= v.x * s;
		y -= v.y * s;
		z -= v.z * s;
	}

	/**
	 * 乗算代入
	 * @param s
	 */
	public void multiply(float s){
		multiply(this, s);
	}

	/**
	 * v と s を乗算し代入
	 * @param v
	 * @param s
	 */
	public void multiply(Vector3 v, float s){
		x = v.x * s;
		y = v.y * s;
		z = v.z * s;
	}

	/**
	 * 除算代入
	 * @param s
	 */
	public void divide(float s){
		divide(this, s);
	}

	/**
	 * v を s で除算し代入
	 * @param v
	 * @param s
	 */
	public void divide(Vector3 v, float s){
		assert(Math.abs(s) > 0.0f): "division by zero";
		x = v.x / s;
		y = v.y / s;
		z = v.z / s;
	}

	/**
	 * 内積
	 * @param v
	 * @return
	 */
	public float inner(Vector3 v){
		return x * v.x + y * v.y + z * v.z;
	}

	/**
	 * 外積代入
	 * @param v
	 */
	public void outer(Vector3 v){
		final float x = this.x;
		final float y = this.y;
		final float z = this.z;
		this.x = y * v.z - z * v.y;
		this.y = z * v.x - x * v.z;
		this.z = x * v.y - y * v.x;
	}

	/**
	 * v1 と v2 の外積を代入
	 * @param v1
	 * @param v2
	 */
	public void outer(Vector3 v1, Vector3 v2){
		x = v1.y * v2.z - v1.z * v2.y;
		y = v1.z * v2.x - v1.x * v2.z;
		z = v1.x * v2.y - v1.y * v2.x;
	}

	/**
	 * 長さの二乗
	 * @return
	 */
	public float length_squared(){
		return x * x + y * y + z * z;
	}

	/**
	 * 長さ
	 * @return
	 */
	public float length(){
		return (float)Math.sqrt(length_squared());
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
	public void normalize(Vector3 v){
		final float l = v.length();
		assert(l > 0.0f): "division by zero";
		multiply(v, 1.0f / l);
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
	public void negate(Vector3 v){
		x = -v.x;
		y = -v.y;
		z = -v.z;
	}

	/**
	 * 垂直か？
	 * @param v
	 * @return
	 */
	public boolean isPerpendicular(Vector3 v){
		return !(Math.abs(inner(v)) > 0.0f);
	}

	/**
	 * 平行か？
	 * @param v
	 * @return
	 */
	public boolean isParallel(Vector3 v){
		assert(FloatComparer.almostEquals(1.0f, length(), 1)): "this is not an unit vector.";
		assert(FloatComparer.almostEquals(1.0f, v.length(), 1)): "v is not an unit vector.";
		return !(Math.abs(inner(v)) < 1.0f);
	}

	/**
	 * ベクトル間の角度
	 * @param v
	 * @return The angle in radians
	 */
	public float angle(Vector3 v){
		return (float)Math.acos(inner(v));
	}

	/**
	 * v1 と v2 を線形補間し代入
	 * @param v1
	 * @param v2
	 * @param t		[0,1]
	 */
	public void lerp(Vector3 v1, Vector3 v2, float t){
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
	public void slerp(Vector3 v1, Vector3 v2, float t){
		final double theta = v1.angle(v2);
		if(theta > 0.0f){
			final float fx = (float)Math.sin(theta * (1.0f - t));
			final float fy = (float)Math.sin(theta * t);
			final float cosec = 1.0f / (float)Math.sin(theta);
			x = (fx * v1.x + fy * v2.x) * cosec;
			y = (fx * v1.y + fy * v2.y) * cosec;
			z = (fx * v1.z + fy * v2.z) * cosec;
		}
		else{
			set(v1);
		}
	}

	/**
	 * 重心座標の点を計算.
	 * <p>\f$ (1 - f - g)\vec{V_1} + f\vec{V_2} + g\vec{V_3}\f$</p>
	 * <p>(f >= 0 && g >= 0 && 1 - f - g >= 0) ならば、点は三角形の中にある.</p>
	 * <p>(f == 0 && g >= 0 && 1 - f - g >= 0) ならば、点は線分 v1v3 上にある.</p>
	 * <p>(f >= 0 && g == 0 && 1 - f - g >= 0) ならば、点は線分 v1v2 上にある.</p>
	 * <p>(f >= 0 && g >= 0 && 1 - f - g == 0) ならば、点は線分 v2v3 上にある.</p>
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param f     v2 に対する重み係数.
	 * @param g     v3 に対する重み係数.
	 */
	public void BaryCentric(Vector3 v1, Vector3 v2, Vector3 v3, float f, float g){
		x = v1.x + f * (v2.x - v1.x) + g * (v3.x - v1.x);
		y = v1.y + f * (v2.y - v1.y) + g * (v3.y - v1.y);
		z = v1.z + f * (v2.z - v1.z) + g * (v3.z - v1.z);
	}

	/**
	 * m と v の積.
	 * @param m
	 * @param v
	 */
	public void multiply(CMatrix4 m, Vector3 v){
		x = m.m11 * v.x + m.m12 * v.y + m.m13 * v.z + m.m14;
		y = m.m21 * v.x + m.m22 * v.y + m.m23 * v.z + m.m24;
		z = m.m31 * v.x + m.m32 * v.y + m.m33 * v.z + m.m34;
	}

	/**
	 * v と m の積.
	 * @param v
	 * @param m
	 */
	public void multiply(Vector3 v, RMatrix4 m){
		x = v.x * m.m11 + v.y * m.m21 + v.z * m.m31 + m.m41;
		y = v.x * m.m12 + v.y * m.m22 + v.z * m.m32 + m.m42;
		z = v.x * m.m13 + v.y * m.m23 + v.z * m.m33 + m.m43;
	}

	/**
	 * v を q で回転.
	 * <p>\f$\vec{v'} = q\vec{v}q^{-1}\f$</p>
	 * @param v
	 * @param q
	 */
	public void rotate(Vector3 v, Quaternion q){
		final float x_sq = q.x * q.x;
		final float y_sq = q.y * q.y;
		final float z_sq = q.z * q.z;
		final float xy = q.x * q.y;
		final float yz = q.y * q.z;
		final float xz = q.x * q.z;
		final float wx = q.w * q.x;
		final float wy = q.w * q.y;
		final float wz = q.w * q.z;
		x = (1.0f - 2.0f * (y_sq + z_sq)) * v.x + (2.0f * (xy - wz)) * v.y + (2.0f * (xz + wy)) * v.z;
		y = (2.0f * (xy + wz)) * v.x + (1.0f - 2.0f * (x_sq + z_sq)) * v.y + (2.0f * (yz - wx)) * v.z;
		z = (2.0f * (xz - wy)) * v.x + (2.0f * (yz + wx)) * v.y + (1.0f - 2.0f * (x_sq + y_sq)) * v.z;
	}

	@Override
	public String toString(){
		return "Vector3{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
