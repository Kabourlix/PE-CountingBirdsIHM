package controller;

import abstraction.BoxesModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EditMode extends Mode{
    public EditMode(BoxesModel boxesModel, AnchorPane picture) {
        super(boxesModel, picture);
        modeName = "edit";
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
}
