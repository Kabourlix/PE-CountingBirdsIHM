package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try{
            primaryStage.setTitle("Countable Software");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Main.fxml"));

            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
