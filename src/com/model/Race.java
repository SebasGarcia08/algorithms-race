package com.model;

import java.util.Hashtable;
import java.util.Random;

public class Race {

	enum Mode {
		ITERATIVE, RECURSIVE
	}

	enum Algorithm {
		ADD, SEARCH, DELETE
	}
	
	private Hashtable<String, CompetitiveDataStructure<Long>> dataStructures;
	private Hashtable<String, Integer> operationsCounter;
	private long[] data;
	
	public Race() {
		dataStructures = new Hashtable<String, CompetitiveDataStructure<Long>>();
		dataStructures.put("BST", new BinarySearchTree<Long>());
		dataStructures.put("DLL", new DoublyLinkedList<Long>());
		dataStructures.put("AL",  new MyArrayList<Long>());
		
		operationsCounter = new Hashtable<String, Integer>();
		operationsCounter.put("BST", 0);
		operationsCounter.put("DLL", 0);
		operationsCounter.put("AL", 0);
	}
	
	public void start(Mode mode, Algorithm algorithm, int n) {
		
	}
	
	public void generateRandomNumbers(int n) {
		data = new long[n];
		Random r = new Random();
		for(int i = 0; i < n; i++)
			data[i] = r.nextLong();
	}
}