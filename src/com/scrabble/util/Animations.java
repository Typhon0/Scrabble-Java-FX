package com.scrabble.util;

import com.scrabble.MainApp;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public final class Animations {


    /**
     * Slide in a node from left to the center of the scene
     * @param s
     * @param duration
     * @param FromX
     */
    public static void SlideInFromLeft(Node s, double duration, double FromX) {
        TranslateTransition tt = new TranslateTransition(new Duration(duration), s);
        tt.setFromX(-FromX);
        tt.setToX(0);
        tt.play();
    }

    /**
     * Slide out a node to left to X
     *
     * @param s
     * @param duration
     * @param ToX
     */
    public static void SlideOutToLeft(Node s,double duration,double ToX) {
        TranslateTransition tt = new TranslateTransition(new Duration(duration), s);
        tt.setToX(-ToX);
        tt.play();

    }


}
