package com.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.threads.Race;
import com.threads.Race.Algorithm;
import com.threads.Race.Mode;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
	private ToggleGroup toggleGroupMode;

	@FXML
	private ToggleGroup toggleGroupAlgorithm;

	@FXML
	private JFXButton btnExit;

	/* RACE SCENE */
	@FXML
	private AnchorPane raceAnchor;

	@FXML
	private Pane chronometerPane;

	@FXML
	private Label labelHours;

	@FXML
	private Label labelMinutes;

	@FXML
	private Label labelSeconds;

	@FXML
	private Label labelMillis;

	@FXML
	private JFXButton btnExit1;

	@FXML
	private JFXProgressBar BSTProgressBar;

	@FXML
	private JFXProgressBar DLLProgressBar;

	@FXML
	private JFXProgressBar ALProgressBar;

	private long timeCounter = 0;

	private Timeline chronometerTimeLine;

	public void initialize() {
		chronometerTimeLine = new Timeline(new KeyFrame(Duration.millis(1), (e) -> {
			updateChronometer(timeCounter++);
		}));

		chronometerTimeLine.setCycleCount(Timeline.INDEFINITE);
	}

	@FXML
	private void loadRaceScene(ActionEvent event) throws IOException {
		switch2RaceScene();
		chronometerTimeLine.play();

		Algorithm algorithm = getAlgorithm();
		Mode mode = getMode();
		Long N = Long.parseLong(textFieldCollectionSize.getText());

		Race race = new Race(this, mode, algorithm, N);
		race.setDaemon(true);
		race.start();
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

	public void switch2RaceScene() throws IOException {
		FXMLLoader fxmll = new FXMLLoader();
		fxmll.setLocation(getClass().getResource("/resources/Race.fxml"));
		fxmll.setController(this);
		Parent root = fxmll.load();
		Scene scene = btnRun.getScene();

		root.translateXProperty().set(scene.getHeight());

		parentContainer.getChildren().add(root);

		Timeline timeline = new Timeline();
		KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kf = new KeyFrame(Duration.seconds(0.1), kv);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(t -> {
			parentContainer.getChildren().remove(configurationAnchor);
		});
		timeline.play();
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
		System.out.println(race.getDurationMillis());
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