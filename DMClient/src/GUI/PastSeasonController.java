package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class PastSeasonController implements Initializable {

    @FXML
    public Button menuButton;
    public ComboBox<String> seasonBox;


    public void initialize(URL url, ResourceBundle rb) {

        seasonBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldSelection, String newSelection) {
                try {
                    setTargetSeason(seasonBox.getSelectionModel().getSelectedIndex());
                        Stage stage = (Stage) menuButton.getScene().getWindow();
                        stage.hide();
                        if (Client.permissionLevel > 0)
                        {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperUser.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("Referee Menu");
                            stage.setScene(new Scene(root1));
                            stage.show();
                        }
                        else
                        {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();
                            stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.DECORATED);
                            stage.setTitle("Main Menu");
                            stage.setScene(new Scene(root1));
                            stage.show();
                        }
                    } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        try {
            setData();
        }
        catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    private void setTargetSeason(int target) throws IOException
    {
        Client.toServer.println("Set_TargetSeason");
        Client.toServer.println(target);
        String reply = Client.fromServer.readLine();
        if (reply.equals("Success"))
        {
            //Do nothing
        }
    }

    private void setData() throws IOException
    {
        seasonBox.getItems().clear();
        Client.toServer.println("Get_SeasonList");
        JSONObject seasons = new JSONObject(Client.fromServer.readLine());
        String[] keyNames = JSONObject.getNames(seasons.getJSONObject("SeasonList"));
        for (int i=0; i < keyNames.length; i++)
        {
            seasonBox.getItems().add("Season " + Integer.toString(i+1));
        }
    }

    public void openMainMenu(ActionEvent event) throws NoSuchAlgorithmException
    {
        try{
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.hide();
            if (Client.permissionLevel > 0)
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperUser.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Referee Menu");
                stage.setScene(new Scene(root1));
                stage.show();
            }
            else
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root1));
                stage.show();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
