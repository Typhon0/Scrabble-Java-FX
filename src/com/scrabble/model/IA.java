package com.scrabble.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;


public class IA extends Joueur{
    //Niveau de l'IA
    private IADifficulties level;


    public IA(IADifficulties level, Pioche pioche){
        super("IA", pioche);
        this.setIA(true);
        this.level = level;
    }

    public IADifficulties getLevel(){
        return this.level;
    }

    public void setLevel(IADifficulties newLevel){
        this.level = newLevel;
    }

    /* Méthode qui renvoit un tableau de caractères */
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

    
    /* Méthode qui recherche tous les mots qui peuvent être ajoutés par l'IA sur le plateau*/
    public void findAllWord (ArrayList<Piece> pieces, ArrayList<String> str, Case[][] board){
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
            // Switch case sur le niveau du jour 
//	        switch (this.level){
//	        case 1:
//	        	if (chars.contains('?')){
//	        		
//	        	} else {

	        		// Recherche la lettre manquante dans le mot
	        		for (int i = 0; i< word.length(); i++) {
	        			if (map.get(i)!= null) {
	        				// Trouver les positions d'une lettre sur le plateau
	        				ArrayList<Case> cases = this.trouverLettre(map.get(i).getLettre(), board);
	        				// Iterateur sur les cases correspondant a un caractere
	        				Iterator<Case> iterC = cases.iterator();
	        				// 

	        				while (iterC.hasNext() && encore) {
	        					Case c = iterC.next();
	        					// Position en x et y de la case possedant la lettre
	        					caseX = c.getX();
	        					caseY = c.getY();
	        					// Si le mot est placable en y

	        					if ((i+caseY >= 0) && ((word.length() - i + caseY) <= 15)) {
		        					System.out.println("-------------------------");
	        						if(estPlacable(word, caseY, caseX, i, board, 'Y')) {
	        							System.out.println(word);
	        							this.placerMot(word, i, map.get(i), c, 'Y', map, board);
	        							encore=false;
	        						}
	        					// si le mot est placable en x
	        					} else if((i + caseX >= 0) && (((word.length() - i) + caseX) <= 15)) {

		        					System.out.println("-------------------------");
	        						if(estPlacable(word, caseY, caseX, i, board, 'X')) {
	        							System.out.println(word);
		        						this.placerMot(word, i, map.get(i), c, 'X', map, board);
		        						System.out.println(word);
	        							encore=false;
	        						}
	        					}
	        				}
	        			}
	        		}
//	        	}
//	        break;
//	        case 2:
//	        	break;
//	        }
        }
        if (encore==true) {
        	System.out.println("Aucun resultat trouve pour ce niveau");
        }
        aJoue = true;
    }
    
    public boolean estPlacable (String word, int caseY, int caseX, int indice, Case[][] board, char axe) {
    	boolean res = true;
    	Case c;
    	if(axe == 'Y') {
	    	for(int i = 0; i<word.length(); i++) {
	    		c = board[caseY][(caseX + indice) - i];
	    		if (c.estLibre() || c.getPiece().getLettre() == word.charAt(i)) {
	    			res = res && true;
	    		} else {
	    			res=false;
	    		}
	    	}
    	} else {
    		for(int i = 0; i<word.length(); i++) {
	    		c = board[(caseY + indice) - i][caseX];
	    		if (c.estLibre() || c.getPiece().getLettre() == word.charAt(i)) {
	    			res = res && true;
	    		} else {
	    			res=false;
	    		}
	    	}
    	}
    	
    	return res;
    }
    
    public boolean placerMot(String word, int i, Piece caractere, Case casePos, char axe, Map<Integer, Piece> map, Case[][] board) {
    	boolean succes = false;
    	Case c;
    	switch(axe) {
    	case 'X' :
    		for (int j = 0; j< word.length(); j++) {
    			c = board[(casePos.getY() - i) + j][casePos.getX()];
    			if(c.getPiece().getLettre() != word.charAt(i))
    				poserUnePiece(map.get(j), c);
    			
    				c.setPiece(map.get(j));
    		}
    		succes = true;
    	break;
    	case 'Y' :
    		for (int j = 0; j< word.length(); j++) {
    			c = board[casePos.getY()][(casePos.getX() - i) + j];
    			poserUnePiece(map.get(j), c);
				c.setPiece(map.get(j));
    		}
    		succes = true;
    	break;
    	
    	}
    	return succes;
    }

    public ArrayList<String> researchWordContainsLetter (ArrayList<String> s, IADifficulties level) {
    	int ecart = 1;
        ArrayList<String> res = new ArrayList<String>();
//        ArrayList<String> res2 = new ArrayList<String>();
//        ArrayList<String> res6 = new ArrayList<String>();
        Iterator<String> iter = s.iterator();
        while (iter.hasNext()) {
        	ArrayList<Character> c = this.letters();
            String str = iter.next();
            int ol = otherLetterNumber(str, c);
            if (ol > str.length() - (ecart +1)) {
            	if (str.length() <=6) {
            		res.add(str);
            	} else {
            		res.add(str);
            	}
            }
        }
        if (level==IADifficulties.EASY) return res;
        else return (reverse(res));
    }


    // Retourne le nombre de lettre restante dans le mot
    public int otherLetterNumber (String word, ArrayList<Character> pieces){
    	int count = 0;
    	ArrayList<Character> p = new ArrayList<Character>();
    	int taille = word.length();
    	ArrayList<Character> pieces2 = pieces;
    	if(pieces2.contains('?')) {
    		count--;
    		pieces2.remove((Character) '?');
    	}
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
    
    public ArrayList<String> reverse(ArrayList<String> str){
		ArrayList<String> result = new ArrayList<String>();
		for(int i=str.size()-1; i>=0; i--)
		    result.add(str.get(i));
		return result;
    }
    
    @Override 
    public boolean jouerMot(Scrabble scrab){

    	System.out.println("-------------------------------------");
    	ArrayList<String> str = this.researchWordContainsLetter(scrab.getDico().getDico(), IADifficulties.EASY);
    	findAllWord (this.getMain(), str, scrab.getBoard());
    	
    	this.addNbPoints(compterPoints(scrab.getBoard())); // ajoute les points
		retirerLettresDuMot(); // retire les lettres posees de la main du joueur
		piocher(scrab.getPioche());
		viderEssaiMot(); // vide la tentative de mot pose
		aJoue = true;
    	System.out.println("-------------------------------------");
    	return true;
    }
}