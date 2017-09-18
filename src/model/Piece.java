package model;

public class Piece {
	
	private char lettre;
	private int value;
	public Piece(char lettre, int value) {
		super();
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
