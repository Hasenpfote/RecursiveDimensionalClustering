package jp.gr.java_conf.hasenpfote;

import java.awt.geom.Point2D;
import jp.gr.java_conf.hasenpfote.framework.ObjectPool;

/**
 *
 * @author Hasenpfote
 */
public class Point2DFloatPool extends ObjectPool<Point2D.Float>{

	public Point2DFloatPool(){ super(); }

	public Point2DFloatPool(int capacity){ super(capacity); }

	@Override
	protected Point2D.Float newInstance(){ return new Point2D.Float(); }
}
