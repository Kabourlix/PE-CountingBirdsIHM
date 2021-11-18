package controller;

import abstraction.PictureBank;
import javafx.fxml.FXML;
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

    @FXML private AnchorPane picturePane;

    @FXML protected void onLoadAction()
    {
        dirChooser = new DirectoryChooser();
        File selectedFile = dirChooser.showDialog(stage);
        if(selectedFile != null)
        {
            pictureBank = new PictureBank(selectedFile.getAbsolutePath());
        }
        //TODO : Add the display of the first image.
        Image firstImage = pictureBank.getImage(0);
        picturePane.getChildren().add(new ImageView(firstImage));
    }

    public void init(Stage s)
    {
        stage = s;
    }


}
