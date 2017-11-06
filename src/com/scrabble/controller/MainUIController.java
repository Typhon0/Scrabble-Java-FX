package com.scrabble.controller;

import com.scrabble.MainApp;
import com.scrabble.control.ImageButton;
import com.scrabble.util.Animations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class MainUIController {


    public MainUIController() {

    }

    public MainApp mainApp;


    @FXML
    private AnchorPane menu;
    @FXML
    private ImageView logo;
    @FXML
    private StackPane logoContainer, mainJoueur, boutonJoueur;
    @FXML
    private ImageButton MenuBtn;
    @FXML
    private StackPane dialog;
    @FXML
    private AnchorPane dialogContent;
    @FXML
    private Button buttonOkPopup;
    @FXML
    private Text title;
    @FXML
    private Text message;
    @FXML
    private Button buttonOuiPopup;
    @FXML
    private Button buttonNonPopup;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

    /**
     * This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        menu.toFront();

    }

    /**
     * Action event handler for the go to menu button
     *
     * @param actionEvent
     */
    @FXML
    public void HandleMenuButton(ActionEvent actionEvent) {
        Animations.SlideInFromLeft(menu, 500, mainApp.getPrimaryStage().getWidth(), 0);
        menu.toFront();
    }

    /**
     * Action event handler for the New Game button on the menu
     *
     * @param actionEvent
     */
    @FXML
    public void HandleNewGame(ActionEvent actionEvent) {
        Animations.SlideOutToLeft(menu, 500, mainApp.getPrimaryStage().getWidth());
        menu.toFront();
    }

    /**
     * Action event handler for the Load Game button on the menu
     *
     * @param actionEvent
     */
    @FXML
    public void HandleLoadGame(ActionEvent actionEvent) {

    }

    /**
     * Action event handler for the Exit button on the menu
     *
     * @param actionEvent
     */
    @FXML
    public void HandleQuitGame(ActionEvent actionEvent) {

        mainApp.getPrimaryStage().close();
    }

    @FXML
    public void HandlePopupButton(ActionEvent actionEvent) {
        showConfirmationDialog("Title", "test de message");
    }

    /**
     * Action event handler for the OK button of the dialog
     *
     * @param actionEvent
     */
    @FXML
    public void handlebuttonOkPopup(ActionEvent actionEvent) {

        Animations.BounceOutTransition(dialogContent, true);

    }

    /**
     * Action event handler for the No button of the dialog
     *
     * @param actionEvent
     */
    @FXML
    public void handlebuttonNonPopup(ActionEvent actionEvent) {

        Animations.BounceOutTransition(dialogContent, true);


    }

    /**
     * Action event handler for the yes button of the dialog
     *
     * @param actionEvent
     */
    @FXML
    public void handlebuttonOuiPopup(ActionEvent actionEvent) {
        Animations.BounceOutTransition(dialogContent, true);


    }

    /**
     * Show information dialog with a button to click OK
     *
     * @param title
     * @param message
     */
    public void showInfoDialog(String title, String message) {
        buttonOkPopup.setVisible(true);
        buttonOuiPopup.setVisible(false);
        buttonNonPopup.setVisible(false);
        this.title.setText(title);
        this.message.setText(message);
        dialog.toFront();
        dialog.setVisible(true);
        dialogContent.setVisible(true);
        Animations.BounceInTransition(dialogContent);

    }

    /**
     * Show a confirmation dialog with  Yes and No buttons
     *
     * @param title
     * @param message
     */
    public void showConfirmationDialog(String title, String message) {
        //  ImageButton b = (ImageButton) actionEvent.getSource();
        buttonOkPopup.setVisible(false);
        buttonOuiPopup.setVisible(true);
        buttonNonPopup.setVisible(true);
        this.title.setText(title);
        this.message.setText(message);
        dialog.toFront();
        dialog.setVisible(true);
        dialogContent.setVisible(true);

        Animations.BounceInTransition(dialogContent);

    }


}
