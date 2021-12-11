package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class AddMode extends Mode{

    private BirdBox currentlyAddedBox;
    public AddMode(EnhancedBoxesModel boxesModel, AnchorPane picture, IntegerProperty imageIdProp) {
        super(boxesModel,picture,imageIdProp);
        modeName = "add";
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {

    }

    @Override
    protected void onMousePressed(MouseEvent e) { //TODO : Verify the currentImageID value & set it right.
        System.out.println("Current image id from mode is " + currentImageID.get());
        Point2D position = new Point2D(e.getX(),e.getY());
        currentlyAddedBox = boxesModel.addBox(currentImageID.get(),position,position); // I puted random bird ID
        for (Shape a : currentlyAddedBox.getShapes()){
            picture.getChildren().add(a);
        }
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        currentlyAddedBox.editBox(new Point2D(e.getX(),e.getY()));
    }

    @Override
    protected void onModeChanged(String newMode) {
        // This does nothing
    }
}
