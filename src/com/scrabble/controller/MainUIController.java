package com.scrabble.controller;

import com.scrabble.MainApp;
import com.scrabble.model.ImageButton;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

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


    private void SlideIn(Node s) {
        TranslateTransition tt = new TranslateTransition(new Duration(500), s);
        tt.setToX(0);
        tt.play();
    }

    private void SlideOut(Node s) {
        TranslateTransition tt = new TranslateTransition(new Duration(500), s);
        tt.setToX(-725);
        tt.play();
    }

    @FXML
    public void HandleMenuButton(ActionEvent actionEvent) throws IOException {
        SlideIn(menu);
        menu.toFront();
    }

    @FXML
    public void HandleNewGame(ActionEvent actionEvent) throws IOException {
        SlideOut(menu);
        menu.toFront();
    }


}
