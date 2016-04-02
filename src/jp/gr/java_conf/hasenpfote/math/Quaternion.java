package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/03.
 */
public class Quaternion{

	public static final Quaternion ZERO = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
	public static final Quaternion IDENTITY  = new Quaternion(1.0f, 0.0f, 0.0f, 0.0f);

	public float w;
	public float x;
	public float y;
	public float z;

	public Quaternion(){
	}

	public Quaternion(Quaternion q){
		set(q);
	}

	public Quaternion(float w, float x, float y, float z){
		set(w, x, y, z);
	}

	public Quaternion(float s, Vector3 v){
		set(s, v);
	}

	public void set(Quaternion q){
		w = q.w;
		x = q.x;
		y = q.y;
		z = q.z;
	}

	public void set(float w, float x, float y, float z){
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(float s, Vector3 v){
		w = s;
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * zero quaternion
	 */
	public void zero(){
		w = x = y = z = 0.0f;
	}

	/**
	 * identity quaternion
	 */
	public void identity(){
		w = 1.0f;
		x = y = z = 0.0f;
	}

	/**
	 * q との和.
	 * @param q
	 */
	public void add(Quaternion q){
		add(this, q);
	}

	/**
	 * q1 と q2 の和.
	 * @param q1
	 * @param q2
	 */
	public void add(Quaternion q1, Quaternion q2){
		w = q1.w + q2.w;
		x = q1.x + q2.x;
		y = q1.y + q2.y;
		z = q1.z + q2.z;
	}

	/**
	 * q との差.
	 * @param q
	 */
	public void subtract(Quaternion q){
		subtract(this, q);
	}

	/**
	 * q1 と q2 の差.
	 * @param q1
	 * @param q2
	 */
	public void subtract(Quaternion q1, Quaternion q2){
		w = q1.w - q2.w;
		x = q1.x - q2.x;
		y = q1.y - q2.y;
		z = q1.z - q2.z;
	}

	/**
	 * 和の逆元.
	 */
	public void negate(){
		negate(this);
	}

	/**
	 * q の和の逆元.
	 * @param q
	 */
	public void negate(Quaternion q){
		w = -q.w;
		x = -q.x;
		y = -q.y;
		z = -q.z;
	}

	/**
	 * q との積.
	 * @param q
	 */
	public void multiply(Quaternion q){
		multiply(this, q);
	}

	/**
	 * q1 と q2 の積.
	 * @param q1
	 * @param q2
	 */
	public void multiply(Quaternion q1, Quaternion q2){
		final float _w = q1.w * q2.w - (q1.x * q2.x + q1.y * q2.y + q1.z * q2.z);
		final float _x = q1.w * q2.x + q2.w * q1.x + (q1.y * q2.z - q1.z * q2.y);
		final float _y = q1.w * q2.y + q2.w * q1.y + (q1.z * q2.x - q1.x * q2.z);
		final float _z = q1.w * q2.z + q2.w * q1.z + (q1.x * q2.y - q1.y * q2.x);
		set(_w, _x, _y, _z);
	}

	/**
	 * s との積.
	 * @param s
	 */
	public void multiply(float s){
		multiply(this, s);
	}

	/**
	 * q と s の積.
	 * @param q
	 * @param s
	 */
	public void multiply(Quaternion q, float s){
		w = q.w * s;
		x = q.x * s;
		y = q.y * s;
		z = q.z * s;
	}

	/**
	 * s との商.
	 * @param s
	 */
	public void divide(float s){
		divide(this, s);
	}

	/**
	 * q と s の商を代入.
	 * @param q
	 * @param s
	 */
	public void divide(Quaternion q, float s){
		assert(Math.abs(s) > 0.0): "division by zero.";
		w = q.w / s;
		x = q.x / s;
		y = q.y / s;
		z = q.z / s;
	}

	/**
	 * 積の逆元.
	 * <p>\f$  q^{-1} = \frac{q^{*}}{\|q\|^{2}}\f$</p>
	 */
	public void inverse(){
		inverse(this);
	}

	/**
	 * q の積の逆元.
	 * <p>\f$  q^{-1} = \frac{q^{*}}{\|q\|^{2}}\f$</p>
	 * @param q
	 */
	public void inverse(Quaternion q){
		float norm_sq = q.normSquared();
		assert(norm_sq > 0.0f): "division by zero.";
		norm_sq = 1.0f / norm_sq;
		w =  q.w * norm_sq;
		x = -q.x * norm_sq;
		y = -q.y * norm_sq;
		z = -q.z * norm_sq;
	}

	/**
	 * ノルムの二乗.
	 * @return	\f$\|q\|^{2} = q^{*}\otimes q\f$
	 */
	public float normSquared(){
		return w * w + x * x + y * y + z * z;
	}

	/**
	 * ノルム.
	 * @return \f$\|q\|\f$
	 */
	public float norm(){
		return (float)Math.sqrt(normSquared());
	}

	/**
	 * ベクトル部のノルム.
	 * @return	\f$\|q_{v}\|\f$
	 */
	public float normV(){
		return (float)Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 共役.
	 * <p>\f$q^{*} = [s,-v]\f$</p>
	 */
	public void conjugate(){
		conjugate(this);
	}

	/**
	 * q の共役.
	 * <p>\f$q^{*} = [s,-v]\f$</p>
	 * @param q
	 */
	public void conjugate(Quaternion q){
		w =  q.w;
		x = -q.x;
		y = -q.y;
		z = -q.z;
	}

	/**
	 * 正規化.
	 */
	public void normalize(){
		normalize(this);
	}

	/**
	 * q を正規化.
	 * @param q
	 */
	public void normalize(Quaternion q){
		divide(q, q.norm());
	}

	/**
	 * q との内積.
	 * @param q
	 * @return
	 */
	public float inner(Quaternion q){
		return w * q.w + x * q.x + y * q.y + z * q.z;
	}

	/**
	 * sinc 関数のテイラー級数による近似.
	 * <p>
	 * sinc(x)<br>
	 * \f$\frac{\sin{x}}{x} = 1 - \frac{1}{6}x^{2} + \frac{1}{120}x^{4} - \frac{1}{5040}x^{6}\f$
	 * </p>
	 * @param x
	 * @return
	 */
	private static float _sinc(float x){
		final float x2 = x * x;
		final float x4 = x2 * x2;
		return 1.0f - x2 / 6.0f + x4 / 120.0f - (x2 * x4) / 5040.0f;
	}

	/**
	 * sinc 関数の逆数のテイラー級数による近似.
	 * <p>
	 * reciprocal of sinc(x)<br>
	 * \f$\frac{x}{\sin{x}} = 1 + \frac{1}{6}x^{2} + \frac{7}{360}x^{4} + \frac{31}{15120}x^{6} + ･･･ \f$
	 * </p>
	 * @param x
	 * @return
	 */
	private static float _rsinc(float x){
		final float x2 = x * x;
		final float x4 = x2 * x2;
		return 1.0f + x2 / 6.0f + 7.0f * x4 / 360.0f + 31.0f * (x2 * x4) / 15120.0f;
	}

	/**
	 * q の自然対数.
	 * <p>\f$\ln{q}\f$</p>
	 * @param q
	 */
	public void ln(Quaternion q){
		final float i = q.normV();
		final float phi = (float)Math.atan2(i, q.w);
		final float norm = q.norm();

		float coef;
		if(i > 0.0){	// TODO: 少し余裕を持たせる
			coef = phi / i;
		}
		else{
			coef = _rsinc(phi) / norm;
		}
		w = (float)Math.log(norm);
		x = coef * q.x;
		y = coef * q.y;
		z = coef * q.z;
	}

	/**
	 * q の自然対数.
	 * <p>\f$\ln{q}\f$</p>
	 * @param q		an unit quaternion.
	 */
	public void ln_u(Quaternion q){
		final float i = q.normV();
		final float phi = (float)Math.atan2(i, q.w);
		final float norm = q.norm();
		assert(FloatComparer.almostEquals(1.0f, norm, 1)): "not an unit quaternion.";

		float r_sinc;
		if(i > 0.0f){	// TODO: 少し余裕を持たせる
			r_sinc = phi / (i / norm);
		}
		else{
			r_sinc = _rsinc(phi);
		}
		w = 0.0f;
		x = r_sinc * q.x;
		y = r_sinc * q.y;
		z = r_sinc * q.z;
	}

	/**
	 * 自然対数の底 e の累乗.
	 * <p>\f$e^{q}\f$</p>
	 * @param lnq
	 */
	public void exp(Quaternion lnq){
		final float i = lnq.normV();
		float sinc;
		if(i > 0.0f){	// TODO: 少し余裕を持たせる
			sinc = (float)Math.sin(i) / i;
		}
		else{
			sinc = _sinc(i);
		}
		final float exp_w = (float)Math.exp(lnq.w);
		w = exp_w * (float)Math.cos(i);
		x = exp_w * lnq.x * sinc;
		y = exp_w * lnq.y * sinc;
		z = exp_w * lnq.z * sinc;
	}

	/**
	 * 自然対数の底 e の累乗.
	 * <p>\f$e^{q}\f$</p>
	 * @param lnq 	a purely imaginary quaternion.
	 */
	public void exp_p(Quaternion lnq){
		assert(FloatComparer.almostEquals(0.0f, lnq.w, 1)): "not a purely imaginary quaternion.";
		final float i = lnq.normV();
		float sinc;
		if(i > 0.0f){	// TODO: 少し余裕を持たせる
			sinc = (float)Math.sin(i) / i;
		}
		else{
			sinc = _sinc(i);
		}
		w = (float)Math.cos(i);
		x = lnq.x * sinc;
		y = lnq.y * sinc;
		z = lnq.z * sinc;
	}

	@Deprecated
	/**
	 * 自然対数の底 e の累乗(for axis angle).
	 * @param lnq	a purely imaginary quaternion.
	 */
	public void exp_a(Quaternion lnq){
		assert(FloatComparer.almostEquals(0.0f, lnq.w, 1)): "not a purely imaginary quaternion.";
		final float half_theta = lnq.norm();
		final float theta = 2.0f * half_theta;

		if(theta > 0.0f){	// TODO: 少し余裕を持たせる
			final float sinc = (float)Math.sin(half_theta) / half_theta; // TODO: 1 / half_theta でよい
			w = (float)Math.cos(half_theta);
			x = lnq.x * sinc;
			y = lnq.y * sinc;
			z = lnq.z * sinc;
		}
		else{
			w = 1.0f;
			x = y = z = 0.0f;
		}
		/*
        double vx = 2.0 * lnq.x;
        double vy = 2.0 * lnq.y;
        double vz = 2.0 * lnq.z;
        double norm = Math.sqrt(vx * vx + vy * vy + vz * vz);

        w = Math.cos(norm * 0.5);
        if(norm > 0.0){
            double coef = Math.sin(norm * 0.5) / norm;
            x = vx * coef;
            y = vy * coef;
            z = vz * coef;
        }
        else{
            x = y = z = 0.0;
        }
		*/
	}

	/**
	 * q の累乗.
	 * <p>\f$base^{exponent}\f$</p>
	 * @param base
	 * @param exponent
	 */
	public void pow(Quaternion base, float exponent){
		ln(base);
		multiply(exponent);
		exp(this);
	}

	/**
	 * q の累乗.
	 * <p>\f$base^{exponent}\f$</p>
	 * @param base		an unit quaternion.
	 * @param exponent
	 */
	public void pow_u(Quaternion base, float exponent){
		ln_u(base);
		multiply(exponent);
		exp_p(this);
	}

	/**
	 * 任意軸周りの回転を表すクォータニオンを生成.
	 * @param axis		an unit vector.
	 * @param angle		an angle in radians.
	 */
	public void rotationAxis(Vector3 axis, float angle){
		assert(FloatComparer.almostEquals(1.0f, axis.length(), 1)): "axis is not an unit quaternion.";
		final float half_angle = angle * 0.5f;
		final float s = (float)Math.sin(half_angle);
		w = (float)Math.cos(half_angle);
		x = axis.x * s;
		y = axis.y * s;
		z = axis.z * s;
	}

	/**
	 * v1 と v2 間の最小弧回転を表すクォータニオンを生成.
	 * @param v1	an unit vector.
	 * @param v2	an unit vector.
	 */
	public void rotationShortestArc(Vector3 v1, Vector3 v2){
		final float d = v1.inner(v2);
		final float s = (float)Math.sqrt((1.0f + d) * 2.0f);
		w = s * 0.5f;
		x = (v1.y * v2.z - v1.z * v2.y) / s;
		y = (v1.z * v2.x - v1.x * v2.z) / s;
		z = (v1.x * v2.y - v1.y * v2.x) / s;
	}

	/**
	 * q1 と q2 の間の差分.
	 * <p>\f$q_{1}\otimes diff = q_{2}\f$</p>
	 * @param q1	an unit quaternion.
	 * @param q2	an unit quaternion.
	 */
	public void rotationalDifference(Quaternion q1, Quaternion q2){
		assert(FloatComparer.almostEquals(1.0f, q1.norm(), 1)): "q1 is not an unit quaternion.";
		assert(FloatComparer.almostEquals(1.0f, q2.norm(), 1)): "q2 is not an unit quaternion.";
		this.conjugate(q1);
		this.multiply(q2);
	}

	/**
	 * 回転軸と角度を計算.
	 * @param axis
	 * @return	an angle in radians.
	 */
	public float ToAxisAngle(Vector3 axis){
		assert(FloatComparer.almostEquals(1.0f, norm(), 1)): "quaternion is not an unit quaternion.";
		float i = normV();
		if(i > 0.0f){	// TODO: 少し余裕を持たせる
			float r_i = 1.0f / i;
			axis.x = x * r_i;
			axis.y = y * r_i;
			axis.z = z * r_i;
			return 2.0f * (float)Math.atan2(i, w);
		}
		else{
			axis.x = 0.0f;
			axis.y = 0.0f;
			axis.z = 0.0f;
			return 0.0f;
		}
	}

	/**
	 * 線形補間で q1 と q2 間を補間.
	 * @param q1
	 * @param q2
	 * @param t		\f$[0,1]\f$
	 */
	public void lerp(Quaternion q1, Quaternion q2, float t){
		assert(t >= 0.0f && t <= 1.0f): "t is not in range.";
		w = q1.w + t * (q2.w - q1.w);
		x = q1.x + t * (q2.x - q1.x);
		y = q1.y + t * (q2.y - q1.y);
		z = q1.z + t * (q2.z - q1.z);
	}

	/**
	 * 球面線形補間で q1 と q2 間を補間.
	 * @param q1	an unit quaternion.
	 * @param q2	an unit quaternion.
	 * @param t		\f$[0,1]\f$
	 * @param allowFlip
	 *			true:  360度周期で補間を行う(最大回転変化量は 180度)
	 *			false: 720度周期で補間を行う(最大回転変化量は 360度)
	 * allowFlip を true とするとことで最小弧の補間を行い、不要なスピンを低減することができる
	 */
	public void slerp(Quaternion q1, Quaternion q2, float t, boolean allowFlip){
		assert(t >= 0.0f && t <= 1.0f): "t is not in range.";
		boolean flipped = false;			// q1 または q2 の反転を表す
		float cos_t = q1.inner(q2);
		if(allowFlip && (cos_t < 0.0f)){	// 最小弧で補間を行う
			flipped = true;
			cos_t = -cos_t;
		}

		float fx, fy;
		if(FloatComparer.almostEquals(1.0f, Math.abs(cos_t), 1)){
			// |cosθ| ≈ 1 → sinθ ≈ 0 の時は線形補間に帰着
			fx = 1.0f - t;
			fy = t;
		}
		else{
			final float theta = (float)Math.acos(cos_t);
			final float cosec = 1.0f / (float)Math.sin(theta);
			fx = (float)Math.sin(theta * (1.0f - t)) * cosec;
			fy = (float)Math.sin(theta * t) * cosec;
		}
		if(flipped){
			fy = -fy;
		}
		w = fx * q1.w + fy * q2.w;
		x = fx * q1.x + fy * q2.x;
		y = fx * q1.y + fy * q2.y;
		z = fx * q1.z + fy * q2.z;
	}

	/**
	 * 球面三次補間で p と q 間を補間.
	 * <p>\f$S_{n}(t) = squad(t; q_{n}, q_{n+1}, a_{n}, b_{n+1})\f$</p>
	 * @param p		\f$q_{n}\f$
	 * @param q		\f$q_{n+1}\f$
	 * @param a     \f$a_{n}\f$
	 * @param b     \f$b_{n+1}\f$
	 * @param t     \f$[0,1]\f$
	 * @param temps 作業領域(サイズ 2 を必要とする)
	 */
	public void squad(Quaternion p, Quaternion q, Quaternion a, Quaternion b, float t, Quaternion[] temps){
		assert(t >= 0.0f && t <= 1.0f): "t is not in range.";
		temps[0].slerp(p, q, t, false);
		temps[1].slerp(a, b, t, false);
		slerp(temps[0], temps[1], 2.0f*t*(1.0f-t), false);
	}

	/**
	 * スプライン補間で利用する制御点を計算.
	 * <p>\f$a_{n} = b_{n} = q_{n}\otimes e^{-\frac{ln{(q_{n}^{-1}\otimes q_{n-1})} + ln{(q_{n}^{-1}\otimes q_{n+1})}}{4}}\f$</p>
	 * <pre>{@code
	 *      a = spline(q0, q1, q2);
	 *      b = spline(q1, q2, q3);
	 *      q = squad(q1, q2, a, b, t);
	 * }</pre>
	 * @param prev      \f$q_{n-1}\f$
	 * @param current   \f$q_{n}\f$
	 * @param next      \f$q_{n+1}\f$
	 * @param temps     作業領域(サイズ 2 を必要とする)
	 */
	public void spline(Quaternion prev, Quaternion current, Quaternion next, Quaternion[] temps){
		conjugate(current);
		temps[0].multiply(this, prev);
		temps[0].ln_u(temps[0]);
		temps[1].multiply(this, next);
		temps[1].ln_u(temps[1]);
		add(temps[0], temps[1]);
		multiply(-0.25f);
		exp_p(this);
		multiply(current, this);
	}

	/**
	 * 回転を表すクォータニオンから回転行列へ変換.
	 * @param m
	 */
	public void ToRotationMatrix(CMatrix4 m){
		final float x_sq = x * x;
		final float y_sq = y * y;
		final float z_sq = z * z;
		final float xy = x * y;
		final float yz = y * z;
		final float xz = x * z;
		final float wx = w * x;
		final float wy = w * y;
		final float wz = w * z;
		m.set(1.0f - 2.0f * (y_sq + z_sq), 2.0f * (xy - wz),            2.0f * (xz + wy),            0.0f,
			  2.0f * (xy + wz),            1.0f - 2.0f * (x_sq + z_sq), 2.0f * (yz - wx),            0.0f,
			  2.0f * (xz - wy),            2.0f * (yz + wx),            1.0f - 2.0f * (x_sq + y_sq), 0.0f,
			  0.0f,                        0.0f,                        0.0f,                        1.0f);
	}

	/**
	 * 回転を表すクォータニオンから回転行列へ変換.
	 * @param m
	 */
	public void ToRotationMatrix(RMatrix4 m){
		final float x_sq = x * x;
		final float y_sq = y * y;
		final float z_sq = z * z;
		final float xy = x * y;
		final float yz = y * z;
		final float xz = x * z;
		final float wx = w * x;
		final float wy = w * y;
		final float wz = w * z;
		m.set(1.0f - 2.0f * (y_sq + z_sq), 2.0f * (xy + wz),            2.0f * (xz - wy),            0.0f,
			  2.0f * (xy - wz),            1.0f - 2.0f * (x_sq + z_sq), 2.0f * (yz + wx),            0.0f,
			  2.0f * (xz + wy),            2.0f * (yz - wx),            1.0f - 2.0f * (x_sq + y_sq), 0.0f,
			  0.0f,                        0.0f,                        0.0f,                        1.0f);
	}

	@Override
	public String toString() {
		return "Quaternion{" + "w=" + w + ", x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
