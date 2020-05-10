package com.model;

public abstract class CompetitiveAlgorithm<T> {
	
//	Add algorithms
	abstract void addIteratively(T data);
	abstract void addRecursively(T data);
	
//	Add algorithms
	abstract void deleteIteratively(T data);
	abstract void deleteRecursively(T data);
	
//	Search algorithms
	abstract T searchIteratively(T data);
	abstract T searchRecursively(T data);
}
