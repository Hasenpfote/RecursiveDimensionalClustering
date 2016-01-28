import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import jp.gr.java_conf.hasenpfote.framework.ObjectPool;


/**
 * Recursive Dimensional Clustering
 * @author Hasenpfote
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

	private final ArrayList<CircularPlate> sub_group = new ArrayList<>(MAX_OBJECTS);
	private final EntityPool entity_pool = new EntityPool(MAX_OBJECTS * 2 * 2);
	private final ArrayList<ArrayList<Entity>> boundaries_list = new ArrayList<>(MAX_DEPTH);
	private final HashMap<CircularPlate, Entity> x_axis_open_entities = new HashMap<>(MAX_OBJECTS);
	private final HashMap<CircularPlate, Entity> x_axis_close_entities = new HashMap<>(MAX_OBJECTS);
	private final HashMap<CircularPlate, Entity> y_axis_open_entities = new HashMap<>(MAX_OBJECTS);
	private final HashMap<CircularPlate, Entity> y_axis_close_entities = new HashMap<>(MAX_OBJECTS);

	private final ClusterPool cluster_pool = new ClusterPool(MAX_OBJECTS);
	private final ArrayList<Cluster> clusters = new ArrayList<>(MAX_OBJECTS);

	public Rdc(){
		for(int i = 0; i < MAX_DEPTH; i++){
			boundaries_list.add(new ArrayList<Entity>(MAX_OBJECTS * 2));
		}
	}

	public ArrayList<Cluster> getClusters(){
		return clusters;
	}

	/**
	 * クラスタに登録されているグループを処理する.
	 * @param group クラスタに登録されているグループ
	 */
	private void bruteForce(ArrayList<CircularPlate> group){
		int size = group.size();
		if(size < 1)
			return;
		//
		Cluster cluster = cluster_pool.allocate();
		for(CircularPlate object : group){
			cluster.group.add(object);
		}
		cluster.updateBoundingBox();

		clusters.add(cluster);
	}

	/**
	 * 境界を取得する.
	 * @param group 対象グループ
	 * @param axis 対象軸
	 * @param boundaries 出力用
	 */
	private void getBoundaries(ArrayList<CircularPlate> group, Axis axis, ArrayList<Entity> boundaries){
		Entity e;
		if(axis == Axis.X){
			for(CircularPlate object : group){
				// open
				if(x_axis_open_entities.containsKey(object)){
					e = x_axis_open_entities.get(object);
				}
				else{
					double x = object.px - object.r;
					e = entity_pool.allocate();
					e.set(object, Entity.Boundary.OPEN,  x + CONTACT_THRESHOLD);
					x_axis_open_entities.put(object, e);
				}
				boundaries.add(e);
				// close
				if(x_axis_close_entities.containsKey(object)){
					e = x_axis_close_entities.get(object);
				}
				else{
					double x = object.px + object.r;
					e = entity_pool.allocate();
					e.set(object, Entity.Boundary.CLOSE, x - CONTACT_THRESHOLD);
					x_axis_close_entities.put(object, e);
				}
				boundaries.add(e);
			}
		}
		else{
			for(CircularPlate object : group){
				// open
				if(y_axis_open_entities.containsKey(object)){
					e = y_axis_open_entities.get(object);
				}
				else{
					double y = object.py - object.r;
					e = entity_pool.allocate();
					e.set(object, Entity.Boundary.OPEN,  y + CONTACT_THRESHOLD);
					y_axis_open_entities.put(object, e);
				}
				boundaries.add(e);
				// close
				if(y_axis_close_entities.containsKey(object)){
					e = y_axis_close_entities.get(object);
				}
				else{
					double y = object.py + object.r;
					e = entity_pool.allocate();
					e.set(object, Entity.Boundary.CLOSE, y - CONTACT_THRESHOLD);
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
	private void recursiveClustering(ArrayList<CircularPlate> group, Axis axis1, Axis axis2, int depth){
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
	public void recursiveClustering(ArrayList<CircularPlate> group){
		reset();
		if(!group.isEmpty())
			recursiveClustering(group, Axis.X, Axis.Y, 0);
	}

	/**
	 *
	 */
	public void reset(){
		for(Cluster cluster : clusters){
			cluster.reset();
			cluster_pool.release(cluster);
		}
		clusters.clear();

		for(HashMap.Entry<CircularPlate, Entity> entry : x_axis_open_entities.entrySet()){
			entity_pool.release(entry.getValue());
		}
		x_axis_open_entities.clear();

		for(HashMap.Entry<CircularPlate, Entity> entry : x_axis_close_entities.entrySet()){
			entity_pool.release(entry.getValue());
		}
		x_axis_close_entities.clear();

		for(HashMap.Entry<CircularPlate, Entity> entry : y_axis_open_entities.entrySet()){
			entity_pool.release(entry.getValue());
		}
		y_axis_open_entities.clear();

		for(HashMap.Entry<CircularPlate, Entity> entry : y_axis_close_entities.entrySet()){
			entity_pool.release(entry.getValue());
		}
		y_axis_close_entities.clear();
	}

	/**
	 *
	 */
	private static class Entity implements Comparable<Entity>{

		public enum Boundary{
			OPEN,
			CLOSE
		}
		private CircularPlate object;
		private Boundary boundary;
		private double position;

		public Entity(){
		}

		public void set(CircularPlate object, Boundary boundary, double position){
			this.object = object;
			this.boundary = boundary;
			this.position = position;
		}

		@Override
		public int compareTo(Entity o) {
			double diff = position - o.position;
			if(diff < 0.0)
				return -1;
			if(diff > 0.0)
				return 1;
			return 0;
		}
	}

	/**
	 *
	 */
	private class EntityPool extends ObjectPool<Entity>{

		public EntityPool(){
			super();
		}

		public EntityPool(int capacity) {
			super(capacity);
		}

		@Override
		protected Entity newInstance() {
			return new Entity();
		}
	}

	/**
	 *
	 */
	/*
	public class CollisionPair{
		public CircularPlate first;
		public CircularPlate second;

		public CollisionPair(CircularPlate first, CircularPlate second){
			this.first = first;
			this.second = second;
		}
	}
	*/

	/**
	 *
	 */
	public class Cluster{

		public BoundingBox bbox = new BoundingBox();
		public ArrayList<CircularPlate> group = new ArrayList<>();

		public void updateBoundingBox(){
			CircularPlate object = group.get(0);
			double min_x = object.px - object.r;
			double min_y = object.py - object.r;
			double max_x = object.px + object.r;
			double max_y = object.py + object.r;
			double value;

			int size = group.size();
			for(int i = 1; i < size; i++){
				object = group.get(i);

				value = object.px - object.r;
				if(value < min_x){
					min_x = value;
				}
				value = object.py - object.r;
				if(value < min_y){
					min_y = value;
				}
				value = object.px + object.r;
				if(value > max_x){
					max_x = value;
				}
				value = object.py + object.r;
				if(value > max_y){
					max_y = value;
				}
			}
			bbox.getMin().setLocation(min_x, min_y);
			bbox.getMax().setLocation(max_x, max_y);
		}

		public void reset(){
			group.clear();
		}

	}

	/**
	 *
	 */
	private class ClusterPool extends ObjectPool<Cluster>{

		public ClusterPool(){
			super();
		}

		public ClusterPool(int capacity){
			super(capacity);
		}

		@Override
		protected Cluster newInstance(){
			return new Cluster();
		}
	}

}
