package com.threads;

import java.util.Hashtable;
import java.util.Random;

import com.controller.GUIController;
import com.model.BinarySearchTree;
import com.model.DoublyLinkedList;
import com.model.MyArrayList;

import javafx.application.Platform;

public class Race extends Thread {

	public enum Mode {
		ITERATIVE, RECURSIVE
	}

	public enum Algorithm {
		ADD, SEARCH, DELETE
	}

	private Hashtable<String, Long> durationMillis;
	private Hashtable<String, RaceThread> threads;
	private GUIController guiController;

	public Race(GUIController guiController, Mode mode, Algorithm algorithm, long n) {
//		durationMillis = new Hashtable<String, Long>();
//		threads = new Hashtable<String, RaceThread>();
//
//		durationMillis.put("BST", 0L);
//		durationMillis.put("DLL", 0L);
//		durationMillis.put("AL", 0L);
//
//		this.guiController = guiController;
//		long seed = (new Random()).nextInt();
//
//		RaceThread bstThread = new RaceThread(new BinarySearchTree<Long>(), mode, algorithm, n, seed);
//		RaceThread dllThread = new RaceThread(new DoublyLinkedList<Long>(), mode, algorithm, n, seed);
//		RaceThread alThread = new RaceThread(new MyArrayList<Long>(), mode, algorithm, n, seed);
//
//		threads.put("BST", bstThread);
//		threads.put("DLL", dllThread);
//		threads.put("AL", alThread);
	}

//	@Override
//	public void run() {
//		for (RaceThread t : threads.values()) {
//			t.setDaemon(true);
//			t.start();
//		}
//
//		for (String key : new String[] { "BST", "DLL", "AL" })
//			durationMillis.put(key, threads.get(key).getDurationMillis());
//
//		guiController.showFinalResults(this);
//	}
//
//	public Hashtable<String, Long> getDurationMillis() {
//		return durationMillis;
//	}
}