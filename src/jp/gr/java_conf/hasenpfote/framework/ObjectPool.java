/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.gr.java_conf.hasenpfote.framework;

import java.util.ArrayList;

/**
 * 簡易オブジェクトプール.
 * @author Hasenpfote
 */
public abstract class ObjectPool<T>{
	private static final int DEFAULT_CAPACITY = 32;
	private final ArrayList<T> objects;
	private final int capacity;

	public ObjectPool(){
		this(DEFAULT_CAPACITY);
	}

	public ObjectPool(int capacity){
		this.capacity = capacity;
		objects = new ArrayList<T>(capacity);
		fill();
	}

	protected abstract T newInstance();

	private void fill(){
		int capacity = this.capacity;
		for(int i = 0; i < capacity; i++){
			objects.add(newInstance());
		}
	}

	public final int getCapacity(){
		return capacity;
	}

	public final int getFreeSize(){
		return objects.size();
	}

	public final int getUsedSize(){
		return (capacity - objects.size());
	}

	/**
	 * プールから取得
	 * @return
	 */
	public T allocate(){
		T object = (objects.isEmpty())? null : objects.remove(objects.size()-1);
		assert(object != null): "Object pool of type " + this.getClass().getSimpleName() + " exhausted.";
		return object;
	}

	/**
	 * プールに戻す
	 */
	public void release(T object){
		assert(object != null): "object is null.";
		assert(objects.size() < capacity): "Array exhausted.";
		objects.add(object);
	}

}
