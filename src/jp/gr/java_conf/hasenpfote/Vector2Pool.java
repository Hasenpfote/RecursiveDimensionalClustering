package jp.gr.java_conf.hasenpfote;

import jp.gr.java_conf.hasenpfote.framework.ObjectPool;
import jp.gr.java_conf.hasenpfote.math.Vector2;

/**
 * Created by Hasenpfote on 2016/01/29.
 */
public class Vector2Pool extends ObjectPool<Vector2>{

	public Vector2Pool(){
		super();
	}

	public Vector2Pool(int capacity){
		super(capacity);
	}

	@Override
	protected Vector2 newInstance() {
		return new Vector2();
	}
}
