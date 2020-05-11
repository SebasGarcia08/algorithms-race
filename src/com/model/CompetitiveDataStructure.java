package com.model;

public interface CompetitiveDataStructure <T> {
//	Add algorithms
	abstract void addIteratively(T data);
	abstract void addRecursively(T data);
	
//	Add algorithms
	abstract void deleteIteratively(T data);
	abstract void deleteRecursively(T data);
	
//	Search algorithms
	abstract T searchIteratively(T data);
	abstract T searchRecursively(T data);
	
//	For clean the dataStructure
	abstract void reset();
}