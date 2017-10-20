package com.scrabble.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alexr on 22/09/2017.
 */
public class Joueur {
    // Pseudo du joueur
    private String pseudo;
    // Nombre de points du joueur
    private int nbPoints;
    // Liste des pièces dans la main du joueur
    private ArrayList<Piece> main;
    
    // Tentative de creation d'un mot sur le tour
    private ArrayList<Piece> essaiMot;
    private ArrayList<Case> positionMot;

    // Constructeur par défaut
    public Joueur() {
        this.pseudo = "";
        this.nbPoints = 0;
        this.main = new ArrayList<Piece>();
        this.essaiMot = new ArrayList<Piece>();  
    }


    /* Constructeur du joueur
    * pseudo = Pseudo du joueur
    * pioche = Les 7 premières pièces de la main
    */
    public Joueur(String pseudo, Pioche pioche) {
        this.pseudo = pseudo;
        this.nbPoints = 0;
        this.main = new ArrayList<Piece>();
        main = pioche.takeLetterInBag(7);
    }

    // Retourne du nom du joueur
    public String getNom(){
        return this.pseudo;
    }

    // Retourne du nombre de points du joueur
    public int getNbPoints(){
        return this.nbPoints;
    }

    // Retourne de la main du joueur
    public ArrayList<Piece> getMain(){
        return this.main;
    }

    // Modifie les pseudo du joueur
    public void setPseudo(String newPseudo){
        this.pseudo = newPseudo;
    }

    // Ajoute les points au nombre de points du joueur
    public void addNbPoints(int points){
        this.nbPoints = this.nbPoints + points;
    }
    
    public void poserUnePiece(Piece p, Case c)
    {
    	if(possede(p)) // Si le joueur possede cette piece
    	{
    				System.out.println("piece non possedee");
    		if(c.estLibre())
    		{
    			c.setPiece(p);
    			this.essaiMot.add(p);
    		}
    		else
    		{
    				System.out.println("piece non vide");
    		}
    	}
    }
    
    public boolean possede(Piece p)
    {
    	return this.main.contains(p);
    }
    
    public boolean motValide()
    {
    	
    	
    	return false;
    }
    
}
