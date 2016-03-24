package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/02/16.
 */
public class Vector3f {

	public static final Vector3f ZERO = new Vector3f(0.0f, 0.0f, 0.0f);
	public static final Vector3f E1 = new Vector3f(1.0f, 0.0f, 0.0f);
	public static final Vector3f E2 = new Vector3f(0.0f, 1.0f, 0.0f);
	public static final Vector3f E3 = new Vector3f(0.0f, 0.0f, 1.0f);

	public float x;
	public float y;
	public float z;

	public Vector3f(){
	}

	public Vector3f(Vector3f v){
		set(v);
	}

	public Vector3f(float x, float y, float z){
		set(x, y, z);
	}

	public Vector3f(float[] v){
		set(v);
	}

	public void set(Vector3f v){
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
	public void add(Vector3f v){
		add(this, v);
	}

	/**
	 * v1 と v2 を加算し代入
	 * @param v1
	 * @param v2
	 */
	public void add(Vector3f v1, Vector3f v2){
		x = v1.x + v2.x;
		y = v1.y + v2.y;
		z = v1.z + v2.z;
	}

	/**
	 * 積和
	 * @param v
	 * @param s
	 */
	public void madd(Vector3f v, float s){
		x += v.x * s;
		y += v.y * s;
		z += v.z * s;
	}

	/**
	 * 減算代入
	 * @param v
	 */
	public void subtract(Vector3f v){
		subtract(this, v);
	}

	/**
	 * v1 から v2 を減算し代入
	 * @param v1
	 * @param v2
	 */
	public void subtract(Vector3f v1, Vector3f v2){
		x = v1.x - v2.x;
		y = v1.y - v2.y;
		z = v1.z - v2.z;
	}

	/**
	 * 積差
	 * @param v
	 * @param s
	 */
	public void msub(Vector3f v, float s){
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
	public void multiply(Vector3f v, float s){
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
	public void divide(Vector3f v, float s){
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
	public float inner(Vector3f v){
		return x * v.x + y * v.y + z * v.z;
	}

	/**
	 * 外積代入
	 * @param v
	 */
	public void cross(Vector3f v){
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
	public void cross(Vector3f v1, Vector3f v2){
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
	public void normalize(Vector3f v){
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
	public void negate(Vector3f v){
		x = -v.x;
		y = -v.y;
		z = -v.z;
	}

	/**
	 * 垂直か？
	 * @param v
	 * @return
	 */
	public boolean isPerpendicular(Vector3f v){
		return !(Math.abs(inner(v)) > 0.0f);
	}

	/**
	 * 平行か？
	 * @param v
	 * @return
	 */
	public boolean isParallel(Vector3f v){
		return !(Math.abs(inner(v)) < 1.0f);
	}

	/**
	 * ベクトル間の角度
	 * @param v
	 * @return The angle in radians
	 */
	public float angle(Vector3f v){
		return (float)Math.acos(inner(v));
	}

	/**
	 * v1 と v2 を線形補間し代入
	 * @param v1
	 * @param v2
	 * @param t		[0,1]
	 */
	public void lerp(Vector3f v1, Vector3f v2, float t){
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
	public void slerp(Vector3f v1, Vector3f v2, float t){
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

	@Override
	public String toString(){
		return "Vector3f{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
