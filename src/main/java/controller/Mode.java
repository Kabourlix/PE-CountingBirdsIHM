package controller;

import abstraction.EnhancedBoxesModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/***
 * This will set the working mode of the application : is the user selecting ? adding a box ? editing a box ?
 * This abstract class must be inherited to create another mode.
 */
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

    /***
     * This method is called whenever the user click on the AnchorPanel to which the picture is attached to.
     * @param e
     */
    protected abstract void onMouseClicked(MouseEvent e);

    /***
     * Same thing on press.
     * @param e
     */
    protected abstract void onMousePressed(MouseEvent e);

    /***
     * Same thing on mouse dragged.
     * @param e
     */
    protected abstract void onMouseDragged(MouseEvent e);

    /***
     * This method is called when we change mod to communicate information between modes.
     * @param newMode
     */
    protected abstract void onModeChanged(String newMode);

    /***
     * This function deals with the deletion of a box.
     */
    protected abstract void onDeleteClicked();

    /***
     * This method is called whenever the species attached to the box must be changed.
     * @param actionEvent
     */
    protected abstract void onSpeciesNameChange(ActionEvent actionEvent);
}
