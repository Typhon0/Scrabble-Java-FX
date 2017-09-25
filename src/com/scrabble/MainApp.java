package com.scrabble;

import com.scrabble.controller.MainUIController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import com.scrabble.model.Scrabble;

import java.awt.Button;
import java.awt.List;
import java.io.IOException;
import java.util.Arrays;

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

			primaryStage.setMinHeight(360);
			primaryStage.setMinWidth(480);
			primaryStage.setScene(scene);
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
				Pane p = new Pane();
				p.setStyle("-fx-background-color:#126B40"); 
				boardGrid.add(p, i, j);
			}
		}
		setColorCases(boardGrid);
		//setWordCases(boardGrid);
		
		boardGrid.setPadding(new Insets(5));
		// fit parent
		AnchorPane.setLeftAnchor(boardGrid, 0.0);
		AnchorPane.setRightAnchor(boardGrid, 0.0);
		AnchorPane.setTopAnchor(boardGrid, 0.0);
		AnchorPane.setBottomAnchor(boardGrid, 0.0);

		paneBoard.getChildren().add(boardGrid);
		// TODO force to be square
	}

	private void setColorCases(GridPane board) {
		int[] listeMT = { 0, 7, 14, 105, 119, 210, 217, 224 };
		int[] listeLD = { 3, 11, 36, 38, 45, 52, 59, 92, 96, 98, 102, 108, 116, 122, 126, 128, 132, 165, 172, 179, 186,
				188, 213, 221 };
		int[] listeMD = { 16, 28, 32, 42, 48, 56, 64, 70, 154, 160, 168, 176, 182, 192, 196, 208 };
		int[] listeLT = { 20, 24, 76, 80, 84, 88, 136, 140, 144, 148, 200, 204 };
		for (int i : listeMT) {
			board.getChildren().get(i).setStyle("-fx-background-color:#C7031E");
		}
		for (int i : listeLT) {
			board.getChildren().get(i).setStyle("-fx-background-color:#6093D1");
		}
		for (int i : listeMD) {
			board.getChildren().get(i).setStyle("-fx-background-color:#ED989E");
		}
		for (int i : listeLD) {
			board.getChildren().get(i).setStyle("-fx-background-color:#6CDAE7");
		}
		//milieu (etoile)
		board.getChildren().get(112).setStyle("-fx-background-color:#ED989E");
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
