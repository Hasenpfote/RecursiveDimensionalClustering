package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class Circle {
	private final Vector2 center = new Vector2();
	private float radius;

	public Circle(){
	}

	public Circle(Vector2 center, float radius){
		set(center, radius);
	}

	public void set(Vector2 center, float radius){
		this.center.set(center);
		this.radius = radius;
	}

	public Vector2 getCenter(){
		return center;
	}

	public float getRadius(){
		return radius;
	}
}
