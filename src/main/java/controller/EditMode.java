package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

public class EditMode extends Mode{
    private BirdBox boxToEdit;
    private Button editButton;
    private Button deleteButton;
    private Shape[] shapes;

    private int modeMove; // If 1 : move the box, if 2 : edit the box shape from top & if 3 : edit the box shape from bottom.

    private Point2D oldMouse;

    public EditMode(EnhancedBoxesModel boxesModel, AnchorPane picture, IntegerProperty imageIdProp , VBox details, Label amountBirds, Button editButton, Button deleteButton) {
        super(boxesModel, picture,imageIdProp,details, amountBirds);
        modeName = "edit";
        this.editButton = editButton;
        this.deleteButton = deleteButton;

    }

    @Override
    protected void onMouseClicked(MouseEvent e) {

    }

    @Override
    protected void onMousePressed(MouseEvent e) {
        shapes = boxToEdit.getShapes(); //rectangle, top circle & bottom circle.
        Point2D mousePos = new Point2D(e.getX(), e.getY());
        if(shapes[1].contains(mousePos)){ // top circle
            modeMove = 2;
        }else if (shapes[2].contains(mousePos)){ // bottom circle
            modeMove = 3;
        }else if (shapes[0].contains(mousePos)){
            modeMove = 1;
        }else{
            modeMove = 0;
        }
        oldMouse = mousePos;
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        Point2D pos = new Point2D(e.getX(),e.getY());
        switch (modeMove){
            case 0 : break;

            case 1 :
                boxToEdit.moveBox(oldMouse,pos);
                oldMouse = pos;
                break;

            case 2 :
                boxToEdit.editBoxTop(pos);
                break;

            case 3 :
                boxToEdit.editBox(pos);
                break;
        }
    }

    @Override
    protected void onModeChanged(String newMode) {
        switch (newMode){
            case "add":
                if(boxToEdit != null) {
                    boxToEdit.highlightEditModeBox(false);
                    boxToEdit.highlightBox(false);
                    editButton.setDisable(true);
                    deleteButton.setDisable(true);

                }
                break;

            case "selection" :
                if(boxToEdit != null) boxToEdit.highlightEditModeBox(false);
                break;
            //There is no other case.
        }

    }

    @Override
    protected void onDeleteClicked() {
        if(boxToEdit!=null){
            boxesModel.deleteBox(currentImageID.get(),boxToEdit);
            boxToEdit = null;
        }
    }

    @Override
    protected void onSpeciesNameChange(ActionEvent e) {

    }

    public void setBoxToEdit(BirdBox b){boxToEdit = b;}
}
