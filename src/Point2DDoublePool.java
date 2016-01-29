
import java.awt.geom.Point2D;
import jp.gr.java_conf.hasenpfote.framework.ObjectPool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
