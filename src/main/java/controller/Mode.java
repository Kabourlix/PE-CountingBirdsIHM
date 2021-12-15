package controller;

import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

abstract class Mode {
    protected String modeName;
    protected EnhancedBoxesModel boxesModel;
    protected IntegerProperty currentImageID;
    protected AnchorPane picture;

    protected VBox details;
    protected Label amountBirds;

    public Mode(EnhancedBoxesModel boxesModel, AnchorPane picture, IntegerProperty imageIDProp, VBox details, Label amountBirds){
        this.boxesModel = boxesModel;
        this.picture = picture;
        currentImageID = imageIDProp;
        this.details = details;
        this.amountBirds = amountBirds;

    }

    public String getModeName(){return modeName;}
    protected abstract void onMouseClicked(MouseEvent e);
    protected abstract void onMousePressed(MouseEvent e);
    protected abstract void onMouseDragged(MouseEvent e);
    protected abstract void onModeChanged(String newMode);

    protected abstract void onDeleteClicked();
    protected abstract void onSpeciesNameChange(ActionEvent actionEvent);
}
