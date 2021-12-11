package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import abstraction.PictureBank;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

public class ViewController {

    private DirectoryChooser dirChooser;
    private PictureBank pictureBank;
    private Stage stage;
    private ImageView currentPicture;

    private EnhancedBoxesModel boxesModel;

    private Mode mode;
    private SelectionMode selectionMode;
    private AddMode addMode;
    private EditMode editMode;



    @FXML private Button nextButton;
    @FXML private Button prevButton;

    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button addButton;
    @FXML private Button restartButton;

    @FXML private AnchorPane picturePane;

    /***
     * This method is called when we load the pictures from a folder.
     */
    @FXML protected void onLoadAction()
    {
        // Load the pictures and create a pictureBank to manage them.
        dirChooser = new DirectoryChooser();
        File selectedFile = dirChooser.showDialog(stage);
        if(selectedFile != null)
        {
            pictureBank = new PictureBank(selectedFile.getAbsolutePath());
        }

        //Add the display of the first image.
        Image firstImage = pictureBank.getImage(0);

        pictureBank.setCurrentIndex(0);
        prevButton.setDisable(true);
        nextButton.setDisable(false);

        currentPicture = new ImageView(firstImage);
        currentPicture.setPreserveRatio(true);

        currentPicture.fitHeightProperty().bind(picturePane.heightProperty());
        currentPicture.fitWidthProperty().bind(picturePane.widthProperty());

        picturePane.getChildren().add(currentPicture);

        boxesModel = new EnhancedBoxesModel(pictureBank.getImagesLength());
        addButton.setDisable(false);
        restartButton.setDisable(false);

        startAlgorithms();
        initModes();
        initListeners();
    }

    /***
     * This function launches the scripts models.
     */
    private void startAlgorithms(){

    }
    private void initModes(){
        IntegerProperty imageIDProp = pictureBank.getCurrentIndexProperty();
        selectionMode = new SelectionMode(boxesModel, picturePane,imageIDProp,editButton,deleteButton);
        addMode = new AddMode(boxesModel,picturePane,imageIDProp);
        editMode = new EditMode(boxesModel, picturePane, imageIDProp, editButton, deleteButton);
        mode = selectionMode;

    }

    private void updateBoxesToDisplay(){
        picturePane.getChildren().clear();
        picturePane.getChildren().add(currentPicture);
        List<BirdBox> boxes = boxesModel.getAllBoxes(pictureBank.getCurrentIndex());
        for(BirdBox box : boxes ){
            for (Shape a : box.getShapes()){
                picturePane.getChildren().add(a);
            }
        }


    }

    @FXML protected void onNextImage()
    {
        // Gestion de l'image
        int currentIndex = pictureBank.getCurrentIndex();

        if(currentIndex < pictureBank.getImagesLength()-1)
        {
            setMode("selection");

            currentIndex++;
            pictureBank.setCurrentIndex(currentIndex);
            currentPicture.setImage(pictureBank.getImage(currentIndex));

            updateBoxesToDisplay();

            if(currentIndex == pictureBank.getImagesLength()-1){nextButton.setDisable(true);}
            if(prevButton.isDisabled()){prevButton.setDisable(false);}

        }
    }

    @FXML protected void onPrevImage()
    {
        int currentIndex = pictureBank.getCurrentIndex();

        if(currentIndex > 0)
        {
            setMode("selection");

            currentIndex--;
            pictureBank.setCurrentIndex(currentIndex);
            currentPicture.setImage(pictureBank.getImage(currentIndex));

            updateBoxesToDisplay();

            if(currentIndex == 0){prevButton.setDisable(true);}
            if(nextButton.isDisabled()){nextButton.setDisable(false);}
        }
    }

    public void init(Stage s)
    {
        stage = s;
    }

    // Mouse interaction with the pane
    public void onMousePressed(MouseEvent mouseEvent) {
        mode.onMousePressed(mouseEvent);
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        mode.onMouseClicked(mouseEvent);
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        mode.onMouseDragged(mouseEvent);
    }

    public void addIsClicked(ActionEvent actionEvent) {
        setMode(!mode.getModeName().equals("selection") ? "selection" : "add");
    }

    private void setMode(String modeName){
        if(modeName.equals(mode.getModeName())) return; // We do nothing if it's the same mode
        mode.onModeChanged(modeName);
        switch (modeName){
            case "add" :{
                mode = addMode;
                addButton.setText("Select");
                break;
            }
            case "edit" : {
                editMode.setBoxToEdit(selectionMode.getCurrentBox()); //!Not very safe, to be updated in future version.
                mode = editMode;
                editButton.setDisable(true);
                addButton.setText("Select");
                break;
            }
            case "selection" :{
                mode = selectionMode;
                addButton.setText("Add");
                break;
            }
        }
    }

    // Listeners for some specifics variables
    private void initListeners(){

    }




    // Change cursor on button and pane
    public void onMouseEnteredPane(MouseEvent mouseEvent) {
        if(mode != null){
            if(mode.getModeName() == "add"){
                stage.getScene().setCursor(Cursor.CROSSHAIR);
            }
        }

    }
    public void onMouseExitedPane(MouseEvent mouseEvent) {
        stage.getScene().setCursor(Cursor.DEFAULT);
    }

    public void onEditButtonIsClicked(ActionEvent actionEvent) {
        setMode("edit");
    }

    public void onDeleteButtonIsClicked(ActionEvent actionEvent) {
    }
}
