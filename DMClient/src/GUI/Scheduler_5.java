package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by claytonleikness on 10/6/16.
 */
public class Scheduler_5 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scheduler.fxml"));
        primaryStage.setTitle("Team Input");
        primaryStage.setScene(new Scene(root, 500, 630));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
