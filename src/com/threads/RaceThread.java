package com.threads;

import java.util.Random;
import java.util.function.Consumer;
import com.controller.GUIController;
import com.model.CompetitiveDataStructure;
import com.threads.Race.Algorithm;
import com.threads.Race.Mode;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

public class RaceThread extends Service<Void> {
	private CompetitiveDataStructure<Long> dataStructure;
	private Random random;
	private double numOfOperationsDone;
	private double numOfOperationsPending;
	private Consumer<Long> methodSelected;
	private GUIController controller;
	private long durationMillis;
	private Algorithm algorithm;

	public RaceThread(GUIController controller, CompetitiveDataStructure<Long> ds, Mode mode,
			Algorithm algorithm, long n, long seed) {
		this.dataStructure = ds;
		this.random = new Random(seed);
		random.setSeed(seed);
		this.numOfOperationsPending = n;
		this.numOfOperationsDone = 0;
		this.controller = controller;
		this.algorithm = algorithm;
		
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
		
	    // if succeeded
        setOnSucceeded(s -> {
           controller.numOfThreadsFinished++;
           controller.stopChronometer();
          
        });
        
        // if failed
        setOnFailed(fail -> {
        	System.out.println("Failed");
        });

        //if cancelled
        setOnCancelled(cancelled->{
        	System.out.println("cancelled");
        });
	}
	
	public void go() {
		if(!isRunning()) {
			reset();
			start();
		}
	}
	
	public long getDurationMillis() {
		return this.durationMillis;
	}
	
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			public Void call() {
				if (algorithm != Algorithm.ADD) {
					this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
					this.updateMessage("Adding elements...");
					Random localRandom = new Random();
					long i = 0;
					while (i < numOfOperationsPending) {
						if(isCancelled())
							break;
						dataStructure.addIteratively(localRandom.nextLong());
						i++;
					}
				}
				
				this.updateMessage("Running...");
				long start = System.currentTimeMillis();
				while (numOfOperationsDone <= numOfOperationsPending) {
					if(isCancelled())
						break;
					methodSelected.accept(random.nextLong());
					updateProgress(numOfOperationsDone, numOfOperationsPending);
					numOfOperationsDone++;
				}
				
				durationMillis = System.currentTimeMillis() - start;
				String durationString = millis2TimeString(durationMillis);
				updateMessage(durationString);
				return null;
			}
		};
	}
		
	public String millis2TimeString(long time) {
		long milliseconds = time % 1000;
		long seconds = (time / 1000) % 60;
		long minutes = (time / (1000 * 60)) % 60;
		long hours = (time / (1000 * 60 * 60)) % 60;
		String sMil = milliseconds < 10 ? ("00" + milliseconds)
				: milliseconds < 100 ? ("0" + milliseconds) : ("" + milliseconds);
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);
		String sHours = hours < 10 ? ("0" + hours) : ("" + hours);
		return sHours + ":" + sMin + ":" + sSec + ":" + sMil;
	}
}
