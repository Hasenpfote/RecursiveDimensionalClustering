package jp.gr.java_conf.hasenpfote.math;

/**
 * Created by Hasenpfote on 2016/03/07.
 */
public class ComplexNumber{

	public float re;
	public float im;

	public ComplexNumber(){
	}

	public ComplexNumber(float re, float im){
		set(re, im);
	}

	public void set(float re, float im){
		this.re = re;
		this.im = im;
	}

	/**
	 * 極座標形式で設定.
	 * @param rho
	 * @param theta
	 */
	public void polar(float rho, float theta){
		re = rho * (float)Math.cos(theta);
		im = rho * (float)Math.sin(theta);
	}

	public void zero(){
		re = im = 0.0f;
	}

	public void identity(){
		re = 1.0f;
		im = 0.0f;
	}

	/**
	 * c1 と c2 の和.
	 * @param c1
	 * @param c2
	 */
	public void add(ComplexNumber c1, ComplexNumber c2){
		re = c1.re + c2.re;
		im = c1.im + c2.im;
	}

	/**
	 * c1 と c2 の差.
	 * @param c1
	 * @param c2
	 */
	public void subtract(ComplexNumber c1, ComplexNumber c2){
		re = c1.re - c2.re;
		im = c1.im - c2.im;
	}

	/**
	 * c の和の逆元.
	 * @param c
	 */
	public void negate(ComplexNumber c){
		re = -c.re;
		im = -c.im;
	}

	/**
	 * c1 と c2 の積.
	 * @param c1
	 * @param c2
	 */
	public void multiply(ComplexNumber c1, ComplexNumber c2){
		re = c1.re * c2.re - c1.im * c2.im;
		im = c1.re * c2.im + c1.im * c2.re;
	}

	/**
	 * c と s の積.
	 * @param c
	 * @param s
	 */
	public void multiply(ComplexNumber c, float s){
		re = c.re * s;
		im = c.im * s;
	}

	/**
	 * c と s の商.
	 * @param c
	 * @param s
	 */
	public void divide(ComplexNumber c, float s){
		assert(Math.abs(s) > 0.0f): "division by zero.";
		re = c.re / s;
		im = c.im / s;
	}

	/**
	 * c の積の逆元.
	 * @param c
	 */
	public void inverse(ComplexNumber c){
		float norm_sq = c.normSquared();
		assert(norm_sq > 0.0f): "division by zero.";
		norm_sq = 1.0f / norm_sq;
		re =  c.re * norm_sq;
		im = -c.im * norm_sq;
	}

	/**
	 * ノルムの二乗.
	 * @return
	 */
	public float normSquared(){
		return re * re + im * im;
	}

	/**
	 * ノルム.
	 * @return
	 */
	public float norm(){
		return (float)Math.sqrt(normSquared());
	}

	/**
	 * 共役.
	 * @param c
	 */
	public void conjugate(ComplexNumber c){
		re =  c.re;
		im = -c.im;
	}

	/**
	 * 偏角.
	 * @return
	 */
	public float argument(){
		return (float)Math.atan2(im, re);
	}

	/**
	 * 累乗.
	 * @param base
	 * @param exponent
	 */
	public void pow(ComplexNumber base, float exponent){
		final float rho = (float)Math.pow(base.norm(), exponent);
		final float arg = base.argument() * exponent;
		polar(rho, arg);
	}

	/**
	 * 正規化.
	 * @param c
	 */
	public void normalize(ComplexNumber c){
		divide(c, c.norm());
	}

	/**
	 * 自然対数.
	 * @param c
	 */
	public void ln(ComplexNumber c){
		re = (float)Math.log(c.norm());
		im = c.argument();
	}

	/**
	 * 自然対数の底 e の累乗.
	 * @param lnc
	 */
	public void exp(ComplexNumber lnc){
		polar((float)Math.exp(lnc.re), lnc.im);
	}

	/**
	 * 回転を表す複素数を生成.
	 * @param angle
	 */
	public void rotation(float angle){
		re = (float)Math.cos(angle);
		im = (float)Math.sin(angle);
	}

	@Override
	public String toString(){
		return "ComplexNumber Re(c)=" + re + ", Im(c)=" + im;
	}
}
