package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by loic on 18/09/2017.
 */
public class Scrabble {

    private Case[][] board;

    public Scrabble() {
        board = new Case[15][15];
        initTab();
    }

    public void initTab() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Case(new ImageView(new Image("assets/textures/case.png")));
            }
        }


    }

    public Case[][] getBoard() {
        return board;
    }

    public void setBoard(Case[][] board) {
        this.board = board;
    }
}
