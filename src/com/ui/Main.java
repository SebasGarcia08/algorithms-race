package com.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmll = new FXMLLoader();
		fxmll.setLocation(getClass().getResource("/resources/Main.fxml"));
		
		StackPane root = fxmll.load();
		Scene scene = new Scene(root);
		
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}	
}
