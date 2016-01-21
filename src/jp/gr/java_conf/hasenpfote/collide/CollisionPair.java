package jp.gr.java_conf.hasenpfote.collide;


public final class CollisionPair{

	public Shape first;
	public Shape second;

	public CollisionPair(Shape first, Shape second){
		this.first = first;
		this.second = second;
	}

}
