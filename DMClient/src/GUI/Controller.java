package GUI;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    @FXML
    private ChoiceBox referee;
    public Button beginButton;

    ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
            "one", "two", "three", "four", "five"));



    public void initialize(){
        choiceBox.getItems().removeAll(choiceBox.getItems());
        choiceBox.getItems().addAll("one", "two", "three", "four", "five");
        choiceBox.getSelectionModel().select("one");
    }

    public void checkInitialUser(ActionEvent event)
    {
        //TODO ADD CHECK IF PASSWORDS MATCH
        try{
            Stage stage = (Stage) beginButton.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
          //  stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
