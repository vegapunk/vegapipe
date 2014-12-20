package com.vega.vegapipe.pipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.vega.vegapipe.map.Mapper;

public class Pipe<I,O> {

	private final Mapper mapper;
	private final Pipe node;
	
	private Pipe(Mapper mapper, Pipe node){
		this.mapper = mapper;
		this.node = node;
	}
	
	//TODO quick test code, remove
	public static void main(String[] args){
		
		Mapper<String,String> mapA = new Mapper<String, String>() {
			public String apply(String in) {
				return in;
			}
		};
		Mapper<String, Integer> mapB = new Mapper<String, Integer>() {
			public Integer apply(String in) {
				return new Integer(in);
			}
		};
		Pipe<String, String> p = Pipe.newIns(a->a+1);
		Pipe<String, String> map = p.map(s->new Integer(s)).map(i->i+1).map(x->x.toString()).map(a->a+"a");
		Collection<String> run = map.run(Arrays.asList("1","2", "3"));
		for(String i : run){
			System.out.println(i);
		}
		
	}
	
	public static <I,O> Pipe<I,O> newIns(Mapper<I,O> mapper){
		return new Pipe(mapper, null);
	}
	
	public static <I,O> Pipe<I,O> newIns(Class<I> clazz){
		return new Pipe<I, O>(new NoMapMapper(),null);
	}
	
	public <T> Pipe<I,T> map(Mapper<O,T> mapper){
		return new Pipe<I,T>(mapper, this);
	}
	
	//TODO better name?
	public Collection<O> run(Collection<I> in){
		Collection<?> res = in;
		List<Mapper> transformations = getTransformations();
		for(Mapper m : transformations){
			res = mapIt(m, res);
		}
		return (Collection<O>) res;
	}

	private <T> Collection<?> mapIt(Mapper m, Collection<T> item) {
		Collection<O> res = new ArrayList<O>();
		for(T i : item){
			res.add((O) m.apply(i));
		}
		return res;
	}

	private List<Mapper> getTransformations() {
		List<Mapper> mappers = new ArrayList<Mapper>();
		Pipe p = this;
		while(p!=null && p.mapper!=null){
			mappers.add(p.mapper);
			p = p.node;
		}
		return reverse(mappers);
	}

	private <T> List<T> reverse(List<T> mappers) {
		List<T> res = new ArrayList<T>();
		for(int i = mappers.size()-1 ;  i>-1;i-- ){
			res.add(mappers.get(i));
		}
		return res;
	}
}
