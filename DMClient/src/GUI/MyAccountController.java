package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;
import teamHarambe.MethodProvider;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {

    @FXML
    public Button menuButton;
    public Label permissions, matchesLabel;
    public Hyperlink changeEmail, addRef;
    public int matchescount;


    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            if (Client.permissionLevel == 1) {
                permissions.setText(Client.email + " - Referee");
                addRef.setVisible(false);
            }
            if (Client.permissionLevel == 2) {
                permissions.setText(Client.email + " - Super Referee");
            }
            if (MethodProvider.checkForSetup())
            {
                JSONObject matches;
                if (Client.permissionLevel == 2)
                {
                    matches = importReschedules();
                }
                else
                {
                    matches = importMatches();
                }
            if (matches == null)
            {
                if (Client.permissionLevel == 2)
                {
                    matchesLabel.setText("There are currently no matches that need rescheduling.");
                }
                matchesLabel.setText("You currently have no matches to score this season.");
            }

            else
                {
                String[] keyNames = JSONObject.getNames(matches);
                for (int i = 0; i < keyNames.length; i++) {
                    matchescount++;
                }
                if (matchescount == 1)
                {
                    if (Client.permissionLevel == 2)
                    {
                        matchesLabel.setText("You currently have " + matchescount + " match to reschedule this season.");
                    }
                    else
                    {
                        matchesLabel.setText("You currently have " + matchescount + " match to score this season.");
                    }
                } else
                {
                    if (Client.permissionLevel == 2)
                    {
                        matchesLabel.setText("You currently have " + matchescount + " matches to reschedule this season.");
                    }
                    else
                    {
                        matchesLabel.setText("You currently have " + matchescount + " matches to score this season.");
                    }
                }
            }
            }
            else
            {
                matchesLabel.setText("There is currently no active season.");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void backToMenu(ActionEvent event)
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

    public void changeEmailAddress(ActionEvent event)
    {
        try{
            Stage stage = (Stage) changeEmail.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangeEmail.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Change Email");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReferee(ActionEvent event)
    {
        try{
            Stage stage = (Stage) changeEmail.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RefereeList.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Manage Referees");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject importMatches() throws IOException {
        Client.toServer.println("Get_RefereedMatches");
        String message = Client.fromServer.readLine();
        System.out.println(message);
        if (message.equals("{}"))
        {
            return null;
        }
        return new JSONObject(message);
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
