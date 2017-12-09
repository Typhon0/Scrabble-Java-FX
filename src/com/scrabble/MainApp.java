package com.scrabble;

import com.scrabble.controller.MainUIController;
import com.scrabble.model.Case;
import com.scrabble.model.Scrabble;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private Scrabble scrabble;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Scrabble JavaFX");
        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/board.fxml"));
            rootLayout = (AnchorPane) loader.load();
            // Give the controller access to the main app.
            MainUIController controller = loader.getController();
            controller.setMainApp(this);
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("ressources/Styles/style.css").toExternalForm());
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileExist(String filename) {
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }


    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scrabble getScrabble() {
        return this.scrabble;
    }

    public void setScrabble(Scrabble scrabble) {
        this.scrabble = scrabble;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
