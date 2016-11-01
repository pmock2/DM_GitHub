package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class SendEmailController implements Initializable {

    @FXML
    public TextField email;
    public Button sendEmail, mainMenu;
    public Label error;


    public void initialize(URL url, ResourceBundle rb)
    {
    }

    public void attemptSend(ActionEvent event) throws NoSuchAlgorithmException, IOException
    {
        Client.toServer.println("RequestPasswordReset");
        Client.toServer.println(email.getText());
        String message = Client.fromServer.readLine();
            if (message.equals("Reset_Success"))
            {
                try {
                    JOptionPane.showMessageDialog(null, "Email sent successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    Stage stage = (Stage) mainMenu.getScene().getWindow();
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
                //TODO COMPARE EMAIL ENTERED TO EMAILS IN THE DATABASE FOR A MATCH
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (message.equals("Reset_Fail"))
            {
                error.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Oops. Something went wrong.", "We're doomed", JOptionPane.ERROR_MESSAGE);
            }

    }

    public void openMainMenu(ActionEvent event) throws NoSuchAlgorithmException
    {
        try{
            Stage stage = (Stage) mainMenu.getScene().getWindow();
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

    private boolean emailMatch()
    {
        Client.toServer.println("");
        return true;
    }



}
