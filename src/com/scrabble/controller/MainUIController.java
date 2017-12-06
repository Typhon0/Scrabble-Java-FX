package com.scrabble.controller;

import com.scrabble.MainApp;
import com.scrabble.control.ImageButton;
import com.scrabble.model.*;
import com.scrabble.util.Animations;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainUIController {

    //region Constructor
    public MainUIController() {

    }
    //endregion

    //region Variable
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
    private AnchorPane baseAnchor;
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
    private Button buttonNonPopup, swapRecallBtn;
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
    Text pseudoTextJ1;
    @FXML
    Text pseudoTextJ2;
    @FXML
    Text pseudoTextJ3;
    @FXML
    Text pseudoTextJ4;
    @FXML
    AnchorPane J1Box;
    @FXML
    AnchorPane J2Box;
    @FXML
    AnchorPane J3Box;
    @FXML
    AnchorPane J4Box;


    private ArrayList<Button> lettrePlaceesCetteManche; //Graphique
    private ArrayList<Piece> piecePlaceesCetteManche;   //model
    //endregion±

    //region Function

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
        swapRecallBtn.getStyleClass().add("swapImg");
        lettrePlaceesCetteManche = new ArrayList<>();
        piecePlaceesCetteManche = new ArrayList<>();

    }

    /**
     * Initialise le visuel du jeu
     */
    public void initGame() {
        drawboard(baseAnchor);
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
                bindProperty();

            }
        });
    }

    /**
     * Bind les textes de l'UI avec le model
     */
    public void bindProperty() {
        pioche.textProperty().bind(mainApp.getScrabble().getPioche().nbPiecePropertyProperty().asString());
        mainApp.getScrabble().currentPlayerPropertyProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                highlightCurrentPlayerScore(newValue.intValue());
            }
        });

        //initScoreBoard();

    }

    /**
     * Initialise et bind la scoreboard avec le modele
     */
    public void initScoreBoard() {
        if (mainApp.getScrabble().getJoueurs().size() == 2) {
            J1Box.setVisible(true);
            J2Box.setVisible(true);

            scoreJ1.textProperty().bind(mainApp.getScrabble().getJoueur(0).nbPointsProperty().asString());
            scoreJ2.textProperty().bind(mainApp.getScrabble().getJoueur(1).nbPointsProperty().asString());
            pseudoTextJ1.textProperty().bind(mainApp.getScrabble().getJoueur(0).pseudoPropertyProperty());
            pseudoTextJ2.textProperty().bind(mainApp.getScrabble().getJoueur(1).pseudoPropertyProperty());


        } else {
            J1Box.setVisible(true);
            J2Box.setVisible(true);
            J3Box.setVisible(true);
            J4Box.setVisible(true);

            scoreJ1.textProperty().bind(mainApp.getScrabble().getJoueur(0).nbPointsProperty().asString());
            scoreJ2.textProperty().bind(mainApp.getScrabble().getJoueur(1).nbPointsProperty().asString());
            scoreJ3.textProperty().bind(mainApp.getScrabble().getJoueur(2).nbPointsProperty().asString());
            scoreJ4.textProperty().bind(mainApp.getScrabble().getJoueur(3).nbPointsProperty().asString());
            pseudoTextJ1.textProperty().bind(mainApp.getScrabble().getJoueur(0).pseudoPropertyProperty());
            pseudoTextJ2.textProperty().bind(mainApp.getScrabble().getJoueur(1).pseudoPropertyProperty());
            pseudoTextJ3.textProperty().bind(mainApp.getScrabble().getJoueur(2).pseudoPropertyProperty());
            pseudoTextJ4.textProperty().bind(mainApp.getScrabble().getJoueur(3).pseudoPropertyProperty());

        }
        J1Box.getStyleClass().add("scoreBoardHighlight");

        //TODO get pseudo and display

    }

    /**
     * Permet de colorer les contour du scoreboard selon le joueur actuelle
     */
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


    public void swapHand(Button b) {
        int numeroLettreDansMain = Integer.parseInt(LetterWaiting.getId().replace("B", ""));
        int destination = Integer.parseInt(b.getId().replace("B", ""));
        mainApp.getScrabble().getJoueur(0).swap(numeroLettreDansMain, destination);
        LetterWaiting = null;
        showHand();
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
                if (LetterWaiting != null) {
                    Button b = (Button) event.getSource();
                    swapHand(b);
                } else {
                    Button b = (Button) event.getSource();
                    LetterWaiting = b;
                }
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
                    piecePlaceesCetteManche.add(mainApp.getScrabble().getJoueur(0).getMain().get(numeroLettreDansMain));
                    mainApp.getScrabble().getJoueur(0).getMain().remove(numeroLettreDansMain);

                    lettrePlaceesCetteManche.add(LetterWaiting);
                    LetterWaiting = null;
                    swapRecallBtn.getStyleClass().removeAll("swapImg");
                    swapRecallBtn.getStyleClass().add("recallImg");
                    showHand();
                }
            }
        };
    }

    public void resetBonusLabel(StackPane sp, int numero) {
        BonusCase bonus = mainApp.getScrabble().getBoard()[numero % 15][numero / 15].getBonus();
        if (((numero % 15) == 7) && ((numero / 15) == 7)) {
            sp.getChildren().add(new Label("\u2605"));
        } else {
            switch (bonus) {
                case Vide:
                    break;
                case LT:
                    sp.getChildren().add(new Label("LT"));
                    break;
                case MT:
                    sp.getChildren().add(new Label("MT"));
                    break;
                case MD:
                    sp.getChildren().add(new Label("MD"));
                    break;
                case LD:
                    sp.getChildren().add(new Label("LD"));
                    break;
            }
        }
    }

    public void drawboard(AnchorPane boardview) {
        AnchorPane paneBoard = (AnchorPane) boardview.lookup("#board");
        GridPane boardGrid = new GridPane();
        // espace inter colonne et ligne
        boardGrid.setVgap(2);
        boardGrid.setHgap(2);
        // pourcentage egale des cases (resizable)
        int numColsRows = 15;
        for (int i = 0; i < numColsRows; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numColsRows);
            boardGrid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numColsRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numColsRows);
            boardGrid.getRowConstraints().add(rowConst);
        }
        // ajout des cases (Pane)
        int cpt=0;
        for (int i = 0; i < numColsRows; i++) {
            for (int j = 0; j < numColsRows; j++) {
                StackPane p = new StackPane();
                p.setId("S" + cpt);
                cpt++;
                p.setAlignment(Pos.CENTER);
                //p.setStyle("-fx-background-color:#126B40");
                //p.getStyleClass().add("gradiantGeneral");
                boardGrid.add(p, i, j);
            }
        }
        //boardGrid.getStyleClass().add("gradiantGeneral");
        setColor(boardGrid);
        //boardGrid.setGridLinesVisible(true);

        boardGrid.setPadding(new Insets(5));
        // fit parent
        AnchorPane.setLeftAnchor(boardGrid, 0.0);
        AnchorPane.setRightAnchor(boardGrid, 0.0);
        AnchorPane.setTopAnchor(boardGrid, 0.0);
        AnchorPane.setBottomAnchor(boardGrid, 0.0);

        paneBoard.getChildren().add(boardGrid);

    }

    private void setColor(GridPane board) {
        Case[][] plateau = mainApp.getScrabble().getBoard();
        StackPane sp = new StackPane();
        for (int i = 0; i < 225; i++) {
            switch (plateau[i / 15][i % 15].getBonus()) {
                case MT:
                    sp = (StackPane) board.getChildren().get(i);
                    sp.getStyleClass().add("gradiantMT");
                    sp.getChildren().add(new Label("MT"));
                    break;
                case MD:
                    sp = (StackPane) board.getChildren().get(i);
                    sp.getStyleClass().add("gradiantMD");
                    sp.getChildren().add(new Label("MD"));
                    break;
                case LT:
                    sp = (StackPane) board.getChildren().get(i);
                    sp.getStyleClass().add("gradiantLT");
                    sp.getChildren().add(new Label("LT"));
                    break;
                case LD:
                    sp = (StackPane) board.getChildren().get(i);
                    sp.getStyleClass().add("gradiantLD");
                    sp.getChildren().add(new Label("LD"));
                    break;
                case Vide:
                    board.getChildren().get(i).getStyleClass().add("gradiantGeneral");
                    break;
            }

        }
        //etoile
        sp = (StackPane) board.getChildren().get(112);
        sp.getChildren().clear();
        sp.getStyleClass().add("gradiantMD");
        sp.getChildren().add(new Label("\u2605"));
    }
    //endregion

    //region Handler

    /**
     * Action event handler for the go to menu button
     *
     * @param actionEvent
     */
    @FXML
    public void HandleMenuButton(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
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

        int nbj = chooseNbPlayer();
        if (nbj != -1) {
            ArrayList<String> ias = chooseIAPlayer(nbj); // [false,false,true,true,"jean","gsd",null,null]
            if (ias != null) {
                //Create game
                mainApp.setScrabble(new Scrabble());

                Animations.SlideOutToLeft(menu, 500, mainApp.getPrimaryStage().getWidth());
                menu.toFront();
                mainApp.getScrabble().initPlayer(ias);


                initGame();
            }
        }
    }

    /**
     * Action event handler for the Load Game button on the menu
     *
     * @param actionEvent
     */
    @FXML
    public void HandleLoadGame(ActionEvent actionEvent) {
        // Now to read the object from file
        // save the object to file
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try {
            fis = new FileInputStream("ScrabbleSave.ser");
            in = new ObjectInputStream(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scrabble e = null;
        try {
            e = (Scrabble) in.readObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        mainApp.setScrabble(e);
        initGame();
        Animations.SlideOutToLeft(menu, 500, mainApp.getPrimaryStage().getWidth());
        menu.toFront();


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
        //si il s'agit du swap
        if (swapRecallBtn.getStyleClass().get(0).equals("swapImg")) {

            //si il s'agit du recall
        } else {
            int x = 0;
            for (Button b : lettrePlaceesCetteManche) {
                StackPane sp = (StackPane) b.getParent();
                int numeroDeCase = Integer.parseInt(sp.getId().replace("S", ""));
                //retirer dans le modele du board
                Case c = mainApp.getScrabble().getBoard()[numeroDeCase % 15][numeroDeCase / 15];
                mainApp.getScrabble().getBoard()[numeroDeCase % 15][numeroDeCase / 15] = new Case(c.getBonus(), c.getX(), c.getY());
                //ajouter à la main
                mainApp.getScrabble().getJoueur(0).getMain().add(piecePlaceesCetteManche.get(x));
                //retirer graphiquement
                sp.getChildren().remove(b);
                resetBonusLabel(sp, numeroDeCase);
                x++;
            }
            lettrePlaceesCetteManche.clear();
            piecePlaceesCetteManche.clear();
            swapRecallBtn.getStyleClass().removeAll("recallImg");
            swapRecallBtn.getStyleClass().add("swapImg");

            showHand();
        }
    }

    /**
     * Handle Jouer or Passer tour button
     *
     * @param actionEvent
     */
    @FXML
    public void HandleSJouerPasserTour(ActionEvent actionEvent) {
    }

    @FXML
    public void HandleSaveButton(ActionEvent actionEvent) {
        try (
                FileOutputStream fout = new FileOutputStream("ScrabbleSave.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
        ) {
            oos.writeObject(mainApp.getScrabble());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //TODO Save successful
    }


    //endregion

    //region Dialog

    /**
     * Affiche la fenêtre de dialogue pour choisir le nombre de joueur
     *
     * @return
     */
    public int chooseNbPlayer() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog with Custom Actions");
        alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("2");
        ButtonType buttonTypeTwo = new ButtonType("4");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return 2;
        } else if (result.get() == buttonTypeTwo) {
            return 4;
        } else if (result.get() == buttonTypeCancel) {
            return -1;
        }
        return -1;
    }

    /**
     * Fenetre de dialogue permettant de choisir IA ou non
     *
     * @param nbJ
     * @return ArrayList<Boolean> true si IA sinon false
     */
    public ArrayList<String> chooseIAPlayer(int nbJ) {
        // Create the custom dialog.
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choose IA or Player");
        dialog.setHeaderText("Choose IA or Player");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));


        ArrayList<ComboBox<String>> comboBoxes = new ArrayList<>();
        ComboBox<String> comboBoxJ1 = new ComboBox<>();
        ComboBox<String> comboBoxJ2 = new ComboBox<>();
        ComboBox<String> comboBoxJ3 = new ComboBox<>();
        ComboBox<String> comboBoxJ4 = new ComboBox<>();
        ArrayList<TextField> textFields = new ArrayList<>();
        TextField pseudoJ1 = new TextField();
        TextField pseudoJ2 = new TextField();
        TextField pseudoJ3 = new TextField();
        TextField pseudoJ4 = new TextField();


        if (nbJ == 2) { // Si 2 joueurs

            grid.add(comboBoxJ1, 0, 0);
            grid.add(comboBoxJ2, 0, 1);
            grid.add(pseudoJ1, 1, 0);
            grid.add(pseudoJ2, 1, 1);

            textFields.add(pseudoJ1);
            textFields.add(pseudoJ2);

            comboBoxes.add(comboBoxJ1);
            comboBoxes.add(comboBoxJ2);


        } else { //Sinon 4 joueurs

            grid.add(comboBoxJ1, 0, 0);
            grid.add(comboBoxJ2, 0, 1);
            grid.add(comboBoxJ3, 0, 2);
            grid.add(comboBoxJ4, 0, 3);
            grid.add(pseudoJ1, 1, 0);
            grid.add(pseudoJ2, 1, 1);
            grid.add(pseudoJ3, 1, 2);
            grid.add(pseudoJ4, 1, 3);

            textFields.add(pseudoJ1);
            textFields.add(pseudoJ2);
            textFields.add(pseudoJ3);
            textFields.add(pseudoJ4);


            comboBoxes.add(comboBoxJ1);
            comboBoxes.add(comboBoxJ2);
            comboBoxes.add(comboBoxJ3);
            comboBoxes.add(comboBoxJ4);


        }
        //Initialise les valeurs des combobox
        for (ComboBox<String> cb : comboBoxes) {
            cb.getItems().addAll("IA", "Humain");

        }

        // Vérifie que les combobox on etait remplie
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);
        if (nbJ == 4) {

            loginButton.disableProperty().bind(
                    Bindings.isNull(comboBoxJ1.getSelectionModel().selectedItemProperty())
                            .or(Bindings.isNull(comboBoxJ2.getSelectionModel().selectedItemProperty()))
                            .or(Bindings.isNull(comboBoxJ3.getSelectionModel().selectedItemProperty()))
                            .or(Bindings.isNull(comboBoxJ4.getSelectionModel().selectedItemProperty()))
            );
        } else if (nbJ == 2) {
            loginButton.disableProperty().bind(
                    Bindings.isNull(comboBoxJ1.getSelectionModel().selectedItemProperty())
                            .or(Bindings.isNull(comboBoxJ2.getSelectionModel().selectedItemProperty()))
            );
        }
        pseudoJ1.disableProperty().bind(comboBoxJ1.getSelectionModel().selectedItemProperty().isEqualTo("IA"));
        pseudoJ2.disableProperty().bind(comboBoxJ2.getSelectionModel().selectedItemProperty().isEqualTo("IA"));
        pseudoJ3.disableProperty().bind(comboBoxJ3.getSelectionModel().selectedItemProperty().isEqualTo("IA"));
        pseudoJ4.disableProperty().bind(comboBoxJ4.getSelectionModel().selectedItemProperty().isEqualTo("IA"));


        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();

        //Récupération des données
        ArrayList<String> ia = new ArrayList<>();

        if (result.get() == loginButtonType) {
            //met la valeur des combobox dans l'arraylist
            for (ComboBox<String> cb : comboBoxes) {
                if (cb.getSelectionModel().getSelectedItem().equals("IA")) {
                    ia.add("true");
                } else {
                    ia.add("false");
                }
            }
            //met les pseudo dans l'arraylist
            for (TextField textField : textFields) {
                if (textField.isDisabled() == true) {
                    ia.add(null);
                } else {
                    ia.add(textField.getText());
                }
            }
            return ia;
        } else if (result.get() == ButtonType.CANCEL) { //IF cancel
            return null;
        } else {
            return null;
        }

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

        mainApp.getScrabble().getJoueur(0).addNbPoints(5);
    }
    //endregion


}
