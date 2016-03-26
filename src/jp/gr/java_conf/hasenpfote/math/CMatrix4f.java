package jp.gr.java_conf.hasenpfote.math;

/**
 * Column matrix.
 * Created by Hasenpfote on 2016/03/25.
 */
public final class CMatrix4f{

	public float m11;
	public float m21;
	public float m31;
	public float m41;

	public float m12;
	public float m22;
	public float m32;
	public float m42;

	public float m13;
	public float m23;
	public float m33;
	public float m43;

	public float m14;
	public float m24;
	public float m34;
	public float m44;

	public CMatrix4f(){
	}

	public CMatrix4f(float[] m){
		set(m);
	}

	public void set(float m11, float m12, float m13, float m14,
					float m21, float m22, float m23, float m24,
					float m31, float m32, float m33, float m34,
					float m41, float m42, float m43, float m44){
		this.m11 = m11; this.m12 = m12; this.m13 = m13; this.m14 = m14;
		this.m21 = m21; this.m22 = m22; this.m23 = m23; this.m24 = m24;
		this.m31 = m31; this.m32 = m32; this.m33 = m33; this.m34 = m34;
		this.m41 = m41; this.m42 = m42; this.m43 = m43; this.m44 = m44;
	}

	public void set(float[] m){
		set(m[0], m[4], m[8],  m[12],
			m[1], m[5], m[9],  m[13],
			m[2], m[6], m[10], m[14],
			m[3], m[7], m[10], m[15]);
	}

	public void get(float[] m){
		m[0] = m11;  m[1] = m21;  m[3] = m31;  m[4] = m41;
		m[5] = m12;  m[6] = m22;  m[7] = m32;  m[8] = m42;
		m[9] = m13;  m[10] = m23; m[11] = m33; m[12] = m43;
		m[13] = m14; m[14] = m24; m[15] = m34; m[16] = m44;
	}

	public void identity(){
		set(1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f);
	}

	public void zero(){
		set(0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f);
	}

	/**
	 * m1 と m2 の和.
	 * @param m1
	 * @param m2
	 */
	public void add(CMatrix4f m1, CMatrix4f m2){
		set(m1.m11 + m2.m11, m1.m12 + m2.m12, m1.m13 + m2.m13, m1.m14 + m2.m14,
			m1.m21 + m2.m21, m1.m22 + m2.m22, m1.m23 + m2.m23, m1.m24 + m2.m24,
			m1.m31 + m2.m31, m1.m32 + m2.m32, m1.m33 + m2.m33, m1.m34 + m2.m34,
			m1.m41 + m2.m41, m1.m42 + m2.m42, m1.m43 + m2.m43, m1.m44 + m2.m44);
	}

	/**
	 * m1 と m2 の差.
	 * @param m1
	 * @param m2
	 */
	public void subtract(CMatrix4f m1, CMatrix4f m2){
		set(m1.m11 - m2.m11, m1.m12 - m2.m12, m1.m13 - m2.m13, m1.m14 - m2.m14,
			m1.m21 - m2.m21, m1.m22 - m2.m22, m1.m23 - m2.m23, m1.m24 - m2.m24,
			m1.m31 - m2.m31, m1.m32 - m2.m32, m1.m33 - m2.m33, m1.m34 - m2.m34,
			m1.m41 - m2.m41, m1.m42 - m2.m42, m1.m43 - m2.m43, m1.m44 - m2.m44);
	}

	/**
	 * s と m の積.
	 * @param s
	 * @param m
	 */
	public void multiply(float s, CMatrix4f m){
		set(s * m.m11, s * m.m12, s * m.m13, s * m.m14,
			s * m.m21, s * m.m22, s * m.m23, s * m.m24,
			s * m.m31, s * m.m32, s * m.m33, s * m.m34,
			s * m.m41, s * m.m42, s * m.m43, s * m.m44);
	}

	/**
	 * m1 と m2 の積.
	 * @param m1
	 * @param m2
	 */
	public void multiply(CMatrix4f m1, CMatrix4f m2){
		set(m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31 + m1.m14 * m2.m41,
			m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32 + m1.m14 * m2.m42,
			m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33 + m1.m14 * m2.m43,
			m1.m11 * m2.m14 + m1.m12 * m2.m24 + m1.m13 * m2.m34 + m1.m14 * m2.m44,

			m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31 + m1.m24 * m2.m41,
			m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32 + m1.m24 * m2.m42,
			m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33 + m1.m24 * m2.m43,
			m1.m21 * m2.m14 + m1.m22 * m2.m24 + m1.m23 * m2.m34 + m1.m24 * m2.m44,

			m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31 + m1.m34 * m2.m41,
			m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32 + m1.m34 * m2.m42,
			m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33 + m1.m34 * m2.m43,
			m1.m31 * m2.m14 + m1.m32 * m2.m24 + m1.m33 * m2.m34 + m1.m34 * m2.m44,

			m1.m41 * m2.m11 + m1.m42 * m2.m21 + m1.m43 * m2.m31 + m1.m44 * m2.m41,
			m1.m41 * m2.m12 + m1.m42 * m2.m22 + m1.m43 * m2.m32 + m1.m44 * m2.m42,
			m1.m41 * m2.m13 + m1.m42 * m2.m23 + m1.m43 * m2.m33 + m1.m44 * m2.m43,
			m1.m41 * m2.m14 + m1.m42 * m2.m24 + m1.m43 * m2.m34 + m1.m44 * m2.m44);
	}

