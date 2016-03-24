package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/23.
 */
public class Matrix4f {

	// column-major order
	//   0  4  8 12
	//   1  5  9 13
	//   2  6 10 14
	//   3  7 11 15

	public float m00;
	public float m10;
	public float m20;
	public float m30;

	public float m01;
	public float m11;
	public float m21;
	public float m31;

	public float m02;
	public float m12;
	public float m22;
	public float m32;

	public float m03;
	public float m13;
	public float m23;
	public float m33;

	public Matrix4f(){
	}

	public Matrix4f(float[] m){
		set(m);
	}

	public void set(float m00, float m01, float m02, float m03,
					float m10, float m11, float m12, float m13,
					float m20, float m21, float m22, float m23,
					float m30, float m31, float m32, float m33){
		this.m00 = m00;
		this.m10 = m10;
		this.m20 = m20;
		this.m30 = m30;

		this.m01 = m01;
		this.m11 = m11;
		this.m21 = m21;
		this.m31 = m31;

		this.m02 = m02;
		this.m12 = m12;
		this.m22 = m22;
		this.m32 = m32;

		this.m03 = m03;
		this.m13 = m13;
		this.m23 = m23;
		this.m33 = m33;
	}

	public void set(float[] m){
		m00 = m[0];
		m10 = m[1];
		m20 = m[2];
		m20 = m[3];

		m01 = m[4];
		m11 = m[5];
		m21 = m[6];
		m21 = m[7];

		m02 = m[8];
		m12 = m[9];
		m22 = m[10];
		m22 = m[11];

		m03 = m[12];
		m13 = m[13];
		m23 = m[14];
		m23 = m[15];
	}

	public void get(float[] m){
		m[0] = m00;
		m[1] = m10;
		m[2] = m20;
		m[3] = m30;

		m[4] = m01;
		m[5] = m11;
		m[6] = m21;
		m[7] = m31;

		m[8] = m02;
		m[9] = m12;
		m[10] = m22;
		m[11] = m32;

		m[12] = m03;
		m[13] = m13;
		m[14] = m23;
		m[15] = m33;
	}

	public void identity(){
		m00 = 1.0f;
		m10 = 0.0f;
		m20 = 0.0f;
		m30 = 0.0f;

		m01 = 0.0f;
		m11 = 1.0f;
		m21 = 0.0f;
		m31 = 0.0f;

		m02 = 0.0f;
		m12 = 0.0f;
		m22 = 1.0f;
		m32 = 0.0f;

		m03 = 0.0f;
		m13 = 0.0f;
		m23 = 0.0f;
		m33 = 1.0f;
	}

	/**
	 * トレース.
	 * @return
	 */
	public float trace(){
		return m00 + m11 + m22 + m33;
	}

	/**
	 * 回転行列からクォータニオンへ変換.
	 * @param q
	 */
	public void ToQuaternion(Quaternion q){
		final float tr = trace();
		if(tr >= 1.0f){
			// |w| が最大
			q.w = (float)Math.sqrt(tr) * 0.5f;
			final float r_4w = 1.0f / (4.0f * (float)q.w);  // 1/4|w|
			q.x = (m21 - m12) * r_4w;
			q.y = (m02 - m20) * r_4w;
			q.z = (m10 - m01) * r_4w;
		}
		else
		if((m00 > m11) && (m00 > m22)){
			// |x| が最大
			q.x = (float)Math.sqrt(m00 - m11 - m22 + 1.0f) * 0.5f;
			final float r_4x = 1.0f / (4.0f * (float)q.x);  // 1/4|x|
			q.y = (m01 + m10) * r_4x;
			q.z = (m02 + m20) * r_4x;
			q.w = (m21 - m12) * r_4x;
		}
		else
		if((m11 > m22)){
			// |y| が最大
			q.y = (float)Math.sqrt(m11 - m22 - m00 + 1.0f) * 0.5f;
			final float r_4y = 1.0f / (4.0f * (float)q.y);  // 1/4|y|
			q.x = (m01 + m10) * r_4y;
			q.z = (m21 + m12) * r_4y;
			q.w = (m02 - m20) * r_4y;
		}
		else{
			// |z| が最大
			q.z = (float)Math.sqrt(m22 - m00 - m11 + 1.0f) * 0.5f;
			final float r_4z = 1.0f / (4.0f * (float)q.z);  // 1/4|z|
			q.x = (m02 + m20) * r_4z;
			q.y = (m21 + m12) * r_4z;
			q.w = (m10 - m01) * r_4z;
		}
	}

	@Override
	public String toString() {
		String sep = System.lineSeparator();
		return "Matrix4f{" + sep +
					"[" + m00 + ", " + m01 + ", " + m02 + ", " + m03 + "]" + sep +
					"[" + m10 + ", " + m11 + ", " + m12 + ", " + m13 + "]" + sep +
					"[" + m20 + ", " + m21 + ", " + m22 + ", " + m23 + "]" + sep +
					"[" + m30 + ", " + m31 + ", " + m32 + ", " + m33 + "]" + sep +
				'}';
	}
}
