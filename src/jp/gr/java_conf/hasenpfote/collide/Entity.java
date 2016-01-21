package jp.gr.java_conf.hasenpfote.collide;


public class Entity implements Comparable<Entity>{

	public enum Boundary{
		OPEN,
		CLOSE
	}
	
	public Shape object;
	public Boundary boundary;
	public double position;

	public Entity(Shape object, Boundary boundary, double position){
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
