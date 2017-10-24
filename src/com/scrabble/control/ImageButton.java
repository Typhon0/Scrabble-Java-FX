package com.scrabble.control;


import javafx.scene.control.Button;

/**
 * Custom button with an image
 */
public class ImageButton extends Button {

    public ImageButton() {
        super();
        this.getStyleClass().clear();
        this.getStyleClass().add("ImageButton");

    }
}
