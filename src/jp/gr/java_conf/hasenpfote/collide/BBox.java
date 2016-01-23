package jp.gr.java_conf.hasenpfote.collide;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;


public class BBox extends Shape{

	private final Point2D.Double min = new Point2D.Double();
	private final Point2D.Double max = new Point2D.Double();

	public BBox(double min_x, double min_y, double max_x, double max_y){
		min.setLocation(min_x, min_y);
		max.setLocation(max_x, max_y);
	}

	@Override
	public double getCogX(){ return min.x + (max.x - min.x) * 0.5; }
	@Override
	public double getCogY(){ return min.y + (max.y - min.y) * 0.5; }
	@Override
	public double getMinX(){ return min.x; }
	@Override
	public double getMaxX(){ return max.x; }
	@Override
	public double getMinY(){ return min.y; }
	@Override
	public double getMaxY(){ return max.y; }
	@Override
	public void update(double dt){}
	@Override
	public void render(Graphics2D g2d) {
		//
		Point2D.Double smin = new Point2D.Double();
		Point2D.Double smax = new Point2D.Double();

		AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
		wtos.transform(min, smin);
		wtos.transform(max, smax);
		g2d.drawRect((int)smin.x, (int)smax.y, (int)(smax.x-smin.x), (int)(smin.y-smax.y));
	}
}
