package jp.gr.java_conf.hasenpfote;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;

/**
 *
 * @author Hasenpfote
 */
public class LineSegmentRenderComponent{

	public void update(Point2D.Double s, Point2D.Double e, Graphics2D g2d){
		//
		Point2D.Double _s = new Point2D.Double();
		Point2D.Double _e = new Point2D.Double();

		AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();
		wtos.transform(s, _s);
		wtos.transform(e, _e);
		g2d.drawLine((int)_s.x, (int)_s.y, (int)_e.x, (int)_e.y);
	}
}
