package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class AddTeams extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("AddTeams.fxml"));
        primaryStage.setTitle("Add Teams");
        primaryStage.setScene(new Scene(root, 600, 375));
        primaryStage.show();
        
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void addChoiceBox(ChoiceBox cb)
    {
        cb = new ChoiceBox();
        cb.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    }
}
