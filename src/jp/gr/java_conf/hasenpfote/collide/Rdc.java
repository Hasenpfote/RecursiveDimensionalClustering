package jp.gr.java_conf.hasenpfote.collide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;



/**
 * Recursive Dimensional Clustering
 * @author 
 */
public final class Rdc{

	private enum Axis{
		X,
		Y,
		INVALID
	}

	/** 分割の閾値 */
	private static final int SUBDIVISION_THRESHOLD = 4;		// >= 3
	/** 境界の閾値 */
	private static final double CONTACT_THRESHOLD = 0.001;
	/** 最大再帰深度(>= 2) */
	private static final int MAX_DEPTH = 6;	// x, y, x, y, x, y
	/** 最大処理オブジェクト数(初期許容量に影響) */
	private static final int MAX_OBJECTS = 50;

	private ArrayList<Shape> sub_group = null;
	private ArrayList<ArrayList<Entity>> boundaries_list = null;
	private HashMap<Shape, Entity> x_axis_open_entities = null;
	private HashMap<Shape, Entity> x_axis_close_entities = null;
	private HashMap<Shape, Entity> y_axis_open_entities = null;
	private HashMap<Shape, Entity> y_axis_close_entities = null;
	private RdcDebugImpl debug = null;
	
	public Rdc(RdcDebugImpl debug){
		this.debug = debug;
		sub_group = new ArrayList<Shape>(MAX_OBJECTS);
		boundaries_list = new ArrayList<ArrayList<Entity>>(MAX_DEPTH);
		for(int i = 0; i < MAX_DEPTH; i++){
			boundaries_list.add(new ArrayList<Entity>(MAX_OBJECTS * 2));
		}
		x_axis_open_entities = new HashMap<Shape, Entity>(MAX_OBJECTS);
		x_axis_close_entities = new HashMap<Shape, Entity>(MAX_OBJECTS);
		y_axis_open_entities = new HashMap<Shape, Entity>(MAX_OBJECTS);
		y_axis_close_entities = new HashMap<Shape, Entity>(MAX_OBJECTS);
	}

	/**
	 * クラスタに登録されているグループを処理する.
	 * @param group クラスタに登録されているグループ
	 */
	private void bruteForce(ArrayList<Shape> group){
		int size = group.size();
		if(size < 1)
			return;
		for(int i = 0; i < (size - 1); i++){
			for(int j = i + 1; j < size; j++){
				// collide
				if(debug != null)
					debug.registerPair(group.get(i), group.get(j));
			}
		}
		// for debug
		if(debug != null)
			debug.registerCluster(group);
	}

	/**
	 * 境界を取得する.
	 * @param group 対象グループ
	 * @param axis 対象軸
	 * @param boundaries 出力用
	 */
	private void getBoundaries(ArrayList<Shape> group, Axis axis, ArrayList<Entity> boundaries){
		Entity e = null;
		if(axis == Axis.X){
			for(Shape object : group){
				// open
				if(x_axis_open_entities.containsKey(object)){
					e = x_axis_open_entities.get(object);
				}
				else{
					// TODO: alloc aaahhh
					e = new Entity(object, Entity.Boundary.OPEN,  object.getMinX() + CONTACT_THRESHOLD);
					x_axis_open_entities.put(object, e);
				}
				boundaries.add(e);
				// close
				if(x_axis_close_entities.containsKey(object)){
					e = x_axis_close_entities.get(object);
				}
				else{
					// TODO: alloc aaahhh
					e = new Entity(object, Entity.Boundary.CLOSE, object.getMaxX() - CONTACT_THRESHOLD);
					x_axis_close_entities.put(object, e);
				}
				boundaries.add(e);
			}
		}
		else{
			for(Shape object : group){
				// open
				if(y_axis_open_entities.containsKey(object)){
					e = y_axis_open_entities.get(object);
				}
				else{
					// TODO: alloc aaahhh
					e = new Entity(object, Entity.Boundary.OPEN,  object.getMinY() + CONTACT_THRESHOLD);
					y_axis_open_entities.put(object, e);
				}
				boundaries.add(e);
				// close
				if(y_axis_close_entities.containsKey(object)){
					e = y_axis_close_entities.get(object);
				}
				else{
					// TODO: alloc aaahhh
					e = new Entity(object, Entity.Boundary.CLOSE, object.getMaxY() - CONTACT_THRESHOLD);
					y_axis_close_entities.put(object, e);
				}
				boundaries.add(e);
			}
		}
	}

	/**
	 * 再帰クラスタリング.
	 * @param group 入力グループ
	 * @param axis1 対象軸
	 * @param axis2 次の軸
	 * @param depth 再帰深度
	 */
	private void recursiveClustering(ArrayList<Shape> group, Axis axis1, Axis axis2, int depth){
		if((group.size() < SUBDIVISION_THRESHOLD) || (axis1 == Axis.INVALID) || (depth >= MAX_DEPTH)){
			bruteForce(group);
		}
		else{
			ArrayList<Entity> boundaries = boundaries_list.get(depth);
			getBoundaries(group, axis1, boundaries);
			Collections.sort(boundaries);

			Axis new_axis1 = axis2;
			int count = 0;
			boolean divided = false;

			sub_group.clear();
			Iterator<Entity> it = boundaries.iterator();
			while(it.hasNext()){
				Entity e = it.next();
				if(e.boundary == Entity.Boundary.OPEN){
					count++;
					sub_group.add(e.object);
				}
				else{
					count--;
					if(count == 0){
						if(it.hasNext()){
							divided = true;
						}
						if(divided){
							new_axis1 = (axis1 == Axis.X)? Axis.Y : Axis.X;
						}
						recursiveClustering(sub_group, new_axis1, Axis.INVALID, depth + 1);
						sub_group.clear();
					}
				}
			}
			boundaries.clear();
		}
	}

	/**
	 * 再帰クラスタリング.
	 * @param group 入力グループ
	 */
	public void recursiveClustering(ArrayList<Shape> group){
		if(debug != null){
			debug.clearAll();
			debug.setNumObjects(group.size());
		}
		if(!group.isEmpty()){
			recursiveClustering(group, Axis.X, Axis.Y, 0);
			x_axis_open_entities.clear();
			x_axis_close_entities.clear();
			y_axis_open_entities.clear();
			y_axis_close_entities.clear();
		}
	}
}
