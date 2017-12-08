package com.scrabble.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by loic on 18/09/2017.
 */
public class Scrabble implements Serializable {

    //region Variables
    private static final long serialVersionUID = 1L;
    private Case[][] board;
    private Pioche pioche;
    private ArrayList<Joueur> joueurs;
    private transient Dictionnaire dictionnaire;
    private int courantPlayer;
    private transient IntegerProperty currentPlayerProperty;
    private int nbPlayer;
    private boolean premierMot;
    //endregion

    //region Constructor
    public Scrabble() {
        board = new Case[15][15];
        initTab();
        initBag();
        this.joueurs = new ArrayList<>();
        nbPlayer = 4;
        courantPlayer = 0;
        initDictionnaire();
        currentPlayerProperty = new SimpleIntegerProperty();
        premierMot = true;

    }
    //endregion

    //region Functions

    public void initPlayer(ArrayList<String> ias) {
        //ias length = 4 || 2
        for (int i = 0; i < ias.size() / 2; i++) {
            if (Boolean.valueOf(Boolean.valueOf(ias.get(i)) == true)) { // Si IA
                getJoueurs().add(new Joueur(pioche));
            } else {
                getJoueurs().add(new Joueur(ias.get(i+ias.size() / 2),pioche));

            }
        }

        nbPlayer = ias.size() / 2;
        setCourantPlayer(0); // met le joueur 1 en joueur actuelle
    }

    public void initTab() {
        //tableau contenant les index de cases a bonus
        int[] MT, MD, LT, LD;
        MT = new int[]{0, 7, 14, 105, 119, 210, 217, 224};
        MD = new int[]{16, 28, 32, 42, 48, 56, 64, 70, 154, 160, 168, 176, 182, 192, 196, 208};
        LT = new int[]{20, 24, 76, 80, 84, 88, 136, 140, 144, 148, 200, 204};
        LD = new int[]{3, 11, 36, 38, 45, 52, 59, 92, 96, 98, 102, 108, 116, 122, 126, 128, 132, 165, 172, 179, 186,
                188, 213, 221};
        //initialisation des 255 cases a Vide
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Case(BonusCase.Vide, i, j);
            }
        }
        //attribution des bonus
        for (int k : MT) {
            board[k / 15][k % 15].setBonus(BonusCase.MT);
        }
        for (int k : MD) {
            board[k / 15][k % 15].setBonus(BonusCase.MD);
        }
        for (int k : LT) {
            board[k / 15][k % 15].setBonus(BonusCase.LT);
        }
        for (int k : LD) {
            board[k / 15][k % 15].setBonus(BonusCase.LD);
        }
        //le milieu (etoile)
        board[7][7].setBonus(BonusCase.MD);
/*
        for (int x = 0;x<15; x++){
            for(int y=0;y<15;y++){
                System.out.print("[" + board[x][y].getBonus() + "]");
            }
            System.out.println();
        }
*/
    }

    public void initBag() {
        pioche = new Pioche();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        currentPlayerProperty = new SimpleIntegerProperty(courantPlayer);

    }

    public void initDictionnaire() {
        Dictionnaire dico = new Dictionnaire();
        this.dictionnaire = dico;
    }

    //endregion

    //region Getters Setters

    public Joueur getCourantJoueur(){
        return joueurs.get(courantPlayer);
    }

    public Dictionnaire getDico() {
        return this.dictionnaire;
    }


    public Case[][] getBoard() {
        return board;
    }


    public void setBoard(Case[][] board) {
        this.board = board;
    }

    public Joueur getJoueur(int NoJoueur) {
        if (NoJoueur > 3 && NoJoueur < 0) {
            throw new IllegalArgumentException();
        } else {
            return joueurs.get(NoJoueur);
        }
    }

    public boolean finDuJeu(Joueur j) {
        boolean fin = false;

        if (pioche.isEmpty() && j.getMain().isEmpty()) {
            fin = true;
        }

        return fin;
    }

    public void reglesDeFin(){
        int pl = courantPlayer;
        int i;
        if(pl==nbPlayer-1)
            i = 0;
        else
            i=nbPlayer+1;
        while (i!=pl){
            int j=0;
            while (this.getJoueur(i).getMain().get(j)!=null) {
                this.getJoueur(pl).setNbPoints(this.getJoueur(i).getMain().get(j).getValue());
                this.getJoueur(i).setNbPoints(- this.getJoueur(i).getMain().get(j++).getValue());
            }
        }
    }


    public void changementTour() {
    	if(this.premierMot && this.getJoueur(courantPlayer).getAJoue())
    	{
    		this.setPremierMot(false);
    	}
        if (courantPlayer == nbPlayer - 1)
            setCourantPlayer(0);
        else
            setCourantPlayer(courantPlayer+1);
    }


    public void setCourantPlayer(int courantPlayer) {
        this.courantPlayer = courantPlayer;
        setCurrentPlayerProperty(courantPlayer);
    }

    public int getCourantPlayer() {
        return courantPlayer;
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public int getCurrentPlayerProperty() {
        return currentPlayerProperty.get();
    }

    public IntegerProperty currentPlayerPropertyProperty() {
        return currentPlayerProperty;
    }

    public void setCurrentPlayerProperty(int currentPlayerProperty) {
        this.currentPlayerProperty.set(currentPlayerProperty);
    }

    public Pioche getPioche() {
        return pioche;
    }

    public boolean isPremierMot()
    {
    	return premierMot;
    }
    
    public void setPremierMot(boolean pm)
    {
    	premierMot = pm;
    }

    //endregion
}
