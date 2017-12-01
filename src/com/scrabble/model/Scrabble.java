package com.scrabble.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by loic on 18/09/2017.
 */
public class Scrabble {

    private Case[][] board;
    private Pioche pioche;
    private ArrayList<Joueur> joueurs;
    private Dictionnaire dictionnaire;
    private int courantPlayer;
    private IntegerProperty currentPlayerProperty;

    private int nbPlayer;

    public Scrabble() {
        board = new Case[15][15];
        initTab();
        initBag();
        initJoueurs();
        nbPlayer=4;
        courantPlayer=0;
        initDictionnaire();
        currentPlayerProperty = new SimpleIntegerProperty();

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
    
    public Dictionnaire getDico()
    {
    	return this.dictionnaire;
    }


    public void initJoueurs() {
        joueurs = new ArrayList<>();
        joueurs.add(new Joueur("Utilisateur", pioche));
        joueurs.add(new Joueur("IA", pioche));
    }

    public Case[][] getBoard() {
        return board;
    }

    public void initDictionnaire() {
        Dictionnaire dico = new Dictionnaire();
        this.dictionnaire = dico;
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

    public boolean finDuJeu(Joueur j)
    {
    	boolean fin = false;
    	
    	if(pioche.isEmpty() && j.getMain().isEmpty())
    	{
    		fin = true;
    	}
    	
    	return fin;
    }


    public void changementTour(){
        if(courantPlayer==nbPlayer-1)
            setCourantPlayer(0);
        else
            setCourantPlayer(courantPlayer++);
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
}
