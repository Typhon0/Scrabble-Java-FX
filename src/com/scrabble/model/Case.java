package com.scrabble.model;

/**
 * Created by loic on 18/09/2017.
 */
public class Case {

    private Piece piece;
    private BonusCase bonus;
    private boolean libre;
    private int x; // coordonnée x de la case sur le plateau
    private int y; // coordonnee y de la case sur le plateau

    public Case(BonusCase _bonus, int i, int j) {
        libre = true;
        bonus = _bonus;
        this.x = j;
        this.y = i;
    }
    
    public int getX()
    {
    	return x;
    }
    
    public int getY()
    {
    	return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
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


}
