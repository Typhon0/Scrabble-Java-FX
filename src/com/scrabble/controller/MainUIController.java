package com.scrabble.controller;

import com.scrabble.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainUIController {

    public MainUIController() {

    }

    public MainApp mainApp;

    @FXML
    private ImageView logo;

    @FXML
    private Pane logoContainer;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        logo.fitWidthProperty().bind(logoContainer.widthProperty());
    }


}
