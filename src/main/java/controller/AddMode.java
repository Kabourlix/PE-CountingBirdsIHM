package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class AddMode extends Mode{

    private BirdBox currentlyAddedBox;
    public AddMode(EnhancedBoxesModel boxesModel, AnchorPane picture) {
        super(boxesModel,picture);
        modeName = "add";
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {

    }

    @Override
    protected void onMousePressed(MouseEvent e) { //TODO : Verify the currentImageID value & set it right.
        Point2D position = new Point2D(e.getX(),e.getY());
       // System.out.println("We have the current data : " + currentImageID.get() + " and mouse position : " + position.toString());
        currentlyAddedBox = boxesModel.addBox(currentImageID.get(),position,position); // I puted random bird ID
        //new_rectangle = boxesModel.getRectangleBox(currentImageID.get());
        for (Shape a : currentlyAddedBox.getShapes()){
            picture.getChildren().add(a);
        }
        //picture.getChildren().add(new_rectangle);
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
