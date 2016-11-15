package GUI;

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

public class StandingsController implements Initializable {

    @FXML
    public TableView<Standings.StandingsData> tv = new TableView<>();
    public TableColumn teamcolumn, winscolumn, rankcolumn;
    public Button menuButton;
    private ObservableList<Standings.StandingsData> data = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle rb) {
        teamcolumn.setCellValueFactory(new PropertyValueFactory<>("team1"));
        winscolumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        rankcolumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        try {
            data = getStandingsData();
            tv.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
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
    public ObservableList<Standings.StandingsData> getStandingsData() throws IOException
    {
        ObservableList<Standings.StandingsData> data = FXCollections.observableArrayList();
        JSONObject standings = importStandings();
        String[] keyNames = JSONObject.getNames(standings);
        for (int i=0; i < keyNames.length; i++)
        {
            JSONObject matchData = standings.getJSONObject(keyNames[i]);
            data.add(new Standings.StandingsData(matchData.getString("Name"), Double.toString(matchData.getDouble("Wins")), Integer.toString(matchData.getInt("Rank"))));
        }
        return data;
    }

    public JSONObject importStandings() throws IOException {
        Client.toServer.println("Get_Standings");
        String message = Client.fromServer.readLine();
        if (message.equals("No_Standings_Found")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Whoops!");
            alert.setHeaderText("Whoops, we need a schedule");
            alert.setContentText("Standings cannot exist without a schedule. Please have the super referee create a schedule.");
            alert.showAndWait();
        } else {
            return new JSONObject(message);
        }
        return null;
    }

    }
