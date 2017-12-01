package com.scrabble.controller;

import com.scrabble.MainApp;
import com.scrabble.control.ImageButton;
import com.scrabble.model.*;
import com.scrabble.util.Animations;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MainUIController {


    public MainUIController() {

    }

    public MainApp mainApp;
    public Button LetterWaiting = null;

    private GridPane boardGrid;

    @FXML
    Button pioche;
    @FXML
    private AnchorPane board;
    @FXML
    private AnchorPane menu;
    @FXML
    private ImageView logo;
    @FXML
    private StackPane logoContainer, boutonJoueur;
    @FXML
    private Button MenuBtn;
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
    @FXML
    Text scoreJ1;
    @FXML
    Text scoreJ2;
    @FXML
    Text scoreJ3;
    @FXML
    Text scoreJ4;
    @FXML
    AnchorPane J1Box;
    @FXML
    AnchorPane J2Box;
    @FXML
    AnchorPane J3Box;
    @FXML
    AnchorPane J4Box;

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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boardGrid = (GridPane) board.getChildren().get(0);
                ObservableList<Node> list = boardGrid.getChildren();
                for (Node n : list) {
                    n.setOnMouseClicked(onBoardClicked());
                }

                showHand();
                initScoreBoard();
                pioche.textProperty().bind(mainApp.getScrabble().getPioche().nbPieceProperty().asString());
                mainApp.getScrabble().currentPlayerPropertyProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        System.out.println("OLD " + oldValue);
                        System.out.println("NEW " + newValue);

                        highlightCurrentPlayerScore(newValue.intValue());
                    }
                });

            }
        });

    }

    public void initScoreBoard() {
        if (mainApp.getScrabble().getJoueurs().size() == 2) {
            J1Box.setVisible(true);
            J2Box.setVisible(true);

            scoreJ1.textProperty().bind(mainApp.getScrabble().getJoueur(0).nbPointsProperty().asString());
            scoreJ2.textProperty().bind(mainApp.getScrabble().getJoueur(1).nbPointsProperty().asString());
        } else {
            J1Box.setVisible(true);
            J2Box.setVisible(true);
            J3Box.setVisible(true);
            J4Box.setVisible(true);

            scoreJ1.textProperty().bind(mainApp.getScrabble().getJoueur(0).nbPointsProperty().asString());
            scoreJ2.textProperty().bind(mainApp.getScrabble().getJoueur(1).nbPointsProperty().asString());
            scoreJ3.textProperty().bind(mainApp.getScrabble().getJoueur(2).nbPointsProperty().asString());
            scoreJ4.textProperty().bind(mainApp.getScrabble().getJoueur(3).nbPointsProperty().asString());
        }
        J1Box.getStyleClass().add("scoreBoardHighlight");

    }

    public void highlightCurrentPlayerScore(int currentPlayer) {
        switch (currentPlayer) {
            case 0:
                J1Box.getStyleClass().add("scoreBoardHighlight");
                J2Box.getStyleClass().remove("scoreBoardHighlight");
                J3Box.getStyleClass().remove("scoreBoardHighlight");
                J4Box.getStyleClass().remove("scoreBoardHighlight");
                break;
            case 1:
                J1Box.getStyleClass().remove("scoreBoardHighlight");
                J2Box.getStyleClass().add("scoreBoardHighlight");
                J3Box.getStyleClass().remove("scoreBoardHighlight");
                J4Box.getStyleClass().remove("scoreBoardHighlight");

                break;
            case 2:
                J1Box.getStyleClass().remove("scoreBoardHighlight");
                J2Box.getStyleClass().remove("scoreBoardHighlight");
                J3Box.getStyleClass().add("scoreBoardHighlight");
                J4Box.getStyleClass().remove("scoreBoardHighlight");
                break;
            case 3:
                J1Box.getStyleClass().remove("scoreBoardHighlight");
                J2Box.getStyleClass().remove("scoreBoardHighlight");
                J3Box.getStyleClass().remove("scoreBoardHighlight");
                J4Box.getStyleClass().add("scoreBoardHighlight");
                break;
        }
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
        mainApp.getScrabble().getPioche().takeLetterInBag(1);
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
     * Handle shuffle button
     *
     * @param actionEvent
     */
    @FXML
    public void HandleShuffle(ActionEvent actionEvent) {
        mainApp.getScrabble().getJoueur(0).melanger();
        showHand();
    }

    /**
     * Handle Swap or Recall button
     *
     * @param actionEvent
     */
    @FXML
    public void HandleSwapRecall(ActionEvent actionEvent) {
    }

    /**
     * Handle Jouer or Passer tour button
     *
     * @param actionEvent
     */
    @FXML
    public void HandleSJouerPasserTour(ActionEvent actionEvent) {
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

        mainApp.getScrabble().getJoueur(0).addNbPoints(5);
    }

    //TODO
    public static void swapHand(int i, int j) {

    }

    public Button generateButtonFromLetter(char ch) {
        //TODO
        Button btn = new Button();
        btn.getStyleClass().add("buttonLetter");
        if (ch == '?') {
            btn.setStyle("-fx-background-image: url('/com/scrabble/ressources/Piece/letter.png')");
        } else {
            btn.setStyle("-fx-background-image: url('/com/scrabble/ressources/Piece/letter_" + ch + ".png')");
        }
        btn.setMinHeight(40);
        btn.setMinWidth(40);
        return btn;
    }

    public void showHand() {
        ArrayList<Piece> main = mainApp.getScrabble().getJoueur(0).getMain();
        ArrayList<Button> listePiece = new ArrayList<Button>();
        mainJoueur.getChildren().clear();
        for (Piece p : main) {
            Button btn = generateButtonFromLetter(p.getLettre());
            listePiece.add(btn);
        }
        int i = 0;
        for (Button b : listePiece) {
            b.setId("B" + i);
            b.setOnMouseClicked(onMainClicked());
            mainJoueur.getChildren().add(b);
            i++;
        }
    }

    public EventHandler<javafx.scene.input.MouseEvent> onMainClicked() {
        return new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Button b = (Button) event.getSource();
                LetterWaiting = b;
                //System.out.println(b);
            }
        };
    }

    public EventHandler<javafx.scene.input.MouseEvent> onBoardClicked() {
        return new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (event.getSource() instanceof StackPane && LetterWaiting != null) {
                    StackPane sp = (StackPane) event.getSource();
                    sp.getChildren().clear();
                    LetterWaiting.setOnMouseClicked(null);
                    LetterWaiting.setMinSize(sp.getWidth(), sp.getHeight());
                    LetterWaiting.setMaxSize(sp.getWidth(), sp.getHeight());
                    LetterWaiting.getStyleClass().removeAll("buttonLetter");
                    LetterWaiting.getStyleClass().add("resizingPiece");
                    sp.getChildren().add(LetterWaiting);
                    StackPane.setAlignment(LetterWaiting, Pos.CENTER);

                    //model gestion
                    int numeroLettreDansMain = Integer.parseInt(LetterWaiting.getId().replace("B", ""));
                    int numeroDeCase = Integer.parseInt(sp.getId().replace("S", ""));
                    //put in board
                    mainApp.getScrabble().getBoard()[numeroDeCase % 15][numeroDeCase / 15].setPiece(mainApp.getScrabble().getJoueur(0).getMain().get(numeroLettreDansMain));
                    //remove in hand
                    mainApp.getScrabble().getJoueur(0).getMain().remove(numeroLettreDansMain);

                    LetterWaiting = null;
                    showHand();
                }
            }
        };
    }

}
