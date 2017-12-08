package com.scrabble.model;

import java.io.Serializable;

/**
 * Created by loic on 18/09/2017.
 */
public class Case implements Serializable {

    //region Variables
    private Piece piece;
    private BonusCase bonus;
    private boolean libre;
    private int x; // coordonne x de la case sur le plateau
    private int y; // coordonnee y de la case sur le plateau

    //endregion

    //region Constructor

    public Case(BonusCase _bonus, int i, int j) {
        libre = true;
        bonus = _bonus;
        this.x = j;
        this.y = i;
    }

    //endregion

    //region Getters Setters

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (this.piece != null) {
            this.piece.setCasePiece(this);
        }
        this.libre = false;
    }

    public BonusCase getBonus() {
        return bonus;
    }

    public void setBonus(BonusCase bonus) {
        this.bonus = bonus;
    }

    public boolean estLibre() // Renvoie vrai si la case est libre
    {
        return this.libre;
    }

    //endregion


}
