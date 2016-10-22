package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SuperUserSetup_1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SuperRefSetup.fxml"));
        primaryStage.setTitle("Setup");
        primaryStage.setScene(new Scene(root, 600, 325));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
