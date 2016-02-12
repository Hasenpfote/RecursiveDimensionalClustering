package jp.gr.java_conf.hasenpfote;

import java.awt.geom.Point2D;

/**
 *
 * @author Hasenpfote
 */
public class BoundingBox{

	private Point2D.Double min;
	private Point2D.Double max;

	public BoundingBox(){
		min = new Point2D.Double();
		max = new Point2D.Double();
	}

	public Point2D.Double getMin(){ return min; }
	public Point2D.Double getMax(){ return max; }
}
