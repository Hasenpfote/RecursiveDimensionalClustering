package jp.gr.java_conf.hasenpfote.collide;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import jp.gr.java_conf.hasenpfote.framework.GameSystem;



public class RdcDebugImpl implements IDebugRender{

	private ArrayList<BBox> clusters = null;
	private ArrayList<CollisionPair> pairs = null;
	private Font font = null;
	private int num_objects = 0;
	private long num_combinations = 0;
	private long max_combinations = 0;

	public RdcDebugImpl(){
		clusters = new ArrayList<BBox>();
		pairs = new ArrayList<CollisionPair>();
		font = new Font(Font.SERIF, Font.BOLD, 18);
	}

	@Override
	public void render(Graphics2D g2d){
		g2d.setColor(Color.GREEN);
		for(BBox cluster : clusters){
			cluster.render(g2d);
		}

		Point2D.Double p = new Point2D.Double();
		Point2D.Double s = new Point2D.Double();
		Point2D.Double e = new Point2D.Double();
		AffineTransform wtos = GameSystem.getInstance().getWorldToScreenMatrix();

		g2d.setColor(Color.RED);
		for(CollisionPair pair : pairs){
			p.setLocation(pair.first.getCogX(), pair.first.getCogY());
			wtos.transform(p, s);
			p.setLocation(pair.second.getCogX(), pair.second.getCogY());
			wtos.transform(p, e);
			g2d.drawLine((int)s.x, (int)s.y, (int)e.x, (int)e.y);
		}

		g2d.setFont(font);
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.setColor(Color.YELLOW);
		g2d.drawString("objects=" + num_objects, 0, metrics.getHeight() + metrics.getAscent());
		g2d.drawString("clusters=" + clusters.size(), 0, 2 * metrics.getHeight() + metrics.getAscent());
		//g2d.drawString("collisions=" + pairs.size(), 0, 2 * metrics.getHeight() + metrics.getAscent());
		int rate = (int)((double)num_combinations / max_combinations * 100);
		g2d.drawString("combinations=" + num_combinations + " / " + max_combinations + " (" + rate + "%)", 0, 3 * metrics.getHeight() + metrics.getAscent());
	}

	public void registerCluster(ArrayList<Shape> group){
		Shape object = group.get(0);
		double min_x = object.getMinX();
		double min_y = object.getMinY();
		double max_x = object.getMaxX();
		double max_y = object.getMaxY();
		int size = group.size();

		if(size >= 2)
			num_combinations += combination(size, 2);

		for(int i = 1; i < size; i++){
			object = group.get(i);
			if(object.getMinX() < min_x)
				min_x = object.getMinX();
			if(object.getMinY() < min_y)
				min_y = object.getMinY();
			if(object.getMaxX() > max_x)
				max_x = object.getMaxX();
			if(object.getMaxY() > max_y)
				max_y = object.getMaxY();
		}
		clusters.add(new BBox(min_x, min_y, max_x, max_y));
	}

	public void registerPair(Shape first, Shape second){
		Ball f = (Ball)first;
		Ball s = (Ball)second;
		double x = f.px - s.px;
		double y = f.py - s.py;
		double r = f.r + s.r;
		if((x * x + y * y) > (r * r))
			return;
		pairs.add(new CollisionPair(first, second));
	}

	public void clearAll(){
		clusters.clear();
		pairs.clear();
		num_combinations = 0;
	}

	public void setNumObjects(int num_objects){
		this.num_objects = num_objects;
		max_combinations = combination(num_objects, 2);
	}

	/**
	 * 順列
	 * @param	n
	 * @param	r
	 * @return	nPr = n! / (n-r)!
	 */
	public static long permutation(int n, int r){
		assert((n >= 0) && (r >= 0) && (n >= r));
		long npr = 1;
		for(int i = 0; i < r; i++){
			npr = npr * (n - i);
		}
		return npr;
	}

	/**
	 * 組合せ
	 * @param	n
	 * @param	r
	 * @return	nCr = nPr / r!
	 */
	public static long combination(int n, int r){
		assert((n >= 0) && (r >= 0) && (n >= r));
		long ncr = 1;
		for(int i = 1; i <= r; i++){
			ncr = ncr * (n - i + 1) / i;
		}
		return ncr;
	}

}
