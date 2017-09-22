import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Scrabble;

import java.io.IOException;

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
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
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
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/board.fxml"));
            AnchorPane boardview = (AnchorPane) loader.load();


            drawboard(boardview);


            // Set person overview into the center of root layout.
            rootLayout.setCenter(boardview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void drawboard(AnchorPane boardview) {

        int x = 0;
        int y = 0;
        ImageView imageView = null;

        for (int i = 0; i < 15; i++) {
            x = 0;
            for (int j = 0; j < 15; j++) {
                imageView = scrabble.getBoard()[i][j].getTexture();
                imageView.setLayoutY(y);
                imageView.setLayoutX(x);
                boardview.getChildren().add(imageView);
                System.out.println(y + " X :" + x);
                x = x + 50;

            }
            y = y + 60;
        }
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
