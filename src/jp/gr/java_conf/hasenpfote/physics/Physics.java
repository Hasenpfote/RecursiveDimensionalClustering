package jp.gr.java_conf.hasenpfote.physics;

import jp.gr.java_conf.hasenpfote.math.Vector2d;

/**
 * Created by Hasenpfote on 2016/02/01.
 */
public final class Physics{

	/**
	 * gravitational acceleration [m/s^2]
	 */
	public static final double G = 9.80665;

	/**
	 * 衝突応答
	 * @param e		反発係数 [0, 1]
	 * @param m1	質点1 の質量
	 * @param m2	質点2 の質量
	 * @param v1	質点1 の速度
	 * @param v2	質点2 の速度
	 * @return 		力積
	 */
	public static double calcImpulse(double e, double m1, double m2, double v1, double v2){
		double I = (m1 * m2) / (m1 + m2) * (1.0 + e) * (v2 - v1);
		return I;
	}

	/**
	 * 衝突応答
	 * @param impulse	力積
	 * @param e			反発係数 [0, 1]
	 * @param m1		質点1 の質量
	 * @param m2		質点2 の質量
	 * @param v1		質点1 の速度
	 * @param v2		質点2 の速度
	 */
	public static void calcImpulse(Vector2d impulse, double e, double m1, double m2, Vector2d v1, Vector2d v2){
		impulse.sub(v2, v1);			// relative velocity
		impulse.mul((m1 * m2) / (m1 + m2) * (1.0 + e));
	}

	/**
	 * 衝突応答
	 * @param impulse	力積
	 * @param n			質点1 から質点2 へ向かう法戦ベクトル
	 * @param e			反発係数 [0, 1]
	 * @param m1		質点1 の質量
	 * @param m2		質点2 の質量
	 * @param v1		質点1 の速度
	 * @param v2		質点2 の速度
	 */
	public static void calcImpulse(Vector2d impulse, Vector2d n, double e, double m1, double m2, Vector2d v1, Vector2d v2){
		impulse.sub(v2, v1);			// relative velocity
		double i = impulse.inner(n);	// v12・N
		double c = (m1 * m2) / (m1 + m2) * (1.0 + e) * i;
		impulse.mul(n, c);				// I = cN
	}

	/**
	 * 衝突応答
	 * @param impulse	力積
	 * @param n			質点1 から質点2 へ向かう法戦ベクトル
	 * @param e			反発係数 [0, 1]
	 * @param m1		質点1 の質量
	 * @param m2		質点2 の質量
	 * @param v1		質点1 の速度
	 * @param v2		質点2 の速度
	 * @param cd		安定化係数 (0, 1/Δt)
	 * @param d			めり込み量
	 */
	public static void calcImpulse(Vector2d impulse, Vector2d n, double e, double m1, double m2, Vector2d v1, Vector2d v2, double cd, double d){
		impulse.sub(v2, v1);			// relative velocity
		double i = impulse.inner(n);	// v12・N
		double c = (m1 * m2) / (m1 + m2) * ((1.0 + e) * i - cd * d);
		impulse.mul(n, c);				// I = cN
	}

}
