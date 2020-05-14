package com.controller;

import java.io.IOException;
import java.util.Random;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
	private ProgressIndicator BSTProgressIndicator;

	@FXML
	private ProgressIndicator DLLProgressIndicator;

	@FXML
	private ProgressIndicator ALProgressIndicator;

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
		BSTProgressIndicator.progressProperty().bind(raceThreads[0].progressProperty());
		
		DLLProgressBar.progressProperty().bind(raceThreads[1].progressProperty());
		DLLProgressIndicator.progressProperty().bind(raceThreads[1].progressProperty());
		

		ALProgressBar.progressProperty().bind(raceThreads[2].progressProperty());
		ALProgressIndicator.progressProperty().bind(raceThreads[2].progressProperty());		

		lbFinalResultBST.textProperty().bind(raceThreads[0].messageProperty());
		lbFinalResultDLL.textProperty().bind(raceThreads[1].messageProperty());
		lbFinalResultAL.textProperty().bind(raceThreads[2].messageProperty());

		outerBall = new Ball(outerCircle.getRadius(), 31, 15);
		innerBall = new Ball(innerCircle.getRadius(), 31, 15);
		this.expandingBall = true;
	}

	@FXML
	private void go(ActionEvent event) throws IOException, InterruptedException {
		try {
			prepareComponentsForRunning();			
		} catch (Exception e) {
			showDialog("Forgot to fill inputs!", "Please complete all fields before run algorithms", "Okay!");
			resetComponents();
		}
		String StringN = textFieldCollectionSize.getText();
		try {
			Algorithm algorithm = getAlgorithm();
			Mode mode = getMode();
			Long N = Long.parseLong(StringN);
			Random r = new Random();

			chronometerTimeLine.play();

			for (RaceThread race : raceThreads) {
				race.go(mode, algorithm, N, r.nextLong());
			}

			Thread t = new Thread() {
				public void run() {
					while (expandingBall) {
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
		} catch (NullPointerException e) {
			showDialog("Forgot to fill inputs!", "Please complete all fields before run algorithms", "Okay!");
			resetComponents();
		} catch (NumberFormatException e) {
			showDialog("Invalid format", StringN + " is not a valid number", "Okay!");
			resetComponents();
		}
	}

	public void prepareComponentsForRunning() {
		this.timeCounter = 0L;
		this.numOfThreadsFinished = 0;
		this.expandingBall = true;
		btnRun.setDisable(true);
		btnStop.setDisable(false);
		textFieldCollectionSize.setEditable(false);
		
		for(Toggle t : toggleGroupAlgorithm.getToggles())
			((JFXToggleButton) t).setDisable(true);
		
		for(Toggle t : toggleGroupMode.getToggles())
			((JFXToggleButton) t).setDisable(true);

	}

	public void resetComponents() {
		chronometerTimeLine.stop();
		expandingBall = false;
		outerCircle.setRadius(31);
		innerCircle.setRadius(15);
		btnRun.setDisable(false);
		btnStop.setDisable(true);
		textFieldCollectionSize.setEditable(true);
		
		for(Toggle t : toggleGroupAlgorithm.getToggles())
			((JFXToggleButton) t).setDisable(false);
		
		for(Toggle t : toggleGroupMode.getToggles())
			((JFXToggleButton) t).setDisable(false);
	}

	public void updateCircles() {
		outerCircle.setRadius(outerBall.getRadious());
		innerCircle.setRadius(innerBall.getRadious());
	}

	@FXML
	public void stop(ActionEvent e) {
		for (RaceThread race : raceThreads) {
			if (race.isRunning())
				race.cancel();
		}
	}

	public void showDialog(String headerMsg, String bodyMsg, String buttonMsg) {
		JFXDialogLayout content = new JFXDialogLayout();
		JFXDialog dialog = new JFXDialog(parentContainer, content, JFXDialog.DialogTransition.CENTER);
		content.setHeading(new Text(headerMsg));
		content.setBody(new Text(bodyMsg));
		JFXButton button = new JFXButton("Okay!");
		button.setOnAction((e) -> {
			dialog.close();
		});
		content.setActions(button);
		dialog.show();
	}

	public void notificate(String title, String text, FontAwesomeIcon iconEnum) {
		FontAwesomeIconView icon = new FontAwesomeIconView(iconEnum);
		icon.setSize("40");
		icon.setStyle("-fx-background-color: #000000;");
		Notifications notificationBuilder = Notifications.create().title(title).text(text).graphic(icon)
				.hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
		notificationBuilder.darkStyle();
		notificationBuilder.show();
	}

	public void resetControlsOnFinished() {
		if (numOfThreadsFinished == 3) {
			resetComponents();
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
}