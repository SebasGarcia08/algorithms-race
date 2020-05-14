package com.threads;

import java.util.Random;
import java.util.function.Consumer;

import com.controller.GUIController;
import com.model.Algorithm;
import com.model.BinarySearchTree;
import com.model.CompetitiveDataStructure;
import com.model.DoublyLinkedList;
import com.model.Mode;
import com.model.MyArrayList;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
	private String dataStructureName = "";

	public RaceThread(GUIController controller, CompetitiveDataStructure<Long> ds) {
		this.dataStructure = ds;
		this.controller = controller;

		boolean thisIsBST = amIaBinarySearchTree();
		boolean thisIsDLL = AmIaDoublyLinkedList();
		boolean thisIsAL = amIanArrayList();
		
		if (thisIsBST) {
			dataStructureName = "Binary Search Tree";
		} else if (thisIsDLL) {
			dataStructureName = "Doubly Linked List";
		} else {
			dataStructureName = "ArrayList";
		}
		
		// if succeeded
		setOnSucceeded(s -> {
			controller.numOfThreadsFinished++;
			controller.resetControlsOnFinished();
			dataStructure.reset();
			if (controller.numOfThreadsFinished == 1) {
				String title = "We have a winner!";
				String body =  dataStructureName + " was the fastest! only took " + millis2TimeString(durationMillis);
				controller.notificate(title, body, FontAwesomeIcon.TROPHY);
			}
		});

		// if failed
		setOnFailed(fail -> {
			controller.numOfThreadsFinished++;
			dataStructure.reset();
			controller.resetControlsOnFinished();
			controller.notificate("Error!", "Something wrong happened :c", FontAwesomeIcon.WARNING);
		});

		// if cancelled
		setOnCancelled(cancelled -> {
			controller.numOfThreadsFinished++;
			dataStructure.reset();
			controller.resetComponents();
		});

		setOnRunning((e) -> {
			
		});
	}

	public void go(Mode mode, Algorithm algorithm, long n, long seed) {
		if (!isRunning()) {
			this.random = new Random(seed);
			random.setSeed(seed);
			this.numOfOperationsPending = n;
			this.numOfOperationsDone = 0;
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
			dataStructure.reset();
			reset();
			start();
		}
	}

	public boolean amIaBinarySearchTree() {
		return dataStructure instanceof BinarySearchTree<?>;
	}
	
	public boolean amIanArrayList() {
		return dataStructure instanceof MyArrayList<?>;
	}

	public boolean AmIaDoublyLinkedList() {
		return dataStructure instanceof DoublyLinkedList<?>;
	}

	public long getDurationMillis() {
		return this.durationMillis;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override
			public Void call() {
				this.updateProgress(0, numOfOperationsPending);
				try {
					if (algorithm != Algorithm.ADD && !isCancelled()) {
						this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
						this.updateMessage("Adding elements...");
						Random localRandom = new Random();
						for (long i = 0; i <= numOfOperationsPending; i++) {
							if(isCancelled())
								break;
							dataStructure.addIteratively(localRandom.nextLong());
						}
					} if (isCancelled()) {
						this.updateProgress(0, numOfOperationsPending);
					}
				} catch (StackOverflowError e) {
					updateMessage("Stackoverflow");
					controller.notificate("StarckOverFlow!", dataStructureName + " is out of competence", FontAwesomeIcon.EXCLAMATION_CIRCLE);
					this.updateProgress(0, numOfOperationsPending);
					cancel();
				} if (!isCancelled()) {
					try {
						this.updateMessage("Running...");
						long start = System.currentTimeMillis();
						while (numOfOperationsDone <= numOfOperationsPending) {
							if (isCancelled()) {
								this.updateProgress(0, numOfOperationsPending);
								break;
							}
							methodSelected.accept(random.nextLong());
							updateProgress(numOfOperationsDone, numOfOperationsPending);
							numOfOperationsDone++;
						}
						durationMillis = System.currentTimeMillis() - start;
						String durationString = millis2TimeString(durationMillis);
						updateMessage(durationString);
						updateTitle("title");
					} catch (StackOverflowError e) {
						this.updateMessage("Stackoverflow");
						controller.notificate("StarckOverFlow!", dataStructureName + " is out of competence", FontAwesomeIcon.EXCLAMATION_CIRCLE);
						this.updateProgress(0, numOfOperationsPending);
						cancel();
					}
				}
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
