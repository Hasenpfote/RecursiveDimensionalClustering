
import jp.gr.java_conf.hasenpfote.math.Util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * RDC のデバッグ用描画コンポーネント
 * @author Hasenpfote
 */
public class RdcRenderComponent{

	private static final BoundingBoxRenderComponent bbox_rc = new BoundingBoxRenderComponent();

	public void update(Rdc rdc, Graphics2D g2d){
		ArrayList<Rdc.Cluster> clusters = rdc.getClusters();
		//
		Color old = g2d.getColor();
		g2d.setColor(Color.GREEN);
		for(Rdc.Cluster cluster : clusters){
			bbox_rc.update(cluster.getBoundingBox(), g2d);
		}
		//
		long num_combinations = 0;
		int num_objects = 0;
		for(Rdc.Cluster cluster : clusters){
			int size = cluster.getGroup().size();
			num_objects += size;
			num_combinations += (size > 2)? Util.combination(size, 2) : 1;
		}
		long max_combinations = (num_objects > 2)? Util.combination(num_objects, 2) : ((num_objects == 0)? 0 : 1);
		//
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.setColor(Color.YELLOW);
		g2d.drawString("objects=" + num_objects, 0, 2 * metrics.getHeight() + metrics.getAscent());
		g2d.drawString("clusters=" + clusters.size(), 0, 3 * metrics.getHeight() + metrics.getAscent());
		//g2d.drawString("collisions=" + pairs.size(), 0, 2 * metrics.getHeight() + metrics.getAscent());
		int rate = (int)((double)num_combinations / max_combinations * 100);
		g2d.drawString("combinations=" + num_combinations + " / " + max_combinations + " (" + rate + "%)", 0, 4 * metrics.getHeight() + metrics.getAscent());

		g2d.setColor(old);
	}
}
