package jp.gr.java_conf.hasenpfote;

/**
 * Created by Hasenpfote on 2016/01/31.
 */
public class CollisionPair{

	private CircularPlate first;
	private CircularPlate second;

	CollisionPair(){
		reset();
	}

	public void set(CircularPlate first, CircularPlate second){
		this.first = first;
		this.second = second;
	}

	public void reset(){
		first = null;
		second = null;
	}

	public CircularPlate getFirst(){
		return first;
	}

	public CircularPlate getSecond(){
		return second;
	}
}
