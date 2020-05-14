package com.controller;

import java.io.IOException;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.model.Algorithm;
import com.model.BinarySearchTree;
import com.model.DoublyLinkedList;
import com.model.Mode;
import com.model.MyArrayList;
import com.threads.RaceThread;
import com.ui.Ball;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GUIController {

	@FXML
	private StackPane parentContainer;

	@FXML
	private AnchorPane configurationAnchor;

	@FXML
	private JFXButton btnRun;
	
	@FXML
	private JFXButton btnStop;

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

	private RaceThread[] raceThreads;
	
    @FXML
    private Circle outerCircle;
    
    private Ball outerBall;

    @FXML
    private Circle innerCircle;

    private Ball innerBall;
    
    private boolean expandingBall;

	public void initialize() {
		chronometerTimeLine = new Timeline(new KeyFrame(Duration.millis(1), (e) -> {
			updateChronometer(timeCounter++);
		}));
		chronometerTimeLine.setCycleCount(Timeline.INDEFINITE);

		this.raceThreads = new RaceThread[] { new RaceThread(this, new BinarySearchTree<Long>()),
				new RaceThread(this, new DoublyLinkedList<Long>()), new RaceThread(this, new MyArrayList<Long>()) };
		
		BSTProgressBar.progressProperty().bind(raceThreads[0].progressProperty());		
		DLLProgressBar.progressProperty().bind(raceThreads[1].progressProperty());
		ALProgressBar.progressProperty().bind(raceThreads[2].progressProperty());
		
		lbFinalResultBST.textProperty().bind(raceThreads[0].messageProperty());
		lbFinalResultDLL.textProperty().bind(raceThreads[1].messageProperty());
		lbFinalResultAL.textProperty().bind(raceThreads[2].messageProperty());
		
		outerBall = new Ball(outerCircle.getRadius(), 31, 15);
		innerBall = new Ball(innerCircle.getRadius(), 31, 15);
		this.expandingBall = true;
	}

	@FXML
	private void go(ActionEvent event) throws IOException, InterruptedException {
		this.timeCounter = 0L;
		this.numOfThreadsFinished = 0;
		this.expandingBall = true;
		btnRun.setDisable(true);
		btnStop.setDisable(false);
		
		Algorithm algorithm = getAlgorithm();
		Mode mode = getMode();
		Long N = Long.parseLong(textFieldCollectionSize.getText());
		
		Random r = new Random();
		
		chronometerTimeLine.play();
		
		for (RaceThread race : raceThreads) {
			race.go(mode, algorithm, N, r.nextLong());
		}	
		
		Thread t = new Thread() {
			public void run() {
				while(expandingBall) {
					innerBall.move();
					outerBall.move();
					
					Platform.runLater(() -> updateCircles());
					
    				try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.setDaemon(true);
		t.start();
	}
	
	public void updateCircles() {
		outerCircle.setRadius(outerBall.getRadious());
		innerCircle.setRadius(innerBall.getRadious());
	}
	
	@FXML
	public void stop(ActionEvent e) {
		for (RaceThread race : raceThreads) {
			if(race.isRunning()) 
				race.cancel();
		}	
	}
	
	public void stopChronometer() {
		if (numOfThreadsFinished == 3) {
			chronometerTimeLine.stop();
			expandingBall = false;
			outerCircle.setRadius(31);
			innerCircle.setRadius(15);
			btnRun.setDisable(false);
			btnStop.setDisable(true);
		}
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
	
	public void moveCircles() {
		
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
	
	/**
	 * @return the lbFinalResultBST
	 */
	public Label getLbFinalResultBST() {
		return lbFinalResultBST;
	}

	/**
	 * @return the lbFinalResultDLL
	 */
	public Label getLbFinalResultDLL() {
		return lbFinalResultDLL;
	}

	/**
	 * @return the lbFinalResultAL
	 */
	public Label getLbFinalResultAL() {
		return lbFinalResultAL;
	}

	/**
	 * @param lbFinalResultBST the lbFinalResultBST to set
	 */
	public void setLbFinalResultBST(Label lbFinalResultBST) {
		this.lbFinalResultBST = lbFinalResultBST;
	}

	/**
	 * @param lbFinalResultDLL the lbFinalResultDLL to set
	 */
	public void setLbFinalResultDLL(Label lbFinalResultDLL) {
		this.lbFinalResultDLL = lbFinalResultDLL;
	}

	/**
	 * @param lbFinalResultAL the lbFinalResultAL to set
	 */
	public void setLbFinalResultAL(Label lbFinalResultAL) {
		this.lbFinalResultAL = lbFinalResultAL;
	}
	
	
}