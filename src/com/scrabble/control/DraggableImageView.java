package com.scrabble.control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;

import java.io.IOException;

/**
 * Draggable image view
 */
public class DraggableImageView extends ImageView {
    private double mouseX ;
    private double mouseY ;
    private double lastPosX ;
    private double lastPosY ;

    public DraggableImageView()
    {
        super();

        setOnMousePressed(event -> {
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
            lastPosX = getLayoutX();
            lastPosY=getLayoutY();
            this.setCursor(Cursor.HAND);
        });

        setOnMouseDragged(event -> {

            double deltaX = event.getSceneX() - mouseX ;
            double deltaY = event.getSceneY() - mouseY ;
            /*System.out.println(deltaX + "Delta Y :" + deltaY);
            System.out.println("Layout X  " + getLayoutX() + " Layout Y" + getLayoutY());
            System.out.println("last Layout X  " + lastPosX + " last Layout Y" + lastPosY);*/

                relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);



                mouseX = event.getSceneX();
                mouseY = event.getSceneY();

        });



        setOnMouseReleased(event -> {
            if((getLayoutX() < 0 || getLayoutY() < 0) || (getLayoutX() > getScene().getHeight() || getLayoutY() > getScene().getWidth())) {

                relocate(273, 468);
            }
        });

    }
    public DraggableImageView(Image image) {
        super(image);

        setOnMousePressed(event -> {
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
        });

        setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX ;
            double deltaY = event.getSceneY() - mouseY ;
            relocate(getLayoutX() + deltaX, getLayoutY() + deltaY);
            mouseX = event.getSceneX() ;
            mouseY = event.getSceneY() ;
        });
    }
}