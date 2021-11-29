package controller;

import abstraction.BoxesModel;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class AddMode extends Mode{

    private Rectangle new_rectangle;
    public AddMode(BoxesModel boxesModel, AnchorPane picture) {
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
        boxesModel.addBox(currentImageID.get(),position,position,1); // I puted random bird ID
        //new_rectangle = boxesModel.getRectangleBox(currentImageID.get());
        for (Shape a : boxesModel.getShapes(currentImageID.get())){
            picture.getChildren().add(a);
        }
        //picture.getChildren().add(new_rectangle);
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        boxesModel.editBox(currentImageID.get(),new Point2D(e.getX(),e.getY()));
        new_rectangle = boxesModel.getRectangleBox(currentImageID.get());
        //picture.getChildren().set(0,toDrawn);
    }
}
