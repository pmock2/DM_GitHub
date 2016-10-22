package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeScore_9d extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ChangeScore4.fxml"));
        primaryStage.setTitle("Super User Score Change");
        primaryStage.setScene(new Scene(root, 540, 325));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
