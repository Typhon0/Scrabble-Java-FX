package com.scrabble.model;

/**
 * Created by loic on 18/09/2017.
 */
public class Case {

    private Piece piece;
    private BonusCase bonus;

    public Case(BonusCase _bonus) {
        piece = new Piece(); // La case est vide par defaut
        bonus = _bonus;
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
