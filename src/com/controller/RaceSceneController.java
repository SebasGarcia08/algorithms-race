package com.controller;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class RaceSceneController {

    @FXML
    private AnchorPane raceAnchor;

    @FXML
    private FontAwesomeIconView faFlagLeft;

    @FXML
    private Pane chronometerPane;

    @FXML
    private Label labelMinutes;

    @FXML
    private Label semicolon1;

    @FXML
    private Label labelSeconds;

    @FXML
    private Label semicolon2;

    @FXML
    private Label labelMillis;

    @FXML
    private FontAwesomeIconView faFlagRight;
        
    @FXML
    private JFXButton btnExit;
    
    @FXML
    void exit(ActionEvent event) {
    	System.exit(0);
    }
    
    public void initialize() {    
    	
    }  
}