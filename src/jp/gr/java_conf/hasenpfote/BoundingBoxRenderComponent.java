package jp.gr.java_conf.hasenpfote;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import jp.gr.java_conf.hasenpfote.framework.GameSystem;

/**
 *
 * @author Hasenpfote
 */
public class BoundingBoxRenderComponent{

	public void update(BoundingBox box, Graphics2D g2d){
		//
		Point2D.Double smin = new Point2D.Double();
		Point2D.Double smax = new Point2D.Double();

		AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
		wtos.transform(box.getMin(), smin);
		wtos.transform(box.getMax(), smax);
		g2d.drawRect((int)smin.x, (int)smax.y, (int)(smax.x-smin.x), (int)(smin.y-smax.y));
	}
}
