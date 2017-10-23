package com.scrabble.control;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class ImageButton extends Button {

    public ImageButton() {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add("ImageButton");

    }
}
