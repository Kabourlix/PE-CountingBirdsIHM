package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

/***
 * This mode is made for selecting Boxes on the image.
 */
public class SelectionMode extends Mode {
    private BirdBox currentBox;
    private Button editButton;
    private  Button deleteButton;

    private TextField species;

    public SelectionMode(EnhancedBoxesModel boxesModel, AnchorPane picture, IntegerProperty imageIdProp, VBox details, Label amountBirds, Button editButton, Button deleteButton) {
        super(boxesModel, picture, imageIdProp, details, amountBirds);
        modeName = "selection";
        currentBox = null;

        this.editButton = editButton;
        this.deleteButton = deleteButton;
        species = (TextField) details.getChildren().get(1);
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        Point2D position = new Point2D(e.getX(),e.getY());
        BirdBox b = boxesModel.getBox(currentImageID.get(),position);
        if(currentBox != null){ // If we selected a box before
            currentBox.highlightBox(false); //We deselect it
            //We disable the details window
            details.setDisable(true);
            //We update the text in the TextField to be nothing.
            species.setText("");

        }
        if(b != null){ // We found a box
            //We highlight the new one anyway and add it to the previouslySelectedBox
            b.highlightBox(true);
            currentBox = b;

            //We deal with the details window
            details.setDisable(false);


            //We enable the edit button
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }else{ // In case we do not find a box we must disable the edit and delete button.
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }


    }

    @Override
    protected void onMousePressed(MouseEvent e) {
        // This does nothing because are useless in this mode.
    }

    @Override
    protected void onMouseDragged(MouseEvent e) {
        // This does nothing because are useless in this mode.
    }

    @Override
    protected void onModeChanged(String newMode) {
        switch (newMode){
            case "add":
                if(currentBox != null) {
                    currentBox.highlightEditModeBox(false);
                    currentBox.highlightBox(false);
                    editButton.setDisable(true);
                    deleteButton.setDisable(true);

                 }
                break;
            case "edit" :  // This must be possible only if a box is selected.
                currentBox.highlightEditModeBox(true);
                break;

            //There is no other case.
        }

    }

    @Override
    protected void onDeleteClicked() {
        if(currentBox!=null){
            boxesModel.deleteBox(currentImageID.get(),currentBox);
            currentBox = null;
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    public BirdBox getCurrentBox(){return currentBox;}

}
