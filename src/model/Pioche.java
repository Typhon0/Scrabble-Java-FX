/**@author Remi Lenoir
 * */
package model;

import java.util.ArrayList;
import java.util.Collections;

public class Pioche {
	private ArrayList<Piece> bag;

	Pioche() {
		bag = new ArrayList<Piece>();
		addToBag('A', 1, 9);
		addToBag('B', 3, 2);
		addToBag('C', 3, 2);
		addToBag('D', 2, 3);
		addToBag('E', 1, 15);
		addToBag('F', 4, 2);
		addToBag('G', 2, 2);
		addToBag('H', 4, 2);
		addToBag('I', 1, 8);
		addToBag('J', 8, 1);
		addToBag('K', 10, 1);
		addToBag('L', 1, 5);
		addToBag('M', 2, 3);
		addToBag('N', 1, 6);
		addToBag('O', 1, 6);
		addToBag('P', 3, 2);
		addToBag('Q', 8, 1);
		addToBag('R', 1, 6);
		addToBag('S', 1, 6);
		addToBag('T', 1, 6);
		addToBag('U', 1, 6);
		addToBag('V', 4, 2);
		addToBag('W', 10, 1);
		addToBag('X', 10, 1);
		addToBag('Y', 10, 1);
		addToBag('Z', 10, 1);
		addToBag('?', 0, 2);
		Collections.shuffle(bag);
	}

	private void addToBag(char lettre, int val, int nb) {
		for (int i = 0; i < nb; i++) {
			bag.add(new Piece(lettre, val));
		}
	}

	public void addInBag(Piece p) {
		bag.add(p);
	}

	public void addInBag(ArrayList<Piece> remise) {
		for (Piece p : remise) {
			bag.add(p);
		}
	}

	public ArrayList<Piece> takeLetterInBag(int count) {
		if (count > bag.size()) {
			count = bag.size();
		}
		ArrayList<Piece> miniList = new ArrayList<Piece>();
		for (int i = 0; i < count; i++) {
			miniList.add(bag.get(0));
			bag.remove(0);
		}
		return miniList;
	}

	public ArrayList<Piece> getBag() {
		return bag;
	}
}
