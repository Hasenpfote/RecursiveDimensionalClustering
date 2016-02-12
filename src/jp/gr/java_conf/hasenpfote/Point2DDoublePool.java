package jp.gr.java_conf.hasenpfote;

import java.awt.geom.Point2D;
import jp.gr.java_conf.hasenpfote.framework.ObjectPool;

/**
 *
 * @author Hasenpfote
 */
public class Point2DDoublePool extends ObjectPool<Point2D.Double>{

	public Point2DDoublePool(){ super(); }

	public Point2DDoublePool(int capacity){ super(capacity); }

	@Override
	protected Point2D.Double newInstance(){ return new Point2D.Double(); }
}
