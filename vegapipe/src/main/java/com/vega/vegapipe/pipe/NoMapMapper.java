package com.vega.vegapipe.pipe;

import com.vega.vegapipe.map.Mapper;

public class NoMapMapper<T> implements Mapper<T,T> {

	public T apply(T in) {
		return in;
	}

}
