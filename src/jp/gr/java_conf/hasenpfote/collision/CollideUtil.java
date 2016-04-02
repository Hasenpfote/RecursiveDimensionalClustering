package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.*;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public final class CollideUtil{

	private CollideUtil(){}

	private static float[] roots = new float[2];
	private static float[] params = new float[2];

	/**
	 * minimum distance squared from a point to a point.
	 * @param a
	 * @param b
	 * @return
	 */
	public static float distanceSquared(Vector2 a, Vector2 b){
		final float dx = b.x - a.x;
		final float dy = b.y - a.y;
		return dx * dx + dy * dy;
	}

	/**
	 * minimum distance squared from a point to a point.
	 * @param a
	 * @param b
	 * @return
	 */
	public static float distanceSquared(Vector3 a, Vector3 b){
		final float dx = b.x - a.x;
		final float dy = b.y - a.y;
		final float dz = b.z - a.z;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * minimum distance from a point to a point.
	 * @param a
	 * @param b
	 * @return
	 */
	public static float distance(Vector2 a, Vector2 b){
		return (float)Math.sqrt(distanceSquared(a, b));
	}

	/**
	 * minimum distance from a point to a point.
	 * @param a
	 * @param b
	 * @return
	 */
	public static float distance(Vector3 a, Vector3 b){
		return (float)Math.sqrt(distanceSquared(a, b));
	}

	/**
	 * minimum distance from a point to a ray.
	 * @param point
	 * @param ray
	 * @return
	 */
	public static float minimumDistance(Vector2 point, Ray2 ray){
		final Vector2 org = ray.getOrigin();
		final Vector2 dir = ray.getDirection();
		return Math.abs(dir.x * (point.y - org.y) - dir.y * (point.x - org.x));
	}

	/**
	 * minimum distance from a point to a ray.
	 * @param point
	 * @param ray
	 * @return
	 */
	public static float minimumDistance(Vector3 point, Ray3 ray){
		final Vector3 org = ray.getOrigin();
		final Vector3 dir = ray.getDirection();
		final float cx = dir.y * (point.z - org.z) - dir.z * (point.y - org.y);
		final float cy = dir.z * (point.x - org.x) - dir.x * (point.z - org.z);
		final float cz = dir.x * (point.y - org.y) - dir.y * (point.x - org.x);
		return (float)Math.sqrt(cx * cx + cy * cy + cz * cz);
	}

	/**
	 * minimum distance from a point to a segment.
	 * @param point
	 * @param seg
	 * @return
	 */
	public static float minimumDistance(Vector2 point, LineSegment2 seg){
		final Vector2 initial = seg.getInitial();
		final Vector2 terminal = seg.getTerminal();
		final float d = distance(terminal, initial);
		if(d > 0.0f){
			final float a = Math.abs((terminal.x - initial.x) * (point.y - initial.y) - (terminal.y - initial.y) * (point.x - initial.x));
			return a / d;
		}
		return distance(point, initial);
	}

	/**
	 * minimum distance from a point to a segment.
	 * @param point
	 * @param seg
	 * @return
	 */
	public static float minimumDistance(Vector3 point, LineSegment3 seg){
		final Vector3 initial = seg.getInitial();
		final Vector3 terminal = seg.getTerminal();
		final float d = distance(terminal, initial);
		if(d > 0.0f){
			final float cx = (terminal.y - initial.y) * (point.z - initial.z) - (terminal.z - initial.z) * (point.y - initial.y);
			final float cy = (terminal.z - initial.z) * (point.x - initial.x) - (terminal.x - initial.x) * (point.z - initial.z);
			final float cz = (terminal.x - initial.x) * (point.y - initial.y) - (terminal.y - initial.y) * (point.x - initial.x);
			return (float)Math.sqrt(cx * cx + cy * cy + cz * cz) / d;
		}
		return distance(point, initial);
	}

	/**
	 * closest point on a circle from a ray.
	 * @param roots
	 * @param circle
	 * @param ray
	 * @return
	 */
	public static int closestPoint(float[] roots, Circle circle, Ray2 ray){
		final Vector2 center = circle.getCenter();
		final float r = circle.getRadius();
		final Vector2 org = ray.getOrigin();
		final Vector2 dir = ray.getDirection();
		final float dx = org.x - center.x;
		final float dy = org.y - center.y;
		final float b = 2.0f * (dx * dir.x + dy * dir.y);
		final float c = (dx * dx + dy * dy) - (r * r);
		return MathUtil.solveQuadratic(roots, 1.0f, b, c);
	}

	/**
	 * closest point on a circle from a ray.
	 * @param point
	 * @param circle
	 * @param ray
	 * @return
	 */
	public static boolean closestPoint(Vector2 point, Circle circle, Ray2 ray){
		final int res = closestPoint(roots, circle, ray);
		if(res < 0)
			return false;
		if(res == 0){
			if(roots[0] < 0.0f)
				return false;
			point.set(ray.getOrigin());
			point.madd(ray.getDirection(), roots[0]);
			return true;
		}
		if(roots[1] < 0.0f)
			return false;

		final float root = (roots[0] < 0.0f)? roots[1] : roots[0];
		point.set(ray.getOrigin());
		point.madd(ray.getDirection(), root);
		return true;
	}

	/**
	 * closest point on a circle from a line segment.
	 * @param roots
	 * @param circle
	 * @param seg
	 * @return
	 */
	public static int closestPoint(float[] roots, Circle circle, LineSegment2 seg){
		final Vector2 center = circle.getCenter();
		final float r = circle.getRadius();
		final Vector2 initial = seg.getInitial();
		final Vector2 terminal = seg.getTerminal();
		final float dx = initial.x - center.x;
		final float dy = initial.y - center.y;
		final float a = distanceSquared(terminal, initial);
		final float b = 2.0f * (dx * (terminal.x - initial.x) + dy * (terminal.y - initial.y));
		final float c = (dx * dx + dy * dy) - (r * r);
		return MathUtil.solveQuadratic(roots, a, b, c);
	}

	/**
	 * closest point on a circle from a line segment.
	 * @param point
	 * @param circle
	 * @param seg
	 * @return
	 */
	public static boolean closestPoint(Vector2 point, Circle circle, LineSegment2 seg){
		final int res = closestPoint(roots, circle, seg);
		if(res < 0)
			return false;
		if(res == 0){
			if(roots[0] < 0.0f || roots[0] > 1.0f)
				return false;
			point.lerp(seg.getInitial(), seg.getTerminal(), roots[0]);
			return true;
		}
		if(roots[0] >= 0.0f && roots[0] <= 1.0f){
			point.lerp(seg.getInitial(), seg.getTerminal(), roots[0]);
			return true;
		}
		if(roots[1] >= 0.0f && roots[1] <= 1.0f){
			point.lerp(seg.getInitial(), seg.getTerminal(), roots[1]);
			return true;
		}
		return false;
	}

	/**
	 * intersection point of two segments.
	 * @param params
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean intersectionPoint(float[] params, LineSegment2 a, LineSegment2 b){
		if(a.isParallel(b))
			return false;
		final Vector2 ai = a.getInitial();
		final Vector2 at = a.getTerminal();
		final Vector2 bi = b.getInitial();
		final Vector2 bt = b.getTerminal();
		final float adx = at.x - ai.x;
		final float ady = at.y - ai.y;
		final float bdx = bt.x - bi.x;
		final float bdy = bt.y - bi.y;
		final float s = ((bi.x - ai.x) * bdy - (bi.y - ai.y) * bdx) / (adx * bdy - ady * bdx);
		float t;
		if(Math.abs(bdx) > Math.abs(bdy)){
			t = (ai.x + s * adx - bi.x) / bdx;
		}
		else{
			t = (ai.y + s * ady - bi.y) / bdy;
		}
		params[0] = s;
		params[1] = t;
		return true;
	}

	/**
	 * intersection point of two segments.
	 * @param point
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean intersectionPoint(Vector2 point, LineSegment2 a, LineSegment2 b){
		if(!intersectionPoint(params, a, b))
			return false;
		if(params[0] < 0.0f || params[0] > 1.0f)
			return false;
		point.lerp(a.getInitial(), a.getTerminal(), params[0]);
		return true;
	}

}
