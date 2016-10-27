package GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.net.Socket;

import org.json.JSONObject;

public class Controller {

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

    ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
            "one", "two", "three", "four", "five"));


    public void initialize() throws IOException {
        choiceBox.getItems().removeAll(choiceBox.getItems());
        choiceBox.getItems().addAll("one", "two", "three", "four", "five");
        choiceBox.getSelectionModel().select("one");
        //scheduleTextArea.setText("");
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
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root1));
            stage.show();
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

    public void showSchedule(ActionEvent event)
    {
        try{
            setScheduleTextArea();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void openStandings(ActionEvent event)
    {
        try{
        	Client.toServer.println("Get_Rankings");
        	JSONObject rankings = new JSONObject(Client.fromServer.readLine());
        	System.out.println("Got rankings!");
        	System.out.println(rankings.toString());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Standings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Current Standings");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
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
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Schedule.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Schedule");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void attemptLogin(ActionEvent event)
    {
        try{
        	Client.toServer.println("Login");
        	Client.toServer.println(getLoginUsername());
        	Client.toServer.println(getLoginPassword());
        	String loginResult = Client.fromServer.readLine();

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

    public String getLoginUsername()
    {
        return loginText.getText();
    }

    public String getLoginPassword()
    {
        return loginPassword.getText();
    }

    public void setScheduleTextArea() throws IOException {
        sTextArea.clear();
        Client.toServer.println("Get_Schedule");
        String message = Client.fromServer.readLine();
        while (!message.equals("End_Schedule")) {
            sTextArea.appendText(message + "\n");
            message = Client.fromServer.readLine();
        }
    }

}
