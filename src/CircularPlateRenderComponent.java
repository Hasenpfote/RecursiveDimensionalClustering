
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import jp.gr.java_conf.hasenpfote.framework.GameObject;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;
import jp.gr.java_conf.hasenpfote.framework.RenderComponent;
import jp.gr.java_conf.hasenpfote.math.Vector2d;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hasenpfote
 */
public class CircularPlateRenderComponent{

	private static final BasicStroke bs = new BasicStroke(0.05f);

	public void update(CircularPlate circle, Graphics2D g2d){
		Stroke olds = g2d.getStroke();
		g2d.setStroke(bs);
		AffineTransform old = g2d.getTransform();
		g2d.setTransform(GameSystem.getInstance().getWorldToScreenMatrix());
		g2d.translate(circle.getPosition().x, circle.getPosition().y);
		g2d.scale(circle.getRadius(), circle.getRadius());
		g2d.drawOval(-1, -1, 2, 2);
		//g2d.drawLine(0, 0, 1, 0);
		g2d.setTransform(old);
		g2d.setStroke(olds);
		//
		double d = circle.getLinearVelocity().length_squared();
		if(d > 0.0){
			d = 1.0 / Math.sqrt(d);
			double x = circle.getLinearVelocity().x * d;
			double y = circle.getLinearVelocity().y * d;
			Point2D.Double src_s = new Point2D.Double(circle.getPosition().x, circle.getPosition().y);
			Point2D.Double src_e = new Point2D.Double(circle.getPosition().x + x * circle.getRadius(), circle.getPosition().y + y * circle.getRadius());
			Point2D.Double dst_s = new Point2D.Double();
			Point2D.Double dst_e = new Point2D.Double();

			AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
			wtos.transform(src_s, dst_s);
			wtos.transform(src_e, dst_e);
			g2d.drawLine((int)dst_s.x, (int)dst_s.y, (int)dst_e.x, (int)dst_e.y);
		}
	}
}
