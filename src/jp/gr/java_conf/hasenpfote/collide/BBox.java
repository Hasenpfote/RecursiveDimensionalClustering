package jp.gr.java_conf.hasenpfote.collide;

import java.awt.Graphics2D;


public class BBox extends Shape{

	double min_x, min_y;
	double max_x, max_y;

	public BBox(double min_x, double min_y, double max_x, double max_y){
		this.min_x = min_x;
		this.min_y = min_y;
		this.max_x = max_x;
		this.max_y = max_y;
	}

	@Override
	public double getCogX(){ return min_x + (max_x - min_x) * 0.5; }
	@Override
	public double getCogY(){ return min_y + (max_y - min_y) * 0.5; }
	@Override
	public double getMinX(){ return min_x; }
	@Override
	public double getMaxX(){ return max_x; }
	@Override
	public double getMinY(){ return min_y; }
	@Override
	public double getMaxY(){ return max_y; }
	@Override
	public void update(double dt){}
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawRect((int)min_x, (int)min_y, (int)(max_x - min_x), (int)(max_y - min_y));
	}
}
