package controller;

import abstraction.BoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.scene.input.MouseEvent;

abstract class Mode {
    private String modeName;
    protected BoxesModel boxesModel;
    protected IntegerProperty currentImageID;

    public Mode(BoxesModel boxesModel){
        this.boxesModel = boxesModel;
    }

    public IntegerProperty getCurrentImageIDProperty(){return currentImageID;}
    protected abstract void onMouseClicked(MouseEvent e);
    protected abstract void onMousePressed(MouseEvent e);
    protected abstract void onMouseDragged(MouseEvent e);
}
