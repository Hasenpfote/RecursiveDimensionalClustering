package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/03.
 */
public class Quaternion{

	public static final Quaternion ZERO = new Quaternion(0.0, 0.0, 0.0, 0.0);
	public static final Quaternion IDENTITY  = new Quaternion(1.0, 0.0, 0.0, 0.0);

	public double w, x, y, z;

	public Quaternion(){
	}

	public Quaternion(Quaternion q){
		set(q);
	}

	public Quaternion(double w, double x, double y, double z){
		set(w, x, y, z);
	}

	public Quaternion(double s, Vector3d v){
		set(s, v);
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

	public void set(double s, Vector3d v){
		w = s;
		x = v.x;
		y = v.y;
		z = v.z;
	}

	/**
	 * zero quaternion
	 */
	public void zero(){
		w = x = y = z = 0.0;
	}

	/**
	 * identity quaternion
	 */
	public void identity(){
		w = 1.0;
		x = y = z = 0.0;
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
		final double _w = q1.w * q2.w - (q1.x * q2.x + q1.y * q2.y + q1.z * q2.z);
		final double _x = q1.w * q2.x + q2.w * q1.x + (q1.y * q2.z - q1.z * q2.y);
		final double _y = q1.w * q2.y + q2.w * q1.y + (q1.z * q2.x - q1.x * q2.z);
		final double _z = q1.w * q2.z + q2.w * q1.z + (q1.x * q2.y - q1.y * q2.x);
		set(_w, _x, _y, _z);
	}

	/**
	 * s との積.
	 * @param s
	 */
	public void multiply(double s){
		multiply(this, s);
	}

	/**
	 * q と s の積.
	 * @param q
	 * @param s
	 */
	public void multiply(Quaternion q, double s){
		w = q.w * s;
		x = q.x * s;
		y = q.y * s;
		z = q.z * s;
	}

	/**
	 * s との商.
	 * @param s
	 */
	public void divide(double s){
		divide(this, s);
	}

	/**
	 * q と s の商を代入.
	 * @param q
	 * @param s
	 */
	public void divide(Quaternion q, double s){
		assert(Math.abs(s) > 0.0): "division by zero.";
		multiply(q, 1.0 / s);
	}

	/**
	 * 積の逆元.
	 * <p>
	 * q^-1 = q* / ‖q‖^2
	 * </p>
	 */
	public void inverse(){
		inverse(this);
	}

	/**
	 * q の積の逆元.
	 * @param q
	 */
	public void inverse(Quaternion q){
		double norm_sq = q.normSquared();
		assert(norm_sq > 0.0): "division by zero.";
		norm_sq = 1.0 / norm_sq;
		w =  q.w * norm_sq;
		x = -q.x * norm_sq;
		y = -q.y * norm_sq;
		z = -q.z * norm_sq;
	}

	/**
	 * ノルムの二乗.
	 * @return	‖q‖^2 = q* ⊗ q
	 */
	public double normSquared(){
		return w * w + x * x + y * y + z * z;
	}

	/**
	 * ノルム.
	 * @return ‖q‖
	 */
	public double norm(){
		return Math.sqrt(normSquared());
	}

	/**
	 * ベクトル部のノルム.
	 * @return ‖qv‖
	 */
	public double normV(){
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 共役(q*).
	 */
	public void conjugate(){
		conjugate(this);
	}

	/**
	 * q の共役.
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
	public double inner(Quaternion q){
		return w * q.w + x * q.x + y * q.y + z * q.z;
	}

	/**
	 * sinc 関数のテイラー級数による近似.
	 * <p>
	 * sinc(x) = sin(x)/x = 1 - x^2 / 6 + x^4 / 120 - x^6 / 5040 + ...
	 * </p>
	 * @param x
	 * @return
	 */
	private static double _sinc(double x){
		final double x2 = x * x;
		final double x4 = x2 * x2;
		return 1.0 - x2 / 6.0 + x4 / 120.0 - (x2 * x4) / 5040.0;
	}

	/**
	 * sinc 関数の逆数のテイラー級数による近似.
	 * <p>
	 * reciprocal of sinc(x) = x/sin(x) = 1 + x^2 / 6 + 7x^4 / 360 + 31x^6 / 15120 + ...
	 * </p>
	 * @param x
	 * @return
	 */
	private static double _rsinc(double x){
		final double x2 = x * x;
		final double x4 = x2 * x2;
		return 1.0 + x2 / 6.0 + 7 * x4 / 360.0 + 31.0 * (x2 * x4) / 15120.0;
	}

	/**
	 * q の自然対数.
	 * @param q
	 */
	public void ln(Quaternion q){
		final double i = q.normV();
		final double phi = Math.atan2(i, q.w);
		final double norm = q.norm();

		double coef;
		if(i > 0.0){	// TODO: 少し余裕を持たせる
			coef = phi / i;
		}
		else{
			coef = _rsinc(phi) / norm;
		}
		w = Math.log(norm);
		x = coef * q.x;
		y = coef * q.y;
		z = coef * q.z;
	}

	/**
	 * q の自然対数.
	 * @param q	a unit quaternion.
	 */
	public void ln_u(Quaternion q){
		final double i = q.normV();
		final double phi = Math.atan2(i, q.w);
		final double norm = q.norm();
		assert(DoubleComparer.almostEquals(1.0, norm, 1)): "not a unit quaternion.";

		double r_sinc;
		if(i > 0.0){	// TODO: 少し余裕を持たせる
			r_sinc = phi / (i / norm);
		}
		else{
			r_sinc = _rsinc(phi);
		}
		w = 0.0;
		x = r_sinc * q.x;
		y = r_sinc * q.y;
		z = r_sinc * q.z;
	}

	/**
	 * lnq の指数部を計算.
	 * @param lnq
	 */
	public void exp(Quaternion lnq){
		final double i = lnq.normV();
		double sinc;
		if(i > 0.0){	// TODO: 少し余裕を持たせる
			sinc = Math.sin(i) / i;
		}
		else{
			sinc = _sinc(i);
		}
		final double exp_w = Math.exp(lnq.w);
		w = exp_w * Math.cos(i);
		x = exp_w * lnq.x * sinc;
		y = exp_w * lnq.y * sinc;
		z = exp_w * lnq.z * sinc;
	}

	/**
	 * lnq の指数部を計算.
	 * @param lnq 	a purely imaginary quaternion.
	 */
	public void exp_p(Quaternion lnq){
		assert(DoubleComparer.almostEquals(0.0, lnq.w, 1)): "not a purely imaginary quaternion.";
		final double i = lnq.normV();
		double sinc;
		if(i > 0.0){	// TODO: 少し余裕を持たせる
			sinc = Math.sin(i) / i;
		}
		else{
			sinc = _sinc(i);
		}
		w = Math.cos(i);
		x = lnq.x * sinc;
		y = lnq.y * sinc;
		z = lnq.z * sinc;
	}

	/**
	 * lnq の指数部を計算(for axis angle).
	 * @param lnq	a purely imaginary quaternion.
	 */
	public void exp_a(Quaternion lnq){
		assert(DoubleComparer.almostEquals(0.0, lnq.w, 1)): "not a purely imaginary quaternion.";
		final double half_theta = lnq.norm();
		final double theta = 2.0 * half_theta;

		if(theta > 0.0){	// TODO: 少し余裕を持たせる
			final double sinc = Math.sin(half_theta) / half_theta;
			w = Math.cos(half_theta);
			x = lnq.x * sinc;
			y = lnq.y * sinc;
			z = lnq.z * sinc;
		}
		else{
			w = 1.0;
			x = y = z = 0.0;
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
	 * 任意軸周りの回転を表すクォータニオンを生成.
	 * @param axis	任意軸(‖v‖ = 1)
	 * @param angle	角度(radian)
	 */
	public void rotationAxis(Vector3d axis, double angle){
		final double ha = angle * 0.5;	// half angle
		final double s = Math.sin(ha);
		w = Math.cos(ha);
		x = axis.x * s;
		y = axis.y * s;
		z = axis.z * s;
	}

	/**
	 * v1 と v2 間の最小弧回転を表すクォータニオンを生成.
	 * @param v1 ‖v1‖=1
	 * @param v2 ‖v2‖=1
	 */
	public void rotationShortestArc(Vector3d v1, Vector3d v2){
		final double d = v1.inner(v2);
		final double s = Math.sqrt((1.0 + d) * 2.0);
		w = s * 0.5;
		x = (v1.y * v2.z - v1.z * v2.y) / s;
		y = (v1.z * v2.x - v1.x * v2.z) / s;
		z = (v1.x * v2.y - v1.y * v2.x) / s;
	}

	/**
	 * 線形補間で q1 と q2 間を補間.
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
	 * 球面線形補間で q1 と q2 間を補間.
	 * @param q1		‖q1‖=1
	 * @param q2		‖q2‖=1
	 * @param t			[0,1]
	 * @param allowFlip
	 *			true:  360度周期で補間を行う(最大回転変化量は 180度)
	 *			false: 720度周期で補間を行う(最大回転変化量は 360度)
	 * allowFlip を true とするとことで最小弧の補間を行い、不要なスピンを低減することができる
	 */
	public void slerp(Quaternion q1, Quaternion q2, double t, boolean allowFlip){
		boolean flipped = false;		// q1 または q2 の反転を表す
		double cos_t = q1.inner(q2);
		if(allowFlip && (cos_t < 0.0)){	// 最小弧で補間を行う
			flipped = true;
			cos_t = -cos_t;
		}

		double fx, fy;
		if(DoubleComparer.almostEquals(1.0, Math.abs(cos_t), 1)){
			// |cosθ| ≈ 1 → sinθ ≈ 0 の時は線形補間に帰着
			fx = 1.0 - t;
			fy = t;
		}
		else{
			final double theta = Math.acos(cos_t);
			final double cosec = 1.0 / Math.sin(theta);
			fx = Math.sin(theta * (1.0 - t)) * cosec;
			fy = Math.sin(theta * t) * cosec;
		}
		if(flipped){
			fy = -fy;
		}
		w = fx * q1.w + fy * q2.w;
		x = fx * q1.x + fy * q2.x;
		y = fx * q1.y + fy * q2.y;
		z = fx * q1.z + fy * q2.z;
	}

	@Override
	public String toString() {
		return "Quaternion{" + "w=" + w + ", x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
