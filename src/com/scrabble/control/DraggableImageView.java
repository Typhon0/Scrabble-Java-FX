package com.scrabble.control;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Draggable image view
 */
public class DraggableImageView extends ImageView {
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    AnchorPane board;
    Bounds boardBounds;

    public DraggableImageView(Image image, AnchorPane board){
        super(image);
        this.board = board;
        this.setOnMousePressed(ImageViewOnMousePressedEventHandler);
        this.setOnMouseDragged(ImageViewOnMouseDraggedEventHandler);
        this.setOnMouseEntered(ImageViewOnMouseEnteredHandler);
        this.setOnMouseExited(ImageViewOnMouseExitedHandler);
        this.setOnMouseReleased(ImageViewOnMouseReleasedHandler);
    }

    private void updateBoardBounds(){
        boardBounds = board.localToScene(board.getBoundsInLocal());
    }
    //click enfonce
    EventHandler<MouseEvent> ImageViewOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((ImageView)(t.getSource())).getTranslateX();
                    orgTranslateY = ((ImageView)(t.getSource())).getTranslateY();
                }
            };
    //click enfonce et mouvement souris
    EventHandler<MouseEvent> ImageViewOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    setCursor(Cursor.CLOSED_HAND);
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((ImageView)(t.getSource())).setTranslateX(newTranslateX);
                    ((ImageView)(t.getSource())).setTranslateY(newTranslateY);
                }
            };
    //curseur de souris sur la node
    EventHandler<MouseEvent> ImageViewOnMouseEnteredHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCursor(Cursor.HAND);
                    updateBoardBounds();
                }
            };
    //curseur de souris en dehors de la node
    EventHandler<MouseEvent> ImageViewOnMouseExitedHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCursor(Cursor.DEFAULT);
                }
            };
    //click souris relache
    EventHandler<MouseEvent> ImageViewOnMouseReleasedHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setCursor(Cursor.HAND);
                    double X = event.getSceneX();
                    double Y = event.getSceneY();

                    //in board
                    if(X > boardBounds.getMinX() && X < boardBounds.getMaxX() && Y > boardBounds.getMinY() && Y < boardBounds.getMaxY()){
                        GridPane grille = (GridPane) board.getChildren().get(0);
                        int caseX, caseY;
                        double sizeH, sizeW;
                        sizeH = (boardBounds.getMaxY()-(boardBounds.getMinY()+5))/15;
                        sizeW = (boardBounds.getMaxX()-(boardBounds.getMinX()+5))/15;
                        X = X-boardBounds.getMinX()+5;
                        Y = Y-boardBounds.getMinY()+5;
                        caseX = (int) Math.round(X/sizeW);
                        caseY = (int) Math.round(Y/sizeH);
                        caseX--;
                        caseY--;

                        System.out.println(caseX + " " + caseY);

                        StackPane sp = (StackPane) grille.getChildren().get(caseX*15 + caseY%15);
                        DraggableImageView div = (DraggableImageView) event.getSource();
                        Image img = new Image(div.getImage().getUrl(),sizeH,sizeW,true,true);
                        sp.getChildren().clear();
                        sp.getChildren().add(new ImageView(img));
                        HBox mainJoueur = (HBox) div.getParent();
                        mainJoueur.getChildren().removeAll(div);

                        //TODO link with model

                    }else{
                        setTranslateX(0.0);
                        setTranslateY(0.0);
                    }
                }
            };
}