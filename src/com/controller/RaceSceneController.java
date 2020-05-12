package com.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class RaceSceneController implements Initializable {

	@FXML
	private AnchorPane raceAnchor;

	@FXML
	private FontAwesomeIconView faFlagLeft;

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
	private FontAwesomeIconView faFlagRight;

	@FXML
	private JFXButton btnExit;

	private long timeCounter = 0;

	private Timeline chronometerTimeLine;
	
	public RaceSceneController() {
		chronometerTimeLine = new Timeline(new KeyFrame(Duration.millis(1), (event) -> {
			updateLabels(timeCounter++);
		}));

		chronometerTimeLine.setCycleCount(Timeline.INDEFINITE);
	}
	
	@FXML
	void exit(ActionEvent event) {
		System.exit(0);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			chronometerTimeLine.play();
		});
	}
	
	public void updateLabels(long timeCounter) {
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
	
}