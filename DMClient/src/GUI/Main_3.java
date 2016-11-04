package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by claytonleikness on 10/4/16.
 */
public class Main_3 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Main Page");
        primaryStage.setScene(new Scene(root, 1200, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
