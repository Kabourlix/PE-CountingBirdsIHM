package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/***
 * This mode is made for selecting Boxes on the image.
 */
public class SelectionMode extends Mode {
    private BirdBox currentBox;
    private Button editButton;
    private  Button deleteButton;

    public SelectionMode(EnhancedBoxesModel boxesModel, AnchorPane picture, Button editButton, Button deleteButton) {
        super(boxesModel, picture);
        modeName = "selection";
        currentBox = null;

        this.editButton = editButton;
        this.deleteButton = deleteButton;
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {
        Point2D position = new Point2D(e.getX(),e.getY());
        BirdBox b = boxesModel.getBox(currentImageID.get(),position);
        if(currentBox != null){ // If we selected a box before
            currentBox.highlightBox(false); //We deselect it
        }
        if(b != null){ // We found a box
            //We highlight the new one anyway and add it to the previouslySelectedBox
            b.highlightBox(true);
            currentBox = b;

            //TODO : We enable the edit button
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

    public BirdBox getCurrentBox(){return currentBox;}

}
