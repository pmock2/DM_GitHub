package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import teamHarambe.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.json.JSONObject;
import teamHarambe.MethodProvider;

import javax.swing.*;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox referee;
    public Button beginButton;
    public Button loginButton;
    public Button scheduleButton;
    public Button backButton;
    public Button showButton;
    public Hyperlink sLogoutButton;
    public TextField loginText;
    public PasswordField loginPassword;
    public TextArea sTextArea;
    public Label invalidCredentials;
    public ChoiceBox cb;
    public Button createButton;

    public void initialize(URL url, ResourceBundle rb) {
        if (createButton != null)
        {
            if (Client.permissionLevel < 2)
            {
                createButton.setVisible(false);
            }
        }
    }

    public void checkInitialUser(ActionEvent event)
    {
        //TODO ADD CHECK IF PASSWORDS MATCH
        try{
            Stage stage = (Stage) beginButton.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTeams.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Enter Team Amount");
            stage.setScene(new Scene(root1));
            stage.show();
            //setData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openLogin(ActionEvent event)
    {
        try{
            Stage stage = (Stage) scheduleButton.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Login");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void openStandings(ActionEvent event) throws IOException
    {
        if (MethodProvider.checkForSetup()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Standings.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Current Standings");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Standings do not exist. Please log in if you would like to create them.", "Standings error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void goBack(ActionEvent event)
    {
        try{
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSchedule(ActionEvent event) throws IOException
    {
        if (MethodProvider.checkForSetup()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Schedule.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Schedule");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {

            }
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Schedule does not exist. Please log in if you would like to create one.", "Schedule error", JOptionPane.ERROR_MESSAGE);
        }
    }

	public void attemptLogin(ActionEvent event)
    {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(getLoginPassword().getBytes(), 0, getLoginPassword().length());
            String md5 = new BigInteger(1, md.digest()).toString(16);
        	Client.toServer.println("Login");
        	Client.toServer.println(getLoginUsername());
        	Client.toServer.println(md5);
        	String loginResult = Client.fromServer.readLine();
        	
        	if (loginResult.equals("Login_CodeSuccess")) {
                try{
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setTitle("Password Reset");
                    stage.setScene(new Scene(root1));
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
        	}

        	if (loginResult.equals("Login_Success")) {
        		Client.permissionLevel = Integer.parseInt(Client.fromServer.readLine());
        		System.out.println("Client successfully logged in with permission level of " + Client.permissionLevel);
        		Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperUser.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Referee Menu");
                stage.setScene(new Scene(root1));
                stage.show();
        	} else {
                invalidCredentials.setVisible(true);
        		loginText.setText("");
        		loginPassword.setText("");
        	}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void superLogout(ActionEvent event)
    {
        try{
                Stage stage = (Stage) sLogoutButton.getScene().getWindow();
                stage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root1));
                stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createSchedule(ActionEvent event) throws IOException
    {
        if (MethodProvider.checkForSetup())
        {
            JOptionPane.showMessageDialog(null, "A schedule already exists. If you create a new one, the old one will be deleted.", "Schedule exists", JOptionPane.INFORMATION_MESSAGE);
        }
        try{
            Stage stage = (Stage) sLogoutButton.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTeams.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Create Schedule");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JSONObject getRefereedMatches() throws IOException {
    	Client.toServer.println("Get_RefereedMatches");
    	String jsonString = Client.fromServer.readLine();
    	return new JSONObject(jsonString);
    }
    
    public void setMatchScore(int matchId, double team0Score, double team1Score, boolean team0Forfiet, boolean team1Forfiet) throws IOException {
    	JSONObject data = new JSONObject();
    	data.put("MatchId", matchId);
    	data.put("Team0Score", team0Score);
    	data.put("Team1Score", team1Score);
    	data.put("Team0Forfiet", team0Forfiet);
    	data.put("Team1Forfiet", team1Forfiet);
    	
    	Client.toServer.println("Set_MatchScore");
    	Client.toServer.println(data.toString());
    }
    
    public void setMatchDate(int matchId, Calendar newDate) throws IOException {
    	JSONObject data = new JSONObject();
    	data.put("MatchId", matchId);
    	data.put("Year", newDate.get(newDate.YEAR));
    	data.put("Month", newDate.get(newDate.MONTH));
    	data.put("Day", newDate.get(newDate.DAY_OF_MONTH));
    	
    	Client.toServer.println("Set_MatchDate");
    	Client.toServer.println(data.toString());
    }

    public String getLoginUsername()
    {
        return loginText.getText();
    }

    public String getLoginPassword()
    {
        return loginPassword.getText();
    }

}
