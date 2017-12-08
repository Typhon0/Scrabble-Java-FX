package com.scrabble.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by alexr on 22/09/2017.
 */
public class Joueur implements Serializable {

    //region Variables
    // Pseudo du joueur
    private String pseudo;
    // Nombre de points du joueur
    private transient StringProperty pseudoProperty;
    //property du pseudo
    private int nbPoints;
    //Property du nombre de point
    private transient IntegerProperty nbPointsProperty;
    // Liste des pièces dans la main du joueur
    private ArrayList<Piece> main;

    // Tentative de creation d'un mot sur le tour
    private transient ObservableList<Piece> essaiMot;

    private ArrayList<Piece> motPose;

    private boolean IA;

    //endregion

    //region Constructor

    // Constructeur IA
    public Joueur(Pioche pioche) {
        this.pseudo = "IA";
        this.nbPoints = 0;
        this.main = new ArrayList<Piece>();
        main = pioche.takeLetterInBag(7);

        this.essaiMot = FXCollections.observableArrayList();
        this.motPose = new ArrayList<Piece>();
        this.nbPointsProperty = new SimpleIntegerProperty();
        this.pseudoProperty = new SimpleStringProperty("IA");
        setNbPointsProperty(0);
        IA = true;
    }

    // Constructeur Joueur pseudo
    public Joueur(String pseudo,Pioche pioche) {
        this.pseudo = pseudo;
        this.nbPoints = 0;
        this.main = new ArrayList<Piece>();
        main = pioche.takeLetterInBag(7);

        this.essaiMot = FXCollections.observableArrayList();
        this.motPose = new ArrayList<Piece>();
        this.nbPointsProperty = new SimpleIntegerProperty();
        this.pseudoProperty = new SimpleStringProperty(pseudo);
        setNbPointsProperty(0);
        IA = false;
    }


    //endregion

    //region Functions

    // Ajoute les points au nombre de points du joueur
    public void addNbPoints(int points) {
        this.nbPoints = this.nbPoints + points;
        this.setNbPointsProperty(nbPoints);
    }

    public void poserUnePiece(Piece p, Case c) {
        if (possede(p)) // Si le joueur possede cette piece
        {
            if (c.estLibre()) // si la case est libre
            {
                c.setPiece(p); // Ajout de la piece a la case
                p.setCasePiece(c); // Ajout de la case a la piece
                this.essaiMot.add(p);
            } else {
                System.out.println("Case deja prise");
            }
        }
    }
    
    
    public void retirerLettresDuMot()
    {
    	for(Piece p : this.essaiMot)
    	{
    		if(possede(p)) { this.main.remove(p); }
    	}
    }
    
    public void piocher(Pioche pioche)
    {
    	this.main.addAll(pioche.takeLetterInBag(essaiMot.size()));
    }
    
    
    public void viderEssaiMot()
    {
    	essaiMot.clear();
    }
    
    
    public boolean jouerMot(Scrabble scrab)
    {
    	if(!(essaiMot.isEmpty()) && checkPremierMot(scrab.isPremierMot()) && motValide(scrab.getBoard(),scrab.getDico())) 
    	{
    		this.addNbPoints(compterPoints(scrab.getBoard())); // ajoute les points
    		retirerLettresDuMot(); // retire les lettres posees de la main du joueur
    		piocher(scrab.getPioche());
    		viderEssaiMot(); // vide la tentative de mot pose
    		
    		return true;
    	}
    	else
    	{
    		for(Piece p : essaiMot) // libere les cases 
    		{
    			Case c = p.getCasePiece();
    			p.libererPiece();
    			c.libererCase();
    		}

    		return false;
    	}
    }
    
    public boolean checkPremierMot(boolean premierMot)
    {
    	boolean flag = false;
    	if(!(premierMot))
    	{
    		return true;
    	}
    	else
    	{
    		for(Piece p : essaiMot)
    		{
    			if(p.getCasePiece().getX() == 7 && p.getCasePiece().getY() == 7)
    			{
    				flag = true;
    			}
    		}
    	}
    	return flag;
    }


