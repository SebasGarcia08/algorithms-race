package com.threads;

import java.util.Random;
import java.util.function.Consumer;

import com.model.CompetitiveDataStructure;
import com.threads.Race.Algorithm;
import com.threads.Race.Mode;

public class RaceThread extends Thread {
	private CompetitiveDataStructure<Long> dataStructure;
	private Random random;
	private double numOfOperationsDone;
	private double numOfOperationsPending;
	private Consumer<Long> methodSelected;

	private long durationMillis;

	public RaceThread(CompetitiveDataStructure<Long> ds, Mode mode, Algorithm algorithm, long n, long seed) {

		this.dataStructure = ds;
		this.random = new Random(seed);
		this.numOfOperationsPending = n;
		this.numOfOperationsDone = 0;

		switch (algorithm) {
		case ADD:
			if (mode == Mode.ITERATIVE)
				methodSelected = dataStructure::addIteratively;
			else
				methodSelected = dataStructure::addRecursively;
			break;
		case DELETE:
			if (mode == Mode.ITERATIVE)
				methodSelected = dataStructure::deleteIteratively;
			else
				methodSelected = dataStructure::deleteRecursively;
			break;
		case SEARCH:
			if (algorithm == Algorithm.SEARCH)
				methodSelected = dataStructure::searchIteratively;
			else
				methodSelected = dataStructure::searchRecursively;
			break;
		default:
			break;
		}
		random.setSeed(seed);
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		while (numOfOperationsDone <= numOfOperationsPending) {
			methodSelected.accept(random.nextLong());
			double progress = (double) (numOfOperationsDone / numOfOperationsPending);
			System.out.println(progress);
			numOfOperationsDone++;
		}
		this.durationMillis = System.currentTimeMillis() - start;
	}

	public long getDurationMillis() {
		return this.durationMillis;
	}
}
