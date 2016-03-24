package jp.gr.java_conf.hasenpfote;

import jp.gr.java_conf.hasenpfote.framework.ObjectPool;
import jp.gr.java_conf.hasenpfote.math.Vector2f;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public class Vector2fPool extends ObjectPool<Vector2f>{

	public Vector2fPool(){
		super();
	}

	public Vector2fPool(int capacity){
		super(capacity);
	}

	@Override
	protected Vector2f newInstance() {
		return new Vector2f();
	}
}