    public boolean motValide(Case[][] board, Dictionnaire dico) {
        Piece firstPiece = essaiMot.get(0); // Premiere piece posee
        Piece lastPiece = essaiMot.get(essaiMot.size() - 1); // Derniere piece posee
        int firstX = firstPiece.getCasePiece().getX();
        int lastX = lastPiece.getCasePiece().getX();
        int firstY = firstPiece.getCasePiece().getY();
        int lastY = lastPiece.getCasePiece().getY();

        boolean motValide = true; //flag
        String mot = "";

        if (firstX != lastX) // Mot horizontal
        {
            int[] indices = indicesMotComplet(board, true, firstX, lastX, firstY);
            int jMin = indices[0];
            int jMax = indices[1];

            mot = construireMot(piecesMotHorizontal(board, firstY, jMin, jMax));
            if (!(existe(mot, dico))) {
                return false; // si le mot construit horizontalement n'existe pas
            }

            for (int x = jMin; x <= jMax; x++) {
                if (!(testMotVertical(dico, board, x, firstY))) {
                    return false; // si, pour chaque piece horizontale qui complete un mot vertical, un des mots n'existe pas
                }
            }


        } else if (firstY != lastY) // Mot vertical
        {
            int[] indices = indicesMotComplet(board, false, firstY, lastY, firstX);
            int iMin = indices[0];
            int iMax = indices[1];

            mot = construireMot(piecesMotVertical(board, firstX, iMin, iMax));
            if (!(existe(mot, dico))) {
                return false; // si le mot construit horizontalement n'existe pas
            }

            for (int y = iMin; y <= iMax; y++) {
                if (!(testMotHorizontal(dico, board, y, firstX))) {
                    return false; // si, pour chaque piece horizontale qui complete un mot vertical, un des mots n'existe pas
                }
            }

        } else // une seule lettre a ete posee
        {
            int[] indicesHor = indicesMotComplet(board, true, firstX, lastX, firstY);
            int[] indicesVer = indicesMotComplet(board, false, firstY, lastY, firstX);

            if (indicesHor[0] != indicesHor[1]) // si un mot s'est construit horizontalement a la pose de la piece
            {
                mot = construireMot(piecesMotHorizontal(board, firstY, indicesHor[0], indicesHor[1]));
                if (!(existe(mot, dico))) {
                    return false; // si la piece posee complete un mot horizontal qui n'existe pas
                }
            }

            if (indicesVer[0] != indicesVer[1]) // si un mot s'est construit verticalement a la pose de la piece
            {
                mot = construireMot(piecesMotVertical(board, firstX, indicesVer[0], indicesVer[1]));
                if (!(existe(mot, dico))) {
                    return false; // si la piece posee complete un mot vertical qui n'existe pas
                }
            }

        }
        System.out.println(mot);

        return motValide; // Sinon le mot est valide
    }

