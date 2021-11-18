package controller;

import abstraction.PictureBank;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


    @FXML private Button nextButton;
    @FXML private Button prevButton;

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
        picturePane.getChildren().add(currentPicture);
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


}
