/**
 * @author Remi Lenoir
 */
package com.scrabble.model;

import java.io.Serializable;

public class Piece implements Serializable {

    //region Variables

    // Lettre correspondant à la pièce
    private char lettre;
    // Valeur de la pièce
    private int value;

    private Case casePiece; // Case sur laquelle la piece est posee
    private boolean posee; // Indique si la piece est posee sur une case
    //endregion

    //region Constructor

    /**
     * @param lettre is the character of the piece
     * @param value  is the number of point of the letter
     */
    public Piece(char lettre, int value) {
        this.lettre = lettre;
        this.value = value;
        this.posee = false; // la piece n'est pas posee au depart
        this.casePiece = null; // et n'a donc pas de case associee
    }

    //endregion

    //region Getters Setters

    public void setCasePiece(Case c) {
        casePiece = c;
        this.posee = true; // la piece est desormais posee
    }


    public boolean estPosee() {
        return posee;
    }

    public Case getCasePiece() {
        if (posee) // si la case est posee
        {
            return casePiece;
        } else {
            System.out.println("La piece n'est pas posee.");
            return new Case(BonusCase.Vide, -1, -1);
        }
    }

    // Retourne de la lettre de la pièce
    public char getLettre() {
        return lettre;
    }

    // Modifie la lettre de la pièce par le paramètre
    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    // Retourne la valeur de la pièce
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //endregion
}