    private int[] indicesMotComplet(Case[][] board, boolean horizontal, int min, int max, int y) {
        int newMin = min;
        int newMax = max;
        int[] indices = new int[2];

        if (horizontal) {
            while (newMin > 0 && !(board[y][newMin - 1].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
            {
                newMin -= 1;
            }
            while (newMax < 14 && !(board[y][newMax + 1].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
            {
                newMax += 1;
            }
        } else {
            while (newMin > 0 && !(board[newMin - 1][y].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
            {
                newMin -= 1;
            }
            while (newMax < 14 && !(board[newMax + 1][y].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
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

        while (iMin > 0 && !(board[iMin - 1][x].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
        {
            iMin -= 1;
        }
        while (iMax < 14 && !(board[iMax + 1][x].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
        {
            iMax += 1;
        }

        if (iMin == iMax) // une seule lettre a la verticale pour un mot construit horizontalement -> OK
        {
            return true;
        } else {
            mot = construireMot(piecesMotVertical(board, x, iMin, iMax));
            return existe(mot, dico);
        }
    }

    private boolean testMotHorizontal(Dictionnaire dico, Case[][] board, int y, int x) {
        String mot;

        int jMin = x;
        int jMax = x;

        while (jMin > 0 && !(board[y][jMin - 1].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
        {
            jMin -= 1;
        }
        while (jMax < 14 && !(board[y][jMax + 1].estLibre())) // tant que les cases avant la premiere piece posee ne sont pas libres
        {
            jMax += 1;
        }

        if (jMin == jMax) // une seule lettre a la verticale pour un mot construit horizontalement -> OK
        {
            return true;
        } else {
            mot = construireMot(piecesMotHorizontal(board, x, jMin, jMax));
            return existe(mot, dico);
        }
    }

    public ArrayList<Piece> piecesMotHorizontal(Case[][] board, int y, int xMin, int xMax) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();

        for (int j = xMin; j <= xMax; j++) {
            pieces.add(board[y][j].getPiece());
        }

        return pieces;
    }

    public String construireMot(ArrayList<Piece> pieces) {
        String mot = "";

        for (Piece p : pieces) {
            mot += p.getLettre();
        }
        return mot;
    }

    public ArrayList<Piece> piecesMotVertical(Case[][] board, int x, int yMin, int yMax) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();

        for (int i = yMin; i <= yMax; i++) {
            pieces.add(board[i][x].getPiece());
        }

        return pieces;
    }

    public boolean existe(String mot, Dictionnaire dico) {
        CharSequence joker = "?";

        if (mot.contains(joker)) {
            int index = mot.indexOf('?');
            String prefixe = mot.substring(0, index);
            String suffixe = mot.substring(index + 1, mot.length());

            return dico.motExistant(prefixe, suffixe, index, mot.length());
        } else {
            return dico.motExistant(mot);
        }
    }

    public int compterPoints(Case[][] board) {  // fonction qui compte les points du nouveau mot
        int nbPP = 0;            // points des lettres deja existantes
        int nbPG = 0;            // points du mot principal ( lettres posées )
        int cmpLettre = 0;
        int coefMultG = 1;
        int coefMultP = 1;
        int verOuHor; //1 = mot d'essai en horizontal / 2 = vertical / 0 = une lettre inseree;
        if (essaiMot.size() == 1)                        // test si il n'y a qu'une lettre de posée
            verOuHor = 0;
        else if (essaiMot.get(0).getCasePiece().getX() == essaiMot.get(1).getCasePiece().getX()) { // test si le mot est horizontal
            verOuHor = 1;
            nbPG += pointsLettresDejaPresententHorizontal(board, essaiMot.get(0).getCasePiece().getX());

        } else {        // le mot est vertical
            verOuHor = 2;
            nbPG = pointsLettresDejaPresententVertical(board, essaiMot.get(0).getCasePiece().getY());
        }
        while (cmpLettre < essaiMot.size()) {
            if (essaiMot.get(cmpLettre).getCasePiece().getBonus().equals(BonusCase.LD)) {
                nbPG += essaiMot.get(cmpLettre).getValue() * 2;
            } else if (essaiMot.get(cmpLettre).getCasePiece().getBonus().equals(BonusCase.LT)) {
                nbPG += essaiMot.get(cmpLettre).getValue() * 3;
            } else if (essaiMot.get(cmpLettre).getCasePiece().getBonus().equals(BonusCase.MD)) {
                nbPG += essaiMot.get(cmpLettre).getValue();
                coefMultG *= 2;
                coefMultP = 2;
            } else if (essaiMot.get(cmpLettre).getCasePiece().getBonus().equals(BonusCase.MT)) {
                nbPG += essaiMot.get(cmpLettre).getValue();
                coefMultG *= 3;
                coefMultP = 3;
            } else {
                nbPG += essaiMot.get(cmpLettre).getValue();
            }
            nbPP += pointsPetitMot(cmpLettre, board, verOuHor) * coefMultP; // calcul des points des lettres posées auparavant
            coefMultP = 1;
            cmpLettre += 1;
        }
        nbPG *= coefMultG;
        if (essaiMot.size() == 7)   // bonus si toutes les lettres sont posées
            nbPG += 50;
        return nbPG + nbPP;

    }

    public int pointsPetitMot(int cmpLettre, Case[][] board, int versOuHor) {  // appel des fonction qui comptent les points selon la direction du mot
        if (versOuHor == 0) {
            return motPoseAuparavantHorizontal(cmpLettre, board) + motPoseAuparavantVertical(cmpLettre, board);
        } else if (versOuHor == 1) {
            return motPoseAuparavantVertical(cmpLettre, board);
        } else {
            return motPoseAuparavantHorizontal(cmpLettre, board);
        }
    }

    public int motPoseAuparavantHorizontal(int cmpLettre, Case[][] board) {   // comptage des mots déja posés
        int nbP = 0;
        int xp = essaiMot.get(cmpLettre).getCasePiece().getY() + 1;
        int xm = essaiMot.get(cmpLettre).getCasePiece().getY() - 1;
        int y = essaiMot.get(cmpLettre).getCasePiece().getX();
        Case casePlus = board[xp][y];
        Case caseMoins = board[xm][y];
        while (!casePlus.estLibre()) {
            nbP += casePlus.getPiece().getValue();
            casePlus = board[++xp][y];
        }
        while (!caseMoins.estLibre()) {
            nbP += caseMoins.getPiece().getValue();
            caseMoins = board[--xm][y];
            //System.out.println(xm);
        }
        return nbP;
    }

    public int motPoseAuparavantVertical(int cmpLettre, Case[][] board) {    // comptage des mots déja posés
        int nbP = 0;
        int x = essaiMot.get(cmpLettre).getCasePiece().getY();
        int yp = essaiMot.get(cmpLettre).getCasePiece().getX() + 1;
        int ym = essaiMot.get(cmpLettre).getCasePiece().getX() - 1;
        Case casePlus = board[x][yp];
        Case caseMoins = board[x][ym];
        while (!casePlus.estLibre()) {
            nbP += casePlus.getPiece().getValue();
            casePlus = board[x][++yp];
        }
        while (!caseMoins.estLibre()) {
            nbP += caseMoins.getPiece().getValue();
            caseMoins = board[x][--ym];
        }
        return nbP;
    }

    public int pointsLettresDejaPresententHorizontal(Case[][] board, int ligne) {   // comptage des lettres déja posées ( meme direction que le mot principal )
        int i = 0;
        int point = 0;
        int[] tab = new int[essaiMot.size()];
        for (int j = 0; j < essaiMot.size(); j++) {
            tab[j] = essaiMot.get(j).getCasePiece().getY();
            //System.out.println(essaiMot.get(j).getCasePiece().getY());
        }
        int yM = essaiMot.get(0).getCasePiece().getY() - 1;
        int yP = essaiMot.get(0).getCasePiece().getY() + 1;

        while (!board[yM][ligne].estLibre() || contain(yM, tab)) {
            if (!board[yM][ligne].estLibre() && !contain(yM, tab)) {
                //System.out.println(board[ligne][yM].getPiece().getValue());
                point += board[yM][ligne].getPiece().getValue();
            }
            yM--;
        }

        while (!board[yP][ligne].estLibre() || contain(yP, tab)) {
            if (!board[yP][ligne].estLibre() && !contain(yP, tab)) {
                point += board[yP][ligne].getPiece().getValue();
            }
            yP++;
        }
        return point;

    }

    public int pointsLettresDejaPresententVertical(Case[][] board, int ligne) {        // comptage des lettres déja posées ( meme direction que le mot principal )
        int i = 0;
        int point = 0;
        int[] tab = new int[essaiMot.size()];
        for (int j = 0; j < essaiMot.size(); j++) {
            tab[j] = essaiMot.get(j).getCasePiece().getX();
        }
        int xM = essaiMot.get(0).getCasePiece().getX() - 1;
        int xP = essaiMot.get(0).getCasePiece().getX() + 1;
        while (!board[ligne][xM].estLibre() || contain(xM, tab)) {
            if (!board[ligne][xM].estLibre() && contain(xM, tab))
                point += board[ligne][xM].getPiece().getValue();
            xM--;
        }
        while (!board[ligne][xP].estLibre() || contain(xP, tab)) {
            if (!board[ligne][xP].estLibre() && !contain(xP, tab))
                point += board[ligne][xP].getPiece().getValue();
            xP++;
        }
        return point;

    }

    public void swap(int i, int j) {
        Collections.swap(main, i, j);
    }

    public boolean contain(int valeur, int[] tab) {  // teste si la lettre est déja posée
        for (int i = 0; i < tab.length; i++) {
            //System.out.println(tab[i]);
            if (tab[i] == valeur) {
                return true;
            }
        }
        return false;
    }

    public void melanger() // melange la main
    {
        Collections.shuffle(this.main);
    }


    public void echanger(ArrayList<Piece> lettres, Pioche pioche) { // pioche des lettres
        for (int i = 0; i < lettres.size(); i++) {
            for (int j = 0; j < main.size(); j++) {
                if (lettres.get(i).getLettre() == main.get(j).getLettre()) {
                    i++;
                    pioche.addInBag(main.get(j));
                    main.set(j, pioche.takeLetterInBag(1).get(0));
                }
            }
        }
    }




    public ArrayList<Case> trouverLettre(char c, Case[][] board) {
        ArrayList<Case> cases = new ArrayList<Case>();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (!(board[i][j].estLibre()) && board[i][j].getPiece().getLettre() == c) {
                    cases.add(board[i][j]);
                }
            }
        }

        return cases;
    }


    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        nbPointsProperty = new SimpleIntegerProperty(nbPoints);
        pseudoProperty = new SimpleStringProperty(pseudo);
        essaiMot = FXCollections.observableArrayList();
    }
    //endregion

    //region Getters Setters

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    // Retourne du nom du joueur
    public String getNom() {
        return this.pseudo;
    }

    // Retourne du nombre de points du joueur
    public int getNbPoints() {
        return this.nbPoints;
    }

    // Retourne de la main du joueur
    public ArrayList<Piece> getMain() {
        return this.main;
    }

    public IntegerProperty nbPointsProperty() {
        return this.nbPointsProperty;
    }

    public void setNbPointsProperty(int nbPointsProperty) {
        this.nbPointsProperty.set(nbPointsProperty);
    }

    public int getNbPointsProperty() {
        return nbPointsProperty.get();
    }

    public boolean isIA() {
        return IA;
    }

    public String getPseudo() {
        return pseudo;
    }

    // Modifie les pseudo du joueur
    public void setPseudo(String newPseudo) {
        this.pseudo = newPseudo;
        this.setPseudoProperty(newPseudo);
    }

    public String getPseudoProperty() {
        return pseudoProperty.get();
    }

    public StringProperty pseudoPropertyProperty() {
        return pseudoProperty;
    }

    public void setPseudoProperty(String pseudoProperty) {
        this.pseudoProperty.set(pseudoProperty);
    }

    public boolean possede(Piece p) {
        return this.main.contains(p);
    }

    public ObservableList<Piece> getEssaiMot() {
        return essaiMot;
    }

    public void setEssaiMot(ObservableList<Piece> essaiMot) {
        this.essaiMot = essaiMot;
    }
    
    public boolean getIA()
    {
    	return this.IA;
    }
    
    public void setIA(boolean ia)
    {
    	this.IA = ia;
    }



    //endregion


}
