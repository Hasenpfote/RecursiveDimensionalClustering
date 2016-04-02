package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.FloatComparer;
import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class Ray2 {
	private Vector2 origin;
	private Vector2 direction;

	public Ray2(){
	}

	public Ray2(Vector2 origin, Vector2 direction){
		set(origin, direction);
	}

	public void set(Vector2 origin, Vector2 direction){
		assert(FloatComparer.almostEquals(1.0f, direction.length(), 1)): "direction is not an unit vector.";
		this.origin = origin;
		this.direction = direction;
	}

	public Vector2 getOrigin(){
		return origin;
	}

	public Vector2 getDirection(){
		return direction;
	}

	public boolean isParallel(Ray2 ray){
		return !(Math.abs(direction.inner(ray.getDirection())) < 1.0f);
	}
}
