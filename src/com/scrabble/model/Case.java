package com.scrabble.model;


import javafx.scene.image.ImageView;

/**
 * Created by loic on 18/09/2017.
 */
public class Case {

    private ImageView texture;
    private Piece piece;
    private BonusCase bonus;

    public Case(ImageView texture, BonusCase _bonus) {
        this.texture = texture;
        piece = new Piece(); // La case est vide par defaut
        bonus = _bonus;
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

    public BonusCase getBonus() {
        return bonus;
    }

    public void setBonus(BonusCase bonus) {
        this.bonus = bonus;
    }
    
    public boolean estVide() // Renvoie vrai si la case contient la piece vide, faux sinon
    {
    	return (piece.getLettre()=='@')&&(piece.getValue()==0);
    }
}
