package controller;

import abstraction.BirdBox;
import abstraction.EnhancedBoxesModel;
import abstraction.PictureBank;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/***
 * This class is vital to the application.
 * This is the controller that links the UI elements (the fxml file, editable with Scene Builder) and the model of the
 * application (PAC model).
 * It stores all the data related to the UI needeed, and all the model so as to make connections/interactions between them.
 */
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

    @FXML private AnchorPane picturePane;

    @FXML private Label amountBirds;
    @FXML private VBox birdSpecificDetails;


    private IntegerProperty birdsAmountOnCurrentPicture = new SimpleIntegerProperty();
    public void setBirdsAmountOnCurrentPicture(int i){birdsAmountOnCurrentPicture.set(i);};

    /***
     * This method is called when we load the pictures from a folder.
     */
    @FXML protected void onLoadAction() throws FileNotFoundException {
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

        startAlgorithms();
        initModes();

        birdsAmountOnCurrentPicture.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                amountBirds.setText(Integer.toString(birdsAmountOnCurrentPicture.get()));
            }
        });
    }

    /***
     * This function launches the scripts models.
     */
    private void startAlgorithms() throws FileNotFoundException {
        boxesModel.runPythonCSVScripts("salut","salut");
    }
    private void initModes(){
        IntegerProperty imageIDProp = pictureBank.getCurrentIndexProperty();
        selectionMode = new SelectionMode(boxesModel, picturePane,imageIDProp,birdSpecificDetails,amountBirds,editButton,deleteButton);
        addMode = new AddMode(boxesModel,picturePane,imageIDProp,birdSpecificDetails,amountBirds);
        editMode = new EditMode(boxesModel, picturePane, imageIDProp, birdSpecificDetails, amountBirds, editButton, deleteButton);
        mode = selectionMode;

    }

    /***
     * This function deals with the boxes saved when we change picture or when we delete one.
     */
    private void updateBoxesToDisplay(){
        setMode("selection");
        picturePane.getChildren().clear();
        picturePane.getChildren().add(currentPicture);
        List<BirdBox> boxes = boxesModel.getAllBoxes(pictureBank.getCurrentIndex());
        for(BirdBox box : boxes ){
            for (Shape a : box.getShapes()){
                picturePane.getChildren().add(a);
            }
        }
        birdsAmountOnCurrentPicture.set(boxesModel.getAmountForPicture(pictureBank.getCurrentIndex()));


    }

    /***
     * Function linked with the navigation button. For when the button is changed.
     */
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

    /***
     * Same thing but backward.
     */
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
        birdsAmountOnCurrentPicture.set((picturePane.getChildren().size()-1)/3);
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        mode.onMouseDragged(mouseEvent);
    }

    /***
     * Deals with the add/select button action.
     * @param actionEvent
     */
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
                editButton.setDisable(true);
                deleteButton.setDisable(true);
                addButton.setText("Add");
                break;
            }
        }
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
        mode.onDeleteClicked();
        updateBoxesToDisplay();
    }

    /***
     * This methods update the model when the species of a bird related to a certain box is updated by the user.
     * @param actionEvent
     */
    public void onBirdSpeciesChanged(ActionEvent actionEvent) {
    }
}
