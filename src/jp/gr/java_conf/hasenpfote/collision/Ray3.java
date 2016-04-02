package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.FloatComparer;
import jp.gr.java_conf.hasenpfote.math.Vector3;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class Ray3 {
	private Vector3 origin;
	private Vector3 direction;

	public Ray3(){
	}

	public Ray3(Vector3 origin, Vector3 direction){
		set(origin, direction);
	}

	public void set(Vector3 origin, Vector3 direction){
		assert(FloatComparer.almostEquals(1.0f, direction.length(), 1)): "direction is not an unit vector.";
		this.origin = origin;
		this.direction = direction;
	}

	public Vector3 getOrigin(){
		return origin;
	}

	public Vector3 getDirection(){
		return direction;
	}

	public boolean isParallel(Ray3 ray){
		return !(Math.abs(direction.inner(ray.getDirection())) < 1.0f);
	}
}
