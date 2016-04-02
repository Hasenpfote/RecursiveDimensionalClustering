package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public final class Vector2 {

	public static final Vector2 ZERO = new Vector2(0.0f, 0.0f);
	public static final Vector2 E1 = new Vector2(1.0f, 0.0f);
	public static final Vector2 E2 = new Vector2(0.0f, 1.0f);

	public float x;
	public float y;

	public Vector2(){
	}

	public Vector2(Vector2 v){
		set(v);
	}

	public Vector2(float x, float y){
		set(x, y);
	}

	public Vector2(float[] v){
		set(v);
	}

	public void set(Vector2 v){
		x = v.x;
		y = v.y;
	}

	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}

	public void set(float[] v){
		x = v[0];
		y = v[1];
	}

	/**
	 * v を加算代入
	 * @param v
	 */
	public void add(Vector2 v){
		add(this, v);
	}

	/**
	 * v1 と v2 を加算し代入
	 * @param v1
	 * @param v2
	 */
	public void add(Vector2 v1, Vector2 v2){
		x = v1.x + v2.x;
		y = v1.y + v2.y;
	}

	/**
	 * 積和
	 * @param v
	 * @param s
	 */
	public void madd(Vector2 v, float s){
		x += v.x * s;
		y += v.y * s;
	}

	/**
	 * 減算代入
	 * @param v
	 */
	public void subtract(Vector2 v){
		subtract(this, v);
	}

	/**
	 * v1 から v2 を減算し代入
	 * @param v1
	 * @param v2
	 */
	public void subtract(Vector2 v1, Vector2 v2){
		x = v1.x - v2.x;
		y = v1.y - v2.y;
	}

	/**
	 * 積差
	 * @param v
	 * @param s
	 */
	public void msub(Vector2 v, float s){
		x -= v.x * s;
		y -= v.y * s;
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
	public void multiply(Vector2 v, float s){
		x = v.x * s;
		y = v.y * s;
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
	public void divide(Vector2 v, float s){
		assert(Math.abs(s) > 0.0): "division by zero";
		x = v.x / s;
		y = v.y / s;
	}

	/**
	 * 内積
	 * @param v
	 * @return
	 */
	public float inner(Vector2 v){
		return x * v.x + y * v.y;
	}

	/**
	 * 外積
	 * @param v
	 * @return
	 */
	public float outer(Vector2 v){
		return x * v.y - y * v.x;
	}

	/**
	 * 長さの二乗
	 * @return
	 */
	public float length_squared(){
		return x * x + y * y;
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
	public void normalize(Vector2 v){
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
	public void negate(Vector2 v){
		x = -v.x;
		y = -v.y;
	}

	/**
	 * 垂直か？
	 * @param v
	 * @return
	 */
	public boolean isPerpendicular(Vector2 v){
		return !(Math.abs(inner(v)) > 0.0f);
	}

	/**
	 * 平行か？
	 * @param v
	 * @return
	 */
	public boolean isParallel(Vector2 v){
		assert(FloatComparer.almostEquals(1.0f, length(), 1)): "this is not an unit vector.";
		assert(FloatComparer.almostEquals(1.0f, v.length(), 1)): "v is not an unit vector.";
		return !(Math.abs(inner(v)) < 1.0f);
	}

	/**
	 * ベクトル間の角度
	 * @param v
	 * @return The angle in radians
	 */
	public float angle(Vector2 v){
		return (float)Math.acos(inner(v));
	}

	/**
	 * v1 と v2 を線形補間し代入
	 * @param v1
	 * @param v2
	 * @param t		[0,1]
	 */
	public void lerp(Vector2 v1, Vector2 v2, float t){
		x = v1.x + (v2.x - v1.x) * t;
		y = v1.y + (v2.y - v1.y) * t;
	}

	/**
	 * v1 と v2 を球面線形補間し代入
	 * @param v1	‖v1‖ = 1
	 * @param v2	‖v2‖ = 1
	 * @param t		[0,1]
	 */
	public void slerp(Vector2 v1, Vector2 v2, float t){
		final float theta = v1.angle(v2);
		if(theta > 0.0f){
			final float fx = (float)Math.sin(theta * (1.0f - t));
			final float fy = (float)Math.sin(theta * t);
			final float cosec = 1.0f / (float)Math.sin(theta);
			x = (fx * v1.x + fy * v2.x) * cosec;
			y = (fx * v1.y + fy * v2.y) * cosec;
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
	public void BaryCentric(Vector2 v1, Vector2 v2, Vector2 v3, float f, float g){
		x = v1.x + f * (v2.x - v1.x) + g * (v3.x - v1.x);
		y = v1.y + f * (v2.y - v1.y) + g * (v3.y - v1.y);
	}

	@Override
	public String toString(){
		return "Vector2{" + "x=" + x + ", y=" + y + '}';
	}
}