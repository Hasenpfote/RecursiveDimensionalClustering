
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import jp.gr.java_conf.hasenpfote.framework.GameObject;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;
import jp.gr.java_conf.hasenpfote.framework.RenderComponent;

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

	private static final BasicStroke bs = new BasicStroke(0.1f);

	public void update(CircularPlate circle, Graphics2D g2d){
		Stroke olds = g2d.getStroke();
		g2d.setStroke(bs);
		AffineTransform old = g2d.getTransform();
		g2d.setTransform(GameSystem.getInstance().getWorldToScreenMatrix());
		g2d.translate(circle.getPosition().x, circle.getPosition().y);
		g2d.scale(circle.getRadius(), circle.getRadius());
		g2d.drawOval(-1, -1, 2, 2);
		g2d.drawLine(0, 0, 1, 0);
		g2d.setTransform(old);
		g2d.setStroke(olds);
	}
}
