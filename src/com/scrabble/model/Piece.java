/**
 * @author Remi Lenoir
 */
package com.scrabble.model;

public class Piece {
	// Lettre correspondant à la pièce
	private char lettre;
	// Valeur de la pièce
	private int value;
	/**
	 * @param lettre is the character of the piece
	 * @param value is the number of point of the letter
	 */
	public Piece(char lettre, int value) {
		this.lettre = lettre;
		this.value = value;
	}
	
	public Piece() // constructeur par defaut d'une piece Vide
	{
		this.lettre = '@';
		this.value = 0;
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
}
