package com.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.model.BinarySearchTree;
import com.model.DoublyLinkedList;
import com.model.MyArrayList;
import com.threads.Race;
import com.threads.Race.Algorithm;
import com.threads.Race.Mode;
import com.threads.RaceThread;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GUIController {

	@FXML
	private StackPane parentContainer;

	@FXML
	private AnchorPane configurationAnchor;

	@FXML
	private JFXButton btnRun;

	@FXML
	private JFXTextField textFieldCollectionSize;

	@FXML
	private FontAwesomeIconView faFlagLeft;

	@FXML
	private FontAwesomeIconView faFlagRight;

	@FXML
	private ToggleGroup toggleGroupMode;

	@FXML
	private ToggleGroup toggleGroupAlgorithm;

	@FXML
	private JFXButton btnExit;

	@FXML
	private Label labelHours;

	@FXML
	private Label labelMinutes;

	@FXML
	private Label labelSeconds;

	@FXML
	private Label labelMillis;

	@FXML
	private JFXProgressBar BSTProgressBar;

	@FXML
	private JFXProgressBar DLLProgressBar;

	@FXML
	private JFXProgressBar ALProgressBar;

	@FXML
	private Label lbFinalResultBST;

	@FXML
	private Label lbFinalResultDLL;

	@FXML
	private Label lbFinalResultAL;

	public long timeCounter = 0L;

	public int numOfThreadsFinished;

	private Timeline chronometerTimeLine;
	
	public void initialize() {
		chronometerTimeLine = new Timeline(new KeyFrame(Duration.millis(1), (e) -> {
			updateChronometer(timeCounter++);
		}));
		chronometerTimeLine.setCycleCount(Timeline.INDEFINITE);
		
		
		
	}

	@FXML
	private void go(ActionEvent event) throws IOException, InterruptedException {
		timeCounter = 0L;
		numOfThreadsFinished = 0;
		JFXProgressBar[] pbs = new JFXProgressBar[] { BSTProgressBar, DLLProgressBar, ALProgressBar };
		for (JFXProgressBar pb : pbs)
			pb.setProgress(0);

		Algorithm algorithm = getAlgorithm();
		Mode mode = getMode();
		Long N = Long.parseLong(textFieldCollectionSize.getText());

		Random r = new Random();
		RaceThread[] raceThreads = createThreads(mode, algorithm, N, r.nextLong());

		chronometerTimeLine.play();
		
		for(int i = 0; i < pbs.length; i++) {
			pbs[i].progressProperty().bind(raceThreads[i].progressProperty());
		}
		
		for(RaceThread t : raceThreads) {
			t.go();
		}
						
		System.out.println(numOfThreadsFinished);
	}
	
	public RaceThread[] createThreads(Mode mode, Algorithm algorithm, long n, long seed) {
		return new RaceThread[] {
				new RaceThread(this, new BinarySearchTree<Long>(), mode, algorithm, n, seed),
				new RaceThread(this, new DoublyLinkedList<Long>(), mode, algorithm, n, seed),
				new RaceThread(this, new MyArrayList<Long>(), mode, algorithm, n, seed) };
	}

	public void stopChronometer() {
		if(numOfThreadsFinished == 3)
			chronometerTimeLine.stop();
	}
	
	public void resetProgressBars() {
		JFXProgressBar[] pbs = new JFXProgressBar[] { BSTProgressBar, DLLProgressBar, ALProgressBar };
		for(int i = 0; i < pbs.length; i++) {
			pbs[i].setProgress(0);
			pbs[i].progressProperty().unbind();			
		}
	}

	public void updateProgressBar(JFXProgressBar pb, double progress) {
		pb.setProgress(progress);
	}

	public Mode getMode() {
		String selectedMode = ((JFXToggleButton) toggleGroupMode.getSelectedToggle()).getText();
		switch (selectedMode) {
		case "ITERATIVE":
			return Mode.ITERATIVE;
		case "RECURSIVE":
			return Mode.RECURSIVE;
		default:
			return null;
		}
	}

	public Algorithm getAlgorithm() {
		String selectedAlgorithm = ((JFXToggleButton) toggleGroupAlgorithm.getSelectedToggle()).getText();
		switch (selectedAlgorithm) {
		case "ADD":
			return Algorithm.ADD;
		case "SEARCH":
			return Algorithm.SEARCH;
		case "DELETE":
			return Algorithm.DELETE;
		default:
			return null;
		}
	}

	public void exit(ActionEvent e) {
		System.exit(0);
	}

	public void updateChronometer(long timeCounter) {
		long milliseconds = timeCounter % 1000;
		long seconds = (timeCounter / 1000) % 60;
		long minutes = (timeCounter / (1000 * 60)) % 60;
		long hours = (timeCounter / (1000 * 60 * 60)) % 60;
		String sMil = milliseconds < 10 ? ("00" + milliseconds)
				: milliseconds < 100 ? ("0" + milliseconds) : ("" + milliseconds);
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);
		String sHours = hours < 10 ? ("0" + hours) : ("" + hours);
		labelMillis.setText(sMil);
		labelSeconds.setText(sSec);
		labelMinutes.setText(sMin);
		labelHours.setText(sHours);
	}
	
	public void showFinalResults(Race race) {
		System.out.println("Finish");
		chronometerTimeLine.stop();
	}

	/**
	 * @return the bSTProgressBar
	 */
	public JFXProgressBar getBSTProgressBar() {
		return BSTProgressBar;
	}

	/**
	 * @return the dLLProgressBar
	 */
	public JFXProgressBar getDLLProgressBar() {
		return DLLProgressBar;
	}

	/**
	 * @return the aLProgressBar
	 */
	public JFXProgressBar getALProgressBar() {
		return ALProgressBar;
	}
}