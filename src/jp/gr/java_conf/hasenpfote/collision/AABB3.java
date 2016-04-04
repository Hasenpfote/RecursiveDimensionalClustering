package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.Vector3;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class AABB3{
	private final Vector3 minimum = new Vector3();
	private final Vector3 maximum = new Vector3();

	public AABB3(){
	}

	public AABB3(Vector3 minimum, Vector3 maximum){
		set(minimum, maximum);
	}

	public void set(Vector3 minimum, Vector3 maximum){
		this.minimum.set(minimum);
		this.maximum.set(maximum);
	}

	public Vector3 getMinimum(){
		return minimum;
	}

	public Vector3 getMaximum(){
		return maximum;
	}

	public boolean intersect(AABB3 aabb){
		final Vector3 min = aabb.getMinimum();
		final Vector3 max = aabb.getMaximum();
		if((maximum.x < min.x) || (minimum.x > max.x)
		 ||(maximum.y < min.y) || (minimum.y > max.y)
		 ||(maximum.z < min.z) || (minimum.z > max.z))
			return false;
		return true;
	}
}
