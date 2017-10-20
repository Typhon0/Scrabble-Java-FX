package com.scrabble.model;


import javafx.scene.control.Button;

public class ImageButton extends Button {

    public ImageButton() {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add("ImageButton");
    }
}
