package com.scrabble.controller;

import com.scrabble.MainApp;
import com.scrabble.util.Animations;
import com.scrabble.control.ImageButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainUIController {


    public MainUIController() {

    }

    @FXML
    AnchorPane menu;
    public MainApp mainApp;

    @FXML
    private ImageView logo;

    @FXML
    private Pane logoContainer;

    @FXML
    private ImageButton MenuBtn;


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
    private void initialize() throws IOException {
       /*  Pane menuContent;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/Menu.fxml"));
        menuContent = (Pane) loader.load();
        menu.getChildren().add(menuContent);*/
        menu.toFront();

    }


    @FXML
    public void HandleMenuButton(ActionEvent actionEvent) throws IOException {
        Animations.SlideInFromLeft(menu, 500, mainApp.getPrimaryStage().getWidth());
        menu.toFront();
    }

    @FXML
    public void HandleNewGame(ActionEvent actionEvent) throws IOException {
        Animations.SlideOutToLeft(menu, 500, mainApp.getPrimaryStage().getWidth());
        menu.toFront();
    }

    @FXML
    public void HandleLoadGame(ActionEvent actionEvent) {
    }

    @FXML
    public void HandleQuitGame(ActionEvent actionEvent) {

        mainApp.getPrimaryStage().close();
    }
}
