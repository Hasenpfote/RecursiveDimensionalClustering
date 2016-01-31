import jp.gr.java_conf.hasenpfote.framework.ObjectPool;

/**
 * Created by Hasepfote on 2016/01/31.
 */
public class CollisionPairPool extends ObjectPool<CollisionPair>{

	public CollisionPairPool(){ super();
	}

	public CollisionPairPool(int capacity){ super(capacity);
	}

	@Override
	protected CollisionPair newInstance(){ return new CollisionPair(); }
}
