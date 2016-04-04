package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.Vector3;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class LineSegment3 {
	private final Vector3 initial = new Vector3();
	private final Vector3 terminal = new Vector3();

	public LineSegment3(){
	}

	public LineSegment3(Vector3 initial, Vector3 terminal){
		set(initial, terminal);
	}

	public void set(Vector3 initial, Vector3 terminal){
		this.initial.set(initial);
		this.terminal.set(terminal);
	}

	public Vector3 getInitial(){
		return initial;
	}

	public Vector3 getTerminal(){
		return terminal;
	}

	public float getDistance(){
		final float dx = terminal.x - initial.x;
		final float dy = terminal.y - initial.y;
		final float dz = terminal.z - initial.z;
		return (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	public boolean isParallel(LineSegment3 seg){
		final float dx = seg.getTerminal().x - seg.getInitial().x;
		final float dy = seg.getTerminal().y - seg.getInitial().y;
		final float dz = seg.getTerminal().z - seg.getInitial().z;
		return !(Math.abs((terminal.x - initial.x) * dx + (terminal.y - initial.y) * dy + (terminal.z - initial.z) * dz) < (getDistance() * seg.getDistance()));
	}
}
