package jp.gr.java_conf.hasenpfote;

import java.awt.geom.Point2D;

/**
 *
 * @author Hasenpfote
 */
public class BoundingBox{

	private Point2D.Float min;
	private Point2D.Float max;

	public BoundingBox(){
		min = new Point2D.Float();
		max = new Point2D.Float();
	}

	public Point2D.Float getMin(){ return min; }
	public Point2D.Float getMax(){ return max; }
}
