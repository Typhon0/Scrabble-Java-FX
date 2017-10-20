package com.scrabble;

import com.scrabble.controller.MainUIController;
import com.scrabble.model.BonusCase;
import com.scrabble.model.Case;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.scrabble.model.Scrabble;

import java.awt.Button;
import java.awt.List;
import java.io.IOException;
import java.util.Arrays;

import static com.scrabble.model.BonusCase.MT;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Scrabble scrabble = new Scrabble();;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Scrabble JavaFX");
		initRootLayout();
		showBoard();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("view/style.css").toExternalForm());

			primaryStage.setHeight(625);
			primaryStage.setWidth(725);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the board inside the root layout.
	 */
	public void showBoard() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/board.fxml"));
			AnchorPane boardview = (AnchorPane) loader.load();

			drawboard(boardview);

			rootLayout.setCenter(boardview);

            // Give the controller access to the main app.
            MainUIController controller = loader.getController();
            controller.setMainApp(this);


		} catch (IOException e) {
			e.printStackTrace();
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
		for (int i = 0; i < numColsRows; i++) {
			for (int j = 0; j < numColsRows; j++) {
				StackPane p = new StackPane();
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
		Case[][] plateau = scrabble.getBoard();
		StackPane sp = new StackPane();
		for(int i = 0;i<225;i++){
			switch(plateau[i/15][i%15].getBonus()) {
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


	
	
	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}