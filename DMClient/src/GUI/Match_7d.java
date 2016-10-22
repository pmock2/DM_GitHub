package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by claytonleikness on 10/4/16.
 */
public class Match_7d extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Match4.fxml"));
        primaryStage.setTitle("Match 4");
        primaryStage.setScene(new Scene(root, 710, 380));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
