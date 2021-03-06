package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class MatchSelectController implements Initializable {

    @FXML
    public VBox vb;
    public Button menuButton;
    public Label l, l1;


    public void initialize(URL url, ResourceBundle rb) {

        if (Client.permissionLevel == 2)
        {
            l.setText("Matches to Reschedule");
            l1.setText("Please select the matches to reschedule them");
        }

        try {
                vb.getChildren().clear();
                JSONObject matches = null;
            if (Client.permissionLevel == 2)
            {
                matches = importReschedules();
            }
            else
            {
                matches = importMatches();
            }
                if (matches == null) {
                    if (Client.permissionLevel == 2)
                    {
                        vb.getChildren().add(new Label("You currently have no matches to reschedule."));
                    }
                    else {
                        vb.getChildren().add(new Label("You currently have no matches to score."));
                    }
                }
                else
                    {
                    String[] keyNames = JSONObject.getNames(matches);
                    for (int i = 0; i < keyNames.length; i++) {
                        int matchSelected = i;
                        JSONObject matchData = matches.getJSONObject(keyNames[i]);
                        if (!matchData.getBoolean("Scored")) {
                            Hyperlink h = new Hyperlink(matchData.getString("Team0Name") + " vs " + matchData.getString("Team1Name"));
                            h.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    try {
                                        Client.selectedMatch = matchSelected;
                                        Stage stage = (Stage) h.getScene().getWindow();
                                        stage.hide();
                                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InputScore.fxml"));
                                        Parent root1 = (Parent) fxmlLoader.load();
                                        stage = new Stage();
                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        stage.initStyle(StageStyle.DECORATED);
                                        stage.setTitle("Set Team Score");
                                        stage.setScene(new Scene(root1));
                                        stage.show();
                                        System.out.println(Client.selectedMatch);
                                    } catch (IOException f) {
                                        f.printStackTrace();
                                    }
                                }
                            });
                            vb.getChildren().add(h);
                        }
                    }
                }
            } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public JSONObject importMatches() throws IOException {

            Client.toServer.println("Get_RefereedMatches");
            String message = Client.fromServer.readLine();
            System.out.println(message);
            if (message.equals("{}")) {
                return null;
            }
            return new JSONObject(message);
    }

    public void backToMenu(ActionEvent event) throws NoSuchAlgorithmException
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

    public JSONObject importReschedules() throws IOException {
        Client.toServer.println("Get_MatchesNeedingReschedule");
        String message = Client.fromServer.readLine();
        System.out.println(message);
        if (message.equals("{}"))
        {
            return null;
        }
        return new JSONObject(message);
    }

    }
