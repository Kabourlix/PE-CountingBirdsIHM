package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EditMode extends Mode{
    private BirdBox boxToEdit;
    private Button editButton;
    private Button deleteButton;

    public EditMode(EnhancedBoxesModel boxesModel, AnchorPane picture, IntegerProperty imageIdProp , Button editButton, Button deleteButton) {
        super(boxesModel, picture,imageIdProp);
        modeName = "edit";
        this.editButton = editButton;
        this.deleteButton = deleteButton;
    }

    @Override
    protected void onMouseClicked(MouseEvent e) {

    }

    @Override
    protected void onMousePressed(MouseEvent e) {

    }

    @Override
    protected void onMouseDragged(MouseEvent e) {

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
                boxToEdit.highlightEditModeBox(false);
                break;
            //There is no other case.
        }

    }

    public void setBoxToEdit(BirdBox b){boxToEdit = b;}
}
