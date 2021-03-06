package com.scrabble.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;


public class IA extends Joueur implements Serializable{
    //Niveau de l'IA
    private IADifficulties level;

    // Constructeur de l'IA
    public IA(IADifficulties level, Pioche pioche){
        super("IA", pioche);
        this.setIA(true);
        this.level = level;
    }
    
    // Renvoit l'enumeration de la difficulte
    public IADifficulties getLevel(){
        return this.level;
    }

    // Modificateur du niveau
    public void setLevel(IADifficulties newLevel){
        this.level = newLevel;
    }

    // Methode qui renvoit un tableau de caracteres
    public ArrayList<Character> letters() {
    	ArrayList<Piece> pieces = this.getMain();
        ArrayList<Character> IALetters = new ArrayList<Character>();
        Iterator<Piece> iter = pieces.iterator();
        while (iter.hasNext()) {
            Piece p = iter.next();
            IALetters.add(p.getLettre());
        }
        return IALetters;
    }

    
    /* Methode qui recherche tous les mots qui peuvent etre ajoutes par l'IA sur le plateau*/
    public boolean findAllWord (ArrayList<Piece> pieces, ArrayList<String> str, Case[][] board, Scrabble scrab){
    	ArrayList<String> str2 = this.researchWordContainsLetter(str, level);
    	Iterator<String> iter = str2.iterator();
		int caseX;
		int caseY;
		boolean encore = true;
		
		// iterateur sur la liste des mots trouves possedants une lettre en plus
        while (iter.hasNext() && encore) {
        	ArrayList<Character> chars = this.letters();
            String word = iter.next();
            Map<Integer, Piece> map = this.positionLetterWord(word, chars);

	        		// Recherche la lettre manquante dans le mot
	        		for (int i = 0; i< word.length() && encore; i++) {
	        			if (map.get(i)== null) {
	        				// Trouver les positions d'une lettre sur le plateau
	        				ArrayList<Case> cases = this.trouverLettre(word.charAt(i), board);
	        				// Iterateur sur les cases correspondant a un caractere
	        				Iterator<Case> iterC = cases.iterator();
	        				// 

	        				while (iterC.hasNext() && encore) {
	        					Case c = iterC.next();
	        					// Position en x et y de la case possedant la lettre
	        					caseX = c.getX();
	        					caseY = c.getY();
	        					// Si le mot est placable en y
	        						if(estPlacable(word, caseY, caseX, i, board, 'Y', map.get(i))) {
	        							this.placerMot(word, i, map.get(i), c, 'Y', map, board);
	        							encore=false;
	        							
	        					// si le mot est placable en x
	        						} else if(estPlacable(word, caseY, caseX, i, board, 'X', map.get(i))) {
		        						this.placerMot(word, i, map.get(i), c, 'X', map, board);
	        							encore=false;
	        						}
	        					}
	        				}
	        			}
	        		}
    
        if (encore==true) {
        	System.out.println("Aucun resultat trouve pour ce niveau");
        	this.echanger(this.getMain(), scrab.getPioche());
        }
        aJoue = true;
        return encore;
    }
    
    // Renvoit true si le mot est placable
    public boolean estPlacable (String word, int caseY, int caseX, int indice, Case[][] board, char axe, Piece p) {

    	boolean res = true;
	    	Case c;
	    	// Si sur l'axe de Y
	    	if(axe == 'Y') {
		    	for(int i = 0; i<word.length(); i++) {
		    		// Protection contre un index en dehors du plateau
		    		if( ((caseX + i) - indice) < 15 && (caseX + i) - indice >= 0) {
			    		c = board[caseY][(caseX + i) - indice];
			    		if (c.estLibre() || c.getPiece().getLettre() == word.charAt(i)) {
			    			res = res && true;
			    		} else {
			    			res = false;
			    		}
		    		} else {
		    			res = false;
		    		}
		    	}
		    // Si sur l'axe X
	    	} else if(axe == 'X'){
	    		for(int i = 0; i<word.length(); i++) {
		    		// Protection contre un index en dehors du plateau
	    			if(((caseY + i) - indice) < 15 && (caseY + i) - indice >= 0) {
			    		c = board[(caseY + i) - indice][caseX];
			    		if (c.estLibre() || c.getPiece().getLettre() == word.charAt(i)) {
			    			res = res && true;
			    		} else {
			    			res=false;
			    		}
	    			} else {
		    			res = false;
		    		}
		    	}
	    	} else {
	    		res = false;
	    	}
	    	return res;
    }
    
    // Fonction qui place le mot selon certaines informations comme la position de la case possedant la lettre manquante
    public boolean placerMot(String word, int i, Piece caractere, Case casePos, char axe, Map<Integer, Piece> map, Case[][] board) {
    	boolean succes = false;
    	Case c;
    	switch(axe) {
    	case 'X' :
    		for (int j = 0; j< word.length(); j++) {
    			if((((casePos.getY() - i) + j) < 15) || (((casePos.getX() - i) + j) >= 0)) {
	    			c = board[(casePos.getY() - i) + j][casePos.getX()];

	    			if(c.estLibre()) {
	    				poserUnePiece(map.get(j), c);
	                    this.essaiMot.add(map.get(j));
	    			}
    			}
    		}
    		succes = true;
    	break;
    	case 'Y' :
    		for (int j = 0; j< word.length(); j++) {
    			if( (((casePos.getX() - i) + j) < 15) || (((casePos.getX() - i) + j) >= 0)) {
	    			c = board[casePos.getY()][(casePos.getX() - i) + j];
	    			if(c.estLibre()) {
		    			poserUnePiece(map.get(j), c);

	                    this.essaiMot.add(map.get(j));
	    			}
    			}
    		}
    		succes = true;
    	break;
    	
    	}
    	return succes;
    }

