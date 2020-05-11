package com.controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ConfigurationSceneController {

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
    private void loadRaceScene(ActionEvent event) throws IOException {
		FXMLLoader fxmll = new FXMLLoader();
		fxmll.setLocation(getClass().getResource("/resources/Race.fxml"));
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
    
	public void initialize() {    
    }   
}