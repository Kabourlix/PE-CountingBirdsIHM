package controller;

import abstraction.BoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

abstract class Mode {
    protected String modeName;
    protected BoxesModel boxesModel;
    protected IntegerProperty currentImageID = new SimpleIntegerProperty();
    protected AnchorPane picture;

    public Mode(BoxesModel boxesModel, AnchorPane picture){
        this.boxesModel = boxesModel;
        this.picture = picture;
    }

    public IntegerProperty getCurrentImageIDProperty(){return currentImageID;}
    public String getModeName(){return modeName;}
    protected abstract void onMouseClicked(MouseEvent e);
    protected abstract void onMousePressed(MouseEvent e);
    protected abstract void onMouseDragged(MouseEvent e);
}
