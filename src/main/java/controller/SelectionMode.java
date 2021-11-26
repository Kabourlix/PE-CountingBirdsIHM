package controller;

import abstraction.BoxesModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/***
 * This mode is made for selecting Boxes on the image.
 */
public class SelectionMode extends Mode {
    public SelectionMode(BoxesModel boxesModel, AnchorPane picture) {
        super(boxesModel, picture);
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        Point2D position = new Point2D(e.getX(),e.getY());
        boxesModel.getBox(currentImageID.get(),position);
        System.out.println("The click is detected");
    }

    @Override
    protected void onMousePressed(MouseEvent e) {

    }

    @Override
    protected void onMouseDragged(MouseEvent e) {

    }

    public void drawBoxes(int imageID){
        Rectangle toDraw = boxesModel.getRectangleBox(imageID);
    }
}
