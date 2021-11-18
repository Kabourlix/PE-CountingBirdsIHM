package controller;

import abstraction.BoxesModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/***
 * This mode is made for selecting Boxes on the image.
 */
public class SelectionMode extends Mode {
    public SelectionMode(BoxesModel boxesModel) {
        super(boxesModel);
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        Point2D position = new Point2D(e.getX(),e.getY());
        boxesModel.getBox(currentImageID.get(),position);
    }

    @Override
    protected void onMousePressed(MouseEvent e) {

    }

    @Override
    protected void onMouseDragged(MouseEvent e) {

    }
}
