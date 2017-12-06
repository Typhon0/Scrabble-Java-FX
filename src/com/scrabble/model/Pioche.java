/**
 * @author Remi Lenoir
 */
package com.scrabble.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Pioche implements Serializable {
    //region Variables

    //bag contain all the letter
    private ArrayList<Piece> bag;
    private int nbPiece;
    private transient IntegerProperty nbPieceProperty;

    //endregion

    //region Constructor


    Pioche() {
        bag = new ArrayList<Piece>();
        addToBag('A', 1, 9);
        addToBag('B', 3, 2);
        addToBag('C', 3, 2);
        addToBag('D', 2, 3);
        addToBag('E', 1, 15);
        addToBag('F', 4, 2);
        addToBag('G', 2, 2);
        addToBag('H', 4, 2);
        addToBag('I', 1, 8);
        addToBag('J', 8, 1);
        addToBag('K', 10, 1);
        addToBag('L', 1, 5);
        addToBag('M', 2, 3);
        addToBag('N', 1, 6);
        addToBag('O', 1, 6);
        addToBag('P', 3, 2);
        addToBag('Q', 8, 1);
        addToBag('R', 1, 6);
        addToBag('S', 1, 6);
        addToBag('T', 1, 6);
        addToBag('U', 1, 6);
        addToBag('V', 4, 2);
        addToBag('W', 10, 1);
        addToBag('X', 10, 1);
        addToBag('Y', 10, 1);
        addToBag('Z', 10, 1);
        addToBag('?', 0, 2);
        Collections.shuffle(bag);    //shuffle the bag
        nbPieceProperty = new SimpleIntegerProperty(nbPieceInBag());
        this.nbPiece = nbPieceInBag();

    }
    //endregion

    //region Functions

    /**
     * @param lettre is the character to add
     * @param val    is the number of point of the letter
     * @param nb     is the number of pieces with the character "lettre"
     */
    private void addToBag(char lettre, int val, int nb) {
        for (int i = 0; i < nb; i++) {
            bag.add(new Piece(lettre, val));
        }
    }

    //add the piece P in the bag
    public void addInBag(Piece p) {
        bag.add(p);
        setNbPiece(nbPiece + 1);

    }

    //add all the piece contain in remise in the bag
    public void addInBag(ArrayList<Piece> remise) {
        for (Piece p : remise) {
            bag.add(p);
            setNbPiece(nbPiece + 1);

        }
    }

    //take the number of "count" letter in the bag
    public ArrayList<Piece> takeLetterInBag(int count) {
        if (count > bag.size()) {
            count = bag.size();
        }
        ArrayList<Piece> miniList = new ArrayList<Piece>();
        for (int i = 0; i < count; i++) {
            miniList.add(bag.get(0));
            bag.remove(0);
            setNbPiece(nbPiece - 1);
        }
        return miniList;
    }

    public int nbPieceInBag() {
        return bag.size();
    }

    //return the max points contain in the bag
    public int pointsInBag() {
        int res = 0;
        for (Piece p : bag) {
            res += p.getValue();
        }
        return res;
    }


    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        nbPieceProperty = new SimpleIntegerProperty(nbPiece);
    }
    //endregion

    //region Getters Setters

    public ArrayList<Piece> getBag() {
        return bag;
    }

    public boolean isEmpty() {
        return bag.isEmpty();
    }

    public int getNbPiece() {
        return nbPiece;
    }

    public void setNbPiece(int nbPiece) {
        this.nbPiece = nbPiece;
        this.nbPieceProperty.set(nbPiece);
    }

    public int getNbPieceProperty() {
        return nbPieceProperty.get();
    }

    public IntegerProperty nbPiecePropertyProperty() {
        return nbPieceProperty;
    }

    public void setNbPieceProperty(int nbPieceProperty) {
        this.nbPieceProperty.set(nbPieceProperty);
    }


    //endregion
}