	/**
	 * 転置.
	 * @param m
	 */
	public void transpose(CMatrix4f m){
		set(m.m11, m.m21, m.m31, m.m41,
			m.m12, m.m22, m.m32, m.m42,
			m.m13, m.m23, m.m33, m.m43,
			m.m14, m.m24, m.m34, m.m44);
	}

	/**
	 * 逆.
	 * @param m
	 */
	public void inverse(CMatrix4f m){
		// TODO:
	}

	/**
	 * トレース.
	 * @return
	 */
	public float trace(){
		return m11 + m22 + m33 + m44;
	}

	/**
	 * 平行移動.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translation(float x, float y, float z){
		identity();
		m14 = x;
		m24 = y;
		m34 = z;
	}

	/**
	 * スケーリング.
	 * @param x
	 * @param y
	 * @param z
	 */
	public void scaling(float x, float y, float z){
		identity();
		m11 = x;
		m22 = y;
		m33 = z;
	}

	/**
	 * X 軸周りの回転を表す行列を生成.
	 * @param angle		an angle in radians.
	 */
	public void rotationX(float angle){
		identity();
		final float s = (float)Math.sin(angle);
		final float c = (float)Math.cos(angle);
		m22 = c; m23 =-s;
		m32 = s; m33 = c;
	}

	/**
	 * Y 軸周りの回転を表す行列を生成.
	 * @param angle		an angle in radians.
	 */
	public void rotationY(float angle){
		identity();
		final float s = (float)Math.sin(angle);
		final float c = (float)Math.cos(angle);
		m11 = c; m13 = s;
		m31 =-s; m33 = c;
	}

	/**
	 * Z 軸周りの回転を表す行列を生成.
	 * @param angle		an angle in radians.
	 */
	public void rotationZ(float angle){
		identity();
		final float s = (float)Math.sin(angle);
		final float c = (float)Math.cos(angle);
		m11 = c; m12 =-s;
		m21 = s; m22 = c;
	}

	/**
	 * 任意軸周りの回転を表す行列を生成.
	 * @param axis		an unit vector.
	 * @param angle		an angle in radians.
	 */
	public void rotationAxis(Vector3f axis, float angle){
		assert(FloatComparer.almostEquals(1.0f, axis.length(), 1)): "axis is not an unit vector.";
		final float x = axis.x;
		final float y = axis.y;
		final float z = axis.z;
		final float s = (float)Math.sin(angle);
		final float c = (float)Math.cos(angle);
		final float vers = 1.0f - c;
		set(x*x*vers+c,   x*y*vers-z*s, x*z*vers+y*s, 0.0f,
			x*y*vers+z*s, y*y*vers+c,   y*z*vers-x*s, 0.0f,
			x*z*vers-y*s, y*z*vers+x*s, z*z*vers+c,   0.0f,
			0.0f,         0.0f,         0.0f,         1.0f);
	}

	/**
	 * 回転行列からクォータニオンへ変換.
	 * @param q
	 */
	public void ToRotationQuaternion(Quaternion q){
		final float tr = trace();
		if(tr >= 1.0f){
			// |w| が最大
			q.w = (float)Math.sqrt(tr) * 0.5f;
			final float r_4w = 1.0f / (4.0f * q.w);  // 1/4|w|
			q.x = (m32 - m23) * r_4w;	// 4wx / 4|w|
			q.y = (m13 - m31) * r_4w;	// 4wy / 4|w|
			q.z = (m21 - m12) * r_4w;	// 4wz / 4|w|
		}
		else
		if((m11 > m22) && (m11 > m33)){
			// |x| が最大
			q.x = (float)Math.sqrt(m11 - m22 - m33 + 1.0f) * 0.5f;
			final float r_4x = 1.0f / (4.0f * q.x);  // 1/4|x|
			q.y = (m12 + m21) * r_4x;	// 4xy / 4|x|
			q.z = (m13 + m31) * r_4x;	// 4xz / 4|x|
			q.w = (m32 - m23) * r_4x;	// 4wx / 4|x|
		}
		else
		if((m22 > m33)){
			// |y| が最大
			q.y = (float)Math.sqrt(m22 - m33 - m11 + 1.0f) * 0.5f;
			final float r_4y = 1.0f / (4.0f * q.y);  // 1/4|y|
			q.x = (m12 + m21) * r_4y;	// 4xy / 4|y|
			q.z = (m32 + m23) * r_4y;	// 4yz / 4|y|
			q.w = (m13 - m31) * r_4y;	// 4wy / 4|y|
		}
		else{
			// |z| が最大
			q.z = (float)Math.sqrt(m33 - m11 - m22 + 1.0f) * 0.5f;
			final float r_4z = 1.0f / (4.0f * q.z);  // 1/4|z|
			q.x = (m13 + m31) * r_4z;	// 4xz / 4|z|
			q.y = (m32 + m23) * r_4z;	// 4yz / 4|z|
			q.w = (m21 - m12) * r_4z;	// 4wz / 4|z|
		}
	}

