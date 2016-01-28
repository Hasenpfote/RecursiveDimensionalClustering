
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasenpfote
 */
public class BoundingBox {

	private Point2D.Double min;
	private Point2D.Double max;

	public BoundingBox(){
		min = new Point2D.Double();
		max = new Point2D.Double();
	}

	public Point2D.Double getMin(){ return min; }
	public Point2D.Double getMax(){ return max; }

}
