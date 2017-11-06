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
    
    private ArrayList<Piece> motPose;

    // Constructeur par défaut
    public Joueur() {
        this.pseudo = "";
        this.nbPoints = 0;
        this.main = new ArrayList<Piece>();
        this.essaiMot = new ArrayList<Piece>();
        this.motPose = new ArrayList<Piece>();
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
        this.essaiMot = new ArrayList<Piece>();
        this.motPose = new ArrayList<Piece>();
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
    		if(c.estLibre()) // si la case est libre
    		{
    			c.setPiece(p); // Ajout de la piece a la case
    			p.setCasePiece(c); // Ajout de la case a la piece
    			this.essaiMot.add(p);
    		}
    		else
    		{
    				System.out.println("Case deja prise");
    		}
    	}
    }
    
    public boolean possede(Piece p)
    {
    	return this.main.contains(p);
    }
    
    public boolean motValide(Case[][] board, Dictionnaire dico)
    {
    	Piece firstPiece = essaiMot.get(0); // Premiere piece posee
    	Piece lastPiece = essaiMot.get(essaiMot.size()-1); // Derniere piece posee
    	int firstX = firstPiece.getCasePiece().getX();
    	int lastX = lastPiece.getCasePiece().getX();
    	int firstY = firstPiece.getCasePiece().getY();
    	int lastY = lastPiece.getCasePiece().getY();
    	
    	boolean motValide = true; //flag
    	String mot;
    	
    	if(firstX != lastX) // Mot horizontal
    	{
    		int[] indices = indicesMotComplet(board, true, firstX, lastX, firstY);
    		int jMin = indices[0];
    		int jMax = indices[1];
    		
    		mot = construireMot(piecesMotHorizontal(board, firstY, jMin, jMax));
    		if(!(existe(mot,dico)))
    		{
    			return false; // si le mot construit horizontalement n'existe pas
    		}
    		
    		for(int x=jMin ; x<=jMax; x++)
    		{
    			if(!(testMotVertical(dico, board, x, firstY)))
    			{
    				return false; // si, pour chaque piece horizontale qui complete un mot vertical, un des mots n'existe pas
    			}
    		}
    		


    	}
    	else if(firstY != lastY) // Mot vertical
    	{
    		int[] indices = indicesMotComplet(board, false, firstY, lastY, firstX);
    		int iMin = indices[0];
    		int iMax = indices[1];
       		
    		mot = construireMot(piecesMotVertical(board, firstX, iMin, iMax));
    		if(!(existe(mot,dico)))
    		{
    			return false; // si le mot construit horizontalement n'existe pas
    		}
    		
    		for(int y=iMin ; y<=iMax; y++)
    		{
    			if(!(testMotHorizontal(dico, board, y, firstX)))
    			{
    				return false; // si, pour chaque piece horizontale qui complete un mot vertical, un des mots n'existe pas
    			}
    		}

    	}
    	else // une seule lettre a ete posee
    	{
    		int[] indicesHor = indicesMotComplet(board, false, firstY, lastY, firstX);
    		int[] indicesVer = indicesMotComplet(board, false, firstY, lastY, firstX);
    		
    		mot = construireMot(piecesMotHorizontal(board, firstY, indicesHor[0], indicesHor[1]));
    		if(!(existe(mot,dico)))
    		{
    			return false; // si la piece posee complete un mot horizontal qui n'existe pas
    		}
    		
    		mot = construireMot(piecesMotVertical(board, firstX, indicesVer[0], indicesVer[1]));
    		if(!(existe(mot,dico)))
    		{
    			return false; // si la piece posee complete un mot vertical qui n'existe pas
    		}
    	}

    	
    	return motValide; // Sinon le mot est valide
    }
    
    private int[] indicesMotComplet(Case[][] board, boolean horizontal, int min, int max, int y)
    {
    	int newMin = min;
    	int newMax = max;
    	int[] indices = new int[2];
    	
    	if(horizontal)
    	{
    		while(!(board[y][newMin].estLibre()) && newMin >= 0) // tant que les cases avant la premiere piece posee ne sont pas libres
    		{
    			newMin -= 1;
    		}
    		while(!(board[y][newMax].estLibre()) && newMax < 15) // tant que les cases avant la premiere piece posee ne sont pas libres
    		{
    			newMax += 1;
    		}
    	}
    	else
    	{
    		while(!(board[newMin][y].estLibre()) && newMin >= 0) // tant que les cases avant la premiere piece posee ne sont pas libres
    		{
    			newMin -= 1;
    		}
    		while(!(board[newMax][y].estLibre()) && newMax < 15) // tant que les cases avant la premiere piece posee ne sont pas libres
    		{
    			newMax += 1;
    		}
    	}
    	
    	indices[0] = newMin;
    	indices[1] = newMax;
    	
    	return indices;
    }
    
    private boolean testMotVertical(Dictionnaire dico, Case[][] board, int x, int y) {
    	String mot;
		
		int iMin = y;
		int iMax = y;
		
		while(!(board[iMin][x].estLibre()) && iMin >= 0) // tant que les cases avant la premiere piece posee ne sont pas libres
		{
			iMin -= 1;
		}
		while(!(board[iMax][x].estLibre()) && iMax < 15) // tant que les cases avant la premiere piece posee ne sont pas libres
		{
			iMax += 1;
		}
		
		if(iMin == iMax) // une seule lettre � la verticale pour un mot construit horizontalement -> OK
		{
			return true;
		}
		else
		{
    		mot = construireMot(piecesMotVertical(board, x, iMin, iMax));
    		return existe(mot,dico);
		}
	}
    
    private boolean testMotHorizontal(Dictionnaire dico, Case[][] board, int y, int x) {
    	String mot;
		
		int jMin = x;
		int jMax = x;
		
		while(!(board[y][jMin].estLibre()) && jMin >= 0) // tant que les cases avant la premiere piece posee ne sont pas libres
		{
			jMin -= 1;
		}
		while(!(board[y][jMax].estLibre()) && jMax < 15) // tant que les cases avant la premiere piece posee ne sont pas libres
		{
			jMax += 1;
		}
		
		if(jMin == jMax) // une seule lettre � la verticale pour un mot construit horizontalement -> OK
		{
			return true;
		}
		else
		{
    		mot = construireMot(piecesMotHorizontal(board, x, jMin, jMax));
    		return existe(mot,dico);
		}
	}

    
    public ArrayList<Piece> piecesMotHorizontal(Case[][] board, int y, int xMin, int xMax)
    {
    	ArrayList<Piece> pieces = new ArrayList<Piece>();
    	
    	for(int j = xMin ; j <= xMax ; j++)
		{
			pieces.add(board[y][j].getPiece());
		}
    	
    	return pieces;
    }
    
	public String construireMot(ArrayList<Piece> pieces)
    {
    	String mot = "";

		for(Piece p : pieces)
		{
			mot += p.getLettre();
		}
		return mot;
    }
    
    public ArrayList<Piece> piecesMotVertical(Case[][] board, int x, int yMin, int yMax)
    {
    	ArrayList<Piece> pieces = new ArrayList<Piece>();
    	
    	for(int i = yMin ; i <= yMax ; i++)
		{
			pieces.add(board[i][x].getPiece());
		}
    	
    	return pieces;
    }
    
    public boolean existe(String mot, Dictionnaire dico)
    {
    	return dico.motExistant(mot);
    }
    
    
}