	/**
	 * 右手座標系のビュー行列を生成.
	 * @param position	位置
	 * @param target	注視点
	 * @param up		ワールドの上方向
	 */
	public void lookAt(Vector3f position, Vector3f target, Vector3f up){
		Vector3f zaxis = new Vector3f();
		zaxis.subtract(position, target);
		zaxis.normalize();

		Vector3f xaxis = new Vector3f();
		xaxis.cross(up, zaxis);
		xaxis.normalize();

		Vector3f yaxis = new Vector3f();
		yaxis.cross(zaxis, xaxis);

		set(xaxis.x, xaxis.y, xaxis.z, -xaxis.inner(position),
			yaxis.x, yaxis.y, yaxis.z, -yaxis.inner(position),
			zaxis.x, zaxis.y, zaxis.z, -zaxis.inner(position),
			0.0f,    0.0f,    0.0f,    1.0f);
	}

	/**
	 * 右手座標系の射影行列を生成(like an OpenGL).
	 * @param fovy			total field of view in the YZ plane.(an angle in radians.)
	 * @param aspectRatio	aspect ratio of view window.(width:height)
	 * @param near			positive distance from camera to near clipping plane.
	 * @param far			positive distance from camera to far clipping plane.
	 */
	public void perspective(float fovy, float aspectRatio, float near, float far){
		final float cot = 1.0f / (float)Math.tan(fovy * 0.5f);
		final float q = 1.0f / (far - near);
		set(cot / aspectRatio, 0.0f, 0.0f,              0.0f,
			0.0f,              cot,  0.0f,              0.0f,
			0.0f,              0.0f, -(far + near) * q, -2.0f * far * near * q,
			0.0f,			   0.0f, -1.0f,             0.0f);
	}

	/**
	 * 右手座標系の射影行列を生成(like an OpenGL).
	 * @param top		top of view volume at the near clipping plane.
	 * @param bottom	bottom of view volume at the near clipping plane.
	 * @param left		left of view volume at the near clipping plane.
	 * @param right     right of view volume at the near clipping plane.
	 * @param near		positive distance from camera to near clipping plane.
	 * @param far		positive distance from camera to far clipping plane.
	 */
	public void frustum(float top, float bottom, float left, float right, float near, float far){
		final float w = 2.0f * near / (right - left);
		final float h = 2.0f * near / (top - bottom);
		final float q = 1.0f / (far - near);
		final float woff = (right + left) / (right - left);
		final float hoff = (top + bottom) / (top - bottom);
		set(w,    0.0f, woff,              0.0f,
			0.0f, h,    hoff,              0.0f,
			0.0f, 0.0f, -(far + near) * q, -2.0f * far * near * q,
			0.0f, 0.0f, -1.0f,             0.0f);
	}

	/**
	 * 右手座標系の正射影行列を生成(like an OpenGL).
	 * @param top		top of parallel view volume.
	 * @param bottom	bottom of parallel view volume.
	 * @param left		left of parallel view volume.
	 * @param right		right of parallel view volume.
	 * @param near		positive distance from camera to near clipping plane.
	 * @param far		positive distance from camera to far clipping plane.
	 */
	public void ortho(float top, float bottom, float left, float right, float near, float far){
		final float w = 2.0f / (right - left);
		final float h = 2.0f / (top - bottom);
		final float q = 1.0f / (far - near);
		final float woff = -(right + left) / (right - left);
		final float hoff = -(top + bottom) / (top - bottom);
		set(w,    0.0f, 0.0f,      woff,
			0.0f, h,    0.0f,      hoff,
			0.0f, 0.0f, -2.0f * q, -(far + near) * q,
			0.0f, 0.0f, 0.0f,      1.0f);
	}

	@Override
	public String toString() {
		String sep = System.lineSeparator();
		return "CMatrix4f{" + sep +
				"[" + m11 + ", " + m12 + ", " + m13 + ", " + m14 + "]" + sep +
				"[" + m21 + ", " + m22 + ", " + m23 + ", " + m24 + "]" + sep +
				"[" + m31 + ", " + m32 + ", " + m33 + ", " + m34 + "]" + sep +
				"[" + m41 + ", " + m42 + ", " + m43 + ", " + m44 + "] }";
	}
}
