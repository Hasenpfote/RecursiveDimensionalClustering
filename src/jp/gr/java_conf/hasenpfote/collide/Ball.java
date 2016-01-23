package jp.gr.java_conf.hasenpfote.collide;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;


public class Ball extends Shape{

	private static final BasicStroke bs = new BasicStroke(0.1f);

	public double px;
	public double py;
	public double vx;
	public double vy;
	public double r;
	public double m;
	public double inv_m;
	public double fx;
	public double fy;

	public Ball(double px, double py, double vx, double vy, double r){
		set(px, py, vx, vy, r);
	}

	public void set(double px, double py, double vx, double vy, double r){
		this.px = px;
		this.py = py;
		this.vx = vx;
		this.vy = vy;
		this.r = r;
		this.m = r * r * Math.PI;
		this.inv_m = 1.0 / this.m;
	}

	@Override
	public double getCogX(){ return px; }
	@Override
	public double getCogY(){ return py; }
	@Override
	public double getMinX(){ return px - r; }
	@Override
	public double getMaxX(){ return px + r; }
	@Override
	public double getMinY(){ return py - r; }
	@Override
	public double getMaxY(){ return py + r; }
	@Override
	public void update(double dt){
	}
	@Override
	public void render(Graphics2D g2d){
		AffineTransform old = g2d.getTransform();
		Stroke olds = g2d.getStroke();

		g2d.setTransform(GameSystem.getInstance().getWorldToScreenMatrix());
		g2d.translate(px, py);
		g2d.scale(r, r);
		g2d.setStroke(bs);
		g2d.drawOval(-1, -1, 2, 2);
		g2d.setStroke(olds);
		g2d.setTransform(old);
	}

	public void addForce(double fx, double fy){
		this.fx += fx;
		this.fy += fy;
	}

	public void clearForce(){
		fx = fy = 0;
	}
}
