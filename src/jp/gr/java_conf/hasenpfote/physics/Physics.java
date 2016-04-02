package jp.gr.java_conf.hasenpfote.physics;

import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 * Created by Hasenpfote on 2016/02/01.
 */
public final class Physics{

	/**
	 * gravitational acceleration [m/s^2]
	 */
	public static final float G = 9.80665f;

	/**
	 * 衝突応答
	 * @param e		反発係数 [0, 1]
	 * @param m1	質点1 の質量
	 * @param m2	質点2 の質量
	 * @param v1	質点1 の速度
	 * @param v2	質点2 の速度
	 * @return 		力積
	 */
	public static float calcImpulse(float e, float m1, float m2, float v1, float v2){
		float I = (m1 * m2) / (m1 + m2) * (1.0f + e) * (v2 - v1);
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
	public static void calcImpulse(Vector2 impulse, float e, float m1, float m2, Vector2 v1, Vector2 v2){
		impulse.subtract(v2, v1);			// relative velocity
		impulse.multiply((m1 * m2) / (m1 + m2) * (1.0f + e));
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
	public static void calcImpulse(Vector2 impulse, Vector2 n, float e, float m1, float m2, Vector2 v1, Vector2 v2){
		impulse.subtract(v2, v1);		// relative velocity
		float i = impulse.inner(n);		// v12・N
		float c = (m1 * m2) / (m1 + m2) * (1.0f + e) * i;
		impulse.multiply(n, c);			// I = cN
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
	public static void calcImpulse(Vector2 impulse, Vector2 n, float e, float m1, float m2, Vector2 v1, Vector2 v2, float cd, float d){
		impulse.subtract(v2, v1);		// relative velocity
		float i = impulse.inner(n);		// v12・N
		float c = (m1 * m2) / (m1 + m2) * ((1.0f + e) * i - cd * d);
		impulse.multiply(n, c);			// I = cN
	}

}
