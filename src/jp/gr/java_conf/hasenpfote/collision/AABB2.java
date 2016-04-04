package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class AABB2{

	private final Vector2 minimum = new Vector2();
	private final Vector2 maximum = new Vector2();

	public AABB2(){
	}

	public AABB2(Vector2 minimum, Vector2 maximum){
		set(minimum, maximum);
	}

	public void set(Vector2 minimum, Vector2 maximum){
		this.minimum.set(minimum);
		this.maximum.set(maximum);
	}

	public Vector2 getMinimum(){
		return minimum;
	}

	public Vector2 getMaximum(){
		return maximum;
	}

	public boolean intersect(AABB2 aabb){
		final Vector2 min = aabb.getMinimum();
		final Vector2 max = aabb.getMaximum();
		if((maximum.x < min.x) || (minimum.x > max.x)
		 ||(maximum.y < min.y) || (minimum.y > max.y))
			return false;
		return true;
	}
}