    public ArrayList<String> researchWordContainsLetter (ArrayList<String> s, IADifficulties level) {
    	int ecart = 1;
    	// Creation des differents tableaux selon les tailles
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> res1 = new ArrayList<String>();
        ArrayList<String> res2 = new ArrayList<String>();
        ArrayList<String> res3 = new ArrayList<String>();
        ArrayList<String> res4 = new ArrayList<String>();
        ArrayList<String> res5 = new ArrayList<String>();
        ArrayList<String> res6 = new ArrayList<String>();
        ArrayList<String> resD = new ArrayList<String>();
        Iterator<String> iter = s.iterator();
        while (iter.hasNext()) {
        	ArrayList<Character> c = this.letters();
            String str = iter.next();
            int ol = otherLetterNumber(str, c);
            if (ol > str.length() - (ecart +1)) {
            	// Separe les mots obtenus selon leurs tailles
            	switch(str.length()) {
	            	case 1 :
	            		res1.add(str);
	            	break;
	            	case 2 :
	            		res2.add(str);
	            	break;
	            	case 3 :
	            		res3.add(str);
	            	break;
	            	case 4 :
	            		res4.add(str);
	            	break;
	            	case 5 :
	            		res5.add(str);
	            	break;
	            	case 6 :
	            		res6.add(str);
	            	break;
	            	default :
	            		resD.add(str);
	            	break;
            	}
            }
        }
        
        // Liste triee selon la difficulte
        if (level==IADifficulties.NORMAL) {res.addAll(resD);
	    	res.addAll(res3);
	    	res.addAll(res2);
	    	res.addAll(res1);
	    	res.addAll(res4);
	    	res.addAll(res5);
	    	res.addAll(res6);
	    	res.addAll(resD);
    	return res;
        } else if(level==IADifficulties.HARD){
        	res.addAll(resD);
        	res.addAll(res6);
        	res.addAll(res5);
        	res.addAll(res4);
        	res.addAll(res3);
        	res.addAll(res2);
        	res.addAll(res1);
        	return res;
        }
        else {
        	res.addAll(res1);
        	res.addAll(res2);
        	res.addAll(res3);
        	res.addAll(res4);
        	res.addAll(res5);
        	res.addAll(res6);
        	res.addAll(resD);
        	return res;
        }
    }


    // Retourne le nombre de lettre restante dans le mot
    public int otherLetterNumber (String word, ArrayList<Character> pieces){
    	int count = 0;
    	ArrayList<Character> p = new ArrayList<Character>();
    	int taille = word.length();
    	ArrayList<Character> pieces2 = pieces;
    	for(int i = 0; i<taille; i++) {
    		if(!pieces2.contains((Character) word.charAt(i))) {
    			p.add((Character) word.charAt(i));
    			count++;
    		} else {
    			pieces2.remove((Character) word.charAt(i));
    		}
    	}
		return (taille - count);
	}
    
    // Cree une liste des caracteres qui sont dans le mot, mais qui ne sont pas dans la main
    public ArrayList<Character> otherLetter (String word, ArrayList<Character> pieces){
    	ArrayList<Character> p = new ArrayList<Character>();
    	int taille = word.length();
    	ArrayList<Character> pieces2 = pieces;
    	if(pieces2.contains('?')) {
    		pieces2.remove((Character) '?');
    	}
    	for(int i = 0; i<taille; i++) {
    		if(!pieces2.contains((Character) word.charAt(i))) {
    			p.add((Character) word.charAt(i));
    		} else {
    			pieces2.remove((Character) word.charAt(i));
    		}
    	}
		return (p);
	}
    
    public Map<Integer, Piece> positionLetterWord(String word, ArrayList<Character> chars) {
    	Map<Integer, Piece> res = new HashMap<>();
    	for(int i = 0; i<word.length(); i++) {
    		if(chars.contains((Character) word.charAt(i))) {
    			for(int j = 0; j< this.getMain().size(); j++) {
    				if(this.getMain().get(j).getLettre() == word.charAt(i)) {
    	    			res.put(i, this.getMain().get(j));
    				}
    			}
    		}
    	}
    	return res;
    }
    
    // Renverse la liste obtenue en resultat, fonction non utilisee
    public ArrayList<String> reverse(ArrayList<String> str){
		ArrayList<String> result = new ArrayList<String>();
		for(int i=str.size()-1; i>=0; i--)
		    result.add(str.get(i));
		return result;
    }
    
    @Override 
    public boolean jouerMot(Scrabble scrab){
    	// Recherche les mots qui contiennent les lettres de l'ia
    	ArrayList<String> str = this.researchWordContainsLetter(scrab.getDico().getDico(), IADifficulties.EASY);
    	// Faux si un mot a ete trouve
    	boolean res = findAllWord (this.getMain(), str, scrab.getBoard(), scrab);
    	if(res==false && this.motValide(scrab.getBoard(), scrab.getDico())) {
    		this.addNbPoints(compterPoints(scrab.getBoard())); // ajoute les points
    		retirerLettresDuMot(); // retire les lettres posees de la main du joueur
    		piocher(scrab.getPioche());
    		viderEssaiMot(); // vide la tentative de mot pose
    	}
		aJoue = true;
    	return true;
    }

	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();

	}
}