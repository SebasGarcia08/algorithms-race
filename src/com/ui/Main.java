package com.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private static double xOffset = 0;
	private static double yOffset = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmll = new FXMLLoader();
		fxmll.setLocation(getClass().getResource("/resources/Main.fxml"));

		StackPane root = fxmll.load();
		root.setOnMousePressed((event) -> { // Ends events to make window draggable
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		root.setOnMouseDragged((event) -> {
			primaryStage.setX(event.getScreenX() - xOffset);
			primaryStage.setY(event.getScreenY() - yOffset);
		}); // Ends events to make window draggable

		Scene scene = new Scene(root);

		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();

		scene.setFill(Color.TRANSPARENT);
		primaryStage.setTitle("Algorithms race");
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
