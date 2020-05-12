package com.model;

import java.util.ArrayList;

public class MyArrayList<T> extends ArrayList<T> implements CompetitiveDataStructure<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void addIteratively(T data) {
		add(data);
	}
	
	@Override
	public void addRecursively(T data) {
		add(data);
	}
	
	@Override
	public void deleteIteratively(T data) {
		int idx = indexOf(data);
		if(idx != -1)
			remove(idx);
	}
	
	@Override
	public void deleteRecursively(T data) {
		deleteIteratively(data);
	}
	
	@Override
	public T searchIteratively(T data) {
		int idx = indexOf(data);
		if(idx == -1)
			return null;
		else
			return get(idx);
	}
	
	@Override
	public T searchRecursively(T data) {
		return searchIteratively(data);
	}
	
	@Override
	public void reset() {
		this.clear();
	}
}
