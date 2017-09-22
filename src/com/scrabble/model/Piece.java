/**
 * @author Remi Lenoir
 */
package com.scrabble.model;

public class Piece {
	
	private char lettre;
	private int value;
	/**
	 * @param lettre is the character of the piece
	 * @param value is the number of point of the letter
	 */
	public Piece(char lettre, int value) {
		this.lettre = lettre;
		this.value = value;
	}
	public char getLettre() {
		return lettre;
	}
	public void setLettre(char lettre) {
		this.lettre = lettre;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
