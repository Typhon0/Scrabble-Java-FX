package model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alexr on 22/09/2017.
 */
public class Joueur {
    private String pseudo;
    private int nbPoints;
    private ArrayList<Piece> main;

    public Joueur(String pseudo, Pioche pioche) {
        this.pseudo = pseudo;
        this.nbPoints = 0;
        this.main = new ArrayList<Piece>();
        main = pioche.takeLetterInBag(7);
    }
}
