package jp.gr.java_conf.hasenpfote;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import jp.gr.java_conf.hasenpfote.CircularPlate;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;

/**
 *
 * @author Hasenpfote
 */
public class CircularPlateRenderComponent{

	private static final BasicStroke bs = new BasicStroke(0.01f);

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
		float d = circle.getLinearVelocity().length_squared();
		if(d > 0.0f){
			d = 1.0f / (float)Math.sqrt(d);
			float x = circle.getLinearVelocity().x * d;
			float y = circle.getLinearVelocity().y * d;
			Point2D.Float src_s = new Point2D.Float(circle.getPosition().x, circle.getPosition().y);
			Point2D.Float src_e = new Point2D.Float(circle.getPosition().x + x * circle.getRadius(), circle.getPosition().y + y * circle.getRadius());
			Point2D.Float dst_s = new Point2D.Float();
			Point2D.Float dst_e = new Point2D.Float();

			AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
			wtos.transform(src_s, dst_s);
			wtos.transform(src_e, dst_e);
			g2d.drawLine((int)dst_s.x, (int)dst_s.y, (int)dst_e.x, (int)dst_e.y);
		}
	}
}
