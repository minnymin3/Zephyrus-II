package com.minnymin.zephyrus.core.util.map;

/**
 * Zephyrus - MultiEntry.java
 * 
 * @author minnymin3
 *
 * @param <A> Key type
 * @param <B> First value type
 * @param <C> Second value type
 */
public class MultiEntry<A, B, C> {
	
	private A key = null;
	private B firstValue = null;
	private C secondValue = null;
	
	private int index;
	
	public MultiEntry(A key, MultiMap<A, B, C> map) {
		this.key = key;
		this.firstValue = map.getFirstValue(key);
		this.secondValue = map.getSecondValue(key);
		
		this.index = map.getIndex(key);
	}
 
 
	public A getKey() {
		return key;
	}
	
	public B getFirstValue() {
		return firstValue;
	}
	
	public C getSecondValue() {
		return secondValue;
	}
	
	public int getIndex() {
		return index;
	}
	
}
