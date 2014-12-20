package com.vega.vegapipe.filter;

public interface Predicate<T> {

	public boolean apply(T item);
	
}
