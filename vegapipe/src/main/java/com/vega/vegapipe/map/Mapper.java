package com.vega.vegapipe.map;

public interface Mapper<I,O> {

	public O apply(I in);
	
}
