package jp.gr.java_conf.hasenpfote.collision;

import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 * Created by Hasenpfote on 2016/04/02.
 */
public class LineSegment2 {
	private Vector2 initial;
	private Vector2 terminal;

	public LineSegment2(){
	}

	public LineSegment2(Vector2 initial, Vector2 terminal){
		set(initial, terminal);
	}

	public void set(Vector2 initial, Vector2 terminal){
		this.initial = initial;
		this.terminal = terminal;
	}

	public Vector2 getInitial(){
		return initial;
	}

	public Vector2 getTerminal(){
		return terminal;
	}

	public float getDistance(){
		final float dx = terminal.x - initial.x;
		final float dy = terminal.y - initial.y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}

	public boolean isParallel(LineSegment2 seg){
		final float dx = seg.getTerminal().x - seg.getInitial().x;
		final float dy = seg.getTerminal().y - seg.getInitial().y;
		return !(Math.abs((terminal.x - initial.x) * dx + (terminal.y - initial.y) * dy) < (getDistance() * seg.getDistance()));
	}
}
