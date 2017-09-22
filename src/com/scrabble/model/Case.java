package com.scrabble.model;


import javafx.scene.image.ImageView;

/**
 * Created by loic on 18/09/2017.
 */
public class Case {

    private ImageView texture;
    private Piece piece;
    private String bonus;

    public Case(ImageView texture) {
        this.texture = texture;

    }

    public ImageView getTexture() {
        return texture;
    }

    public void setTexture(ImageView texture) {
        this.texture = texture;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }
}
