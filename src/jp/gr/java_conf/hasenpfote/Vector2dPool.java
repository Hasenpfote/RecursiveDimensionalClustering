package jp.gr.java_conf.hasenpfote;

import jp.gr.java_conf.hasenpfote.framework.ObjectPool;
import jp.gr.java_conf.hasenpfote.math.Vector2d;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public class Vector2dPool extends ObjectPool<Vector2d> {

	public Vector2dPool(){
		super();
	}

	public Vector2dPool(int capacity){
		super(capacity);
	}

	@Override
	protected Vector2d newInstance() {
		return new Vector2d();
	}
}
