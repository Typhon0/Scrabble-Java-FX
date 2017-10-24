package com.scrabble.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public final class Animations {


    /**
     * Slide in a node from left to the center of the scene
     *
     * @param s
     * @param duration
     * @param FromX
     */
    public static void SlideInFromLeft(Node s, double duration, double FromX,double ToX) {
        TranslateTransition tt = new TranslateTransition(new Duration(duration), s);
        tt.setFromX(-FromX);
        tt.setToX(ToX);
        tt.play();
    }

    /**
     * Slide out a node to left to X
     *
     * @param s
     * @param duration
     * @param ToX
     */
    public static void SlideOutToLeft(Node s, double duration, double ToX) {
        TranslateTransition tt = new TranslateTransition(new Duration(duration), s);
        tt.setToX(-ToX);
        tt.play();


    }

    /**
     * Bounce In Transition of a node
     * @param node
     */
    public static void BounceInTransition(Node node) {
        Timeline t = new Timeline();
        t.setDelay(Duration.seconds(0.2));
        t.getKeyFrames().addAll(new KeyFrame(Duration.millis(0),
                        new KeyValue(node.opacityProperty(), 0),
                        new KeyValue(node.scaleXProperty(), 0.3),
                        new KeyValue(node.scaleYProperty(), 0.3)
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(node.opacityProperty(), 1),
                        new KeyValue(node.scaleXProperty(), 1.05),
                        new KeyValue(node.scaleYProperty(), 1.05)
                ),
                new KeyFrame(Duration.millis(700),
                        new KeyValue(node.scaleXProperty(), 0.9),
                        new KeyValue(node.scaleYProperty(), 0.9)
                ),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(node.scaleXProperty(), 1),
                        new KeyValue(node.scaleYProperty(), 1)
                )

        );


        //setCycleDuration(Duration.seconds(1));
        t.play();
    }

    /**
     * Bounce Out transition of a node and close parent if closeParent is true
     * @param node
     * @param closeParent
     */
    public static void BounceOutTransition(Node node,Boolean closeParent) {
        Timeline t = new Timeline();
        t.setDelay(Duration.seconds(0.2));
        t.getKeyFrames().addAll(new KeyFrame(Duration.millis(0),
                        new KeyValue(node.scaleXProperty(), 1),
                        new KeyValue(node.scaleYProperty(), 1)
                ),
                new KeyFrame(Duration.millis(250),
                        new KeyValue(node.scaleXProperty(), 0.95),
                        new KeyValue(node.scaleYProperty(), 0.95)
                ),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(node.opacityProperty(), 1),
                        new KeyValue(node.scaleXProperty(), 1.1),
                        new KeyValue(node.scaleYProperty(), 1.1)
                ),
                new KeyFrame(Duration.millis(1000),
                        new KeyValue(node.opacityProperty(), 0),
                        new KeyValue(node.scaleXProperty(), 0.3),
                        new KeyValue(node.scaleYProperty(), 0.3)
                )

        );


        //setCycleDuration(Duration.seconds(1));
        t.play();
        t.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(closeParent == true){
                    node.getParent().setVisible(false);
                }
                node.setVisible(false);
            }
        });
    }

}
