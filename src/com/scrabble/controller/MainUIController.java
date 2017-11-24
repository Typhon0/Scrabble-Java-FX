package com.scrabble.controller;

import com.scrabble.MainApp;
import com.scrabble.control.ImageButton;
import com.scrabble.model.Piece;
import com.scrabble.util.Animations;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MainUIController {


    public MainUIController() {

    }

    public MainApp mainApp;

    @FXML
    private AnchorPane board;
    @FXML
    private AnchorPane menu;
    @FXML
    private ImageView logo;
    @FXML
    private StackPane logoContainer, boutonJoueur;
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
    @FXML
    private HBox mainJoueur;

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
        board.widthProperty().addListener((InvalidationListener) observable -> {
            board.setMinHeight(board.widthProperty().doubleValue());
            board.setMaxHeight(board.widthProperty().doubleValue());


        });

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

    @FXML
    public void HandlePiocheButton(ActionEvent actionEvent) {
        showHand();
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

    //TODO
    public static void swapHand(int i,int j){

    }

    public Button generateButtonFromLetter(char ch, double size){
        //TODO
        Button btn = new Button();
        btn.getStyleClass().add("buttonLetter");
        if(ch == '?'){
            //btn.setText("?");
            btn.setStyle("-fx-background-image: url('/com/scrabble/ressources/Piece/letter.png')");
            //btn.setStyle("-fx-background-size: 20px");
        }else{
            //btn.setText(String.valueOf(ch));
            btn.setStyle("-fx-background-image: url('/com/scrabble/ressources/Piece/letter_" + ch + ".png')");
            //btn.setStyle("-fx-background-size: 20px");
            btn.setMinHeight(40);
            btn.setMinWidth(40);
        }
        return btn;
    }

    public void showHand() {
        ArrayList<Piece> main = mainApp.getScrabble().getJoueur(0).getMain();

        ArrayList<Button> listePiece = new ArrayList<Button>();
       // mainJoueur.getChildren().clear();
        double size = board.getWidth();
        size/=10;
        for (Piece p : main) {
            Button btn = generateButtonFromLetter(p.getLettre(),size);
            listePiece.add(btn);
        }
        for (Button b : listePiece) {
            mainJoueur.getChildren().add(b);
        }

    }

}
