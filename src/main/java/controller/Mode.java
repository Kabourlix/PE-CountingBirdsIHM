package controller;

import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

abstract class Mode {
    protected String modeName;
    protected EnhancedBoxesModel boxesModel;
    protected IntegerProperty currentImageID;
    protected AnchorPane picture;

    public Mode(EnhancedBoxesModel boxesModel, AnchorPane picture, IntegerProperty imageIDProp){
        this.boxesModel = boxesModel;
        this.picture = picture;
        currentImageID = imageIDProp;

    }

    public String getModeName(){return modeName;}
    protected abstract void onMouseClicked(MouseEvent e);
    protected abstract void onMousePressed(MouseEvent e);
    protected abstract void onMouseDragged(MouseEvent e);
    protected abstract void onModeChanged(String newMode);

    protected abstract void onDeleteClicked();
}
