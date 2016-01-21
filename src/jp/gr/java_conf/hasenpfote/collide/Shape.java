package jp.gr.java_conf.hasenpfote.collide;
import java.awt.Graphics2D;


public abstract class Shape{

	public abstract double getCogX();	// center of gravity
	public abstract double getCogY();	// center of gravity
	public abstract double getMinX();
	public abstract double getMaxX();
	public abstract double getMinY();
	public abstract double getMaxY();
	public abstract void update(double dt);
	public abstract void render(Graphics2D g2d);

}
