package controller;

import abstraction.BoxesModel;
import abstraction.PictureBank;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;

public class ViewController {

    private DirectoryChooser dirChooser;
    private PictureBank pictureBank;
    private Stage stage;
    private ImageView currentPicture;

    private BoxesModel boxesModel;

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

    @FXML protected void onLoadAction()
    {
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

        boxesModel = new BoxesModel(pictureBank.getImagesLength());
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
        selectionMode = new SelectionMode(boxesModel, picturePane);
        addMode = new AddMode(boxesModel,picturePane);
        editMode = new EditMode(boxesModel, picturePane);
        mode = selectionMode;

        mode.getCurrentImageIDProperty().bind(pictureBank.getCurrentIndexProperty()); // We bind the image index of mode to the image id of picture bank
        // Thus any change made to the picture bank one will affect the mode one.
    }


    @FXML protected void onNextImage()
    {
        int currentIndex = pictureBank.getCurrentIndex();
        if(currentIndex < pictureBank.getImagesLength()-1)
        {
            currentIndex++;
            pictureBank.setCurrentIndex(currentIndex);
            currentPicture.setImage(pictureBank.getImage(currentIndex));
            if(currentIndex == pictureBank.getImagesLength()-1){nextButton.setDisable(true);}
            if(prevButton.isDisabled()){prevButton.setDisable(false);}
        }
    }

    @FXML protected void onPrevImage()
    {
        int currentIndex = pictureBank.getCurrentIndex();
        if(currentIndex > 0)
        {
            currentIndex--;
            pictureBank.setCurrentIndex(currentIndex);
            currentPicture.setImage(pictureBank.getImage(currentIndex));
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
        if(mode.getModeName()!="add"){
            mode = addMode;
            addButton.setText("Select");
        }
        else{
            mode = selectionMode;
            addButton.setText("Add");
        }

    }

    // Listeners for some specifics variables
    private void initListeners(){
        boxesModel.currentBoxProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                System.out.println("Current box has changed : " + (Integer) oldNumber + " -> " + (Integer) newNumber);
                selectBox((Integer) oldNumber,(Integer) newNumber);
            }
        });
    }

    private void selectBox(int selectedBox, int oldSelectedBox){

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
}
