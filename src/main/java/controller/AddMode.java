package controller;

import abstraction.BoxesModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class AddMode extends Mode{
    public AddMode(BoxesModel boxesModel, AnchorPane picture) {
        super(boxesModel,picture);
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {

    }

    @Override
    protected void onMousePressed(MouseEvent e) { //TODO : Verify the currentImageID value & set it right.
        Point2D position = new Point2D(e.getX(),e.getY());
        boxesModel.addBox(currentImageID.get(),position,position,1); // I puted random bird ID
        Rectangle toDraw = boxesModel.getRectangleBox(currentImageID.get());
        picture.getChildren().add(toDraw);
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        boxesModel.editBox(currentImageID.get(),new Point2D(e.getX(),e.getY()));
        Rectangle toDrawn = boxesModel.getRectangleBox(currentImageID.get());
        picture.getChildren().set(0,toDrawn);
    }
}
