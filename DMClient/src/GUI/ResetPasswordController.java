package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {

    @FXML
    public PasswordField pass1, pass2;
    public Button resetButton;


    public void initialize(URL url, ResourceBundle rb)
    {
    }

    public void attemptReset(ActionEvent event) throws NoSuchAlgorithmException
    {
        try{

            if (!pass1.getText().equals(pass2.getText()))
            {
                JOptionPane.showMessageDialog(null, "Passwords must match.", "Error", JOptionPane.ERROR_MESSAGE);

            }
            else if (pass1.getLength() == 0)
            {
                JOptionPane.showMessageDialog(null, "Please enter a password", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(pass2.getText().getBytes(), 0, pass2.getText().length());
                String md5 = new BigInteger(1, md.digest()).toString(16);
                Client.toServer.println(md5);
                String response = Client.fromServer.readLine();
                
                if (response.equals("Login_Success")) {
                	Client.permissionLevel = Integer.parseInt(Client.fromServer.readLine());
                	System.out.println("Client successfully logged in with permission level of " + Client.permissionLevel);
	                JOptionPane.showMessageDialog(null, "Password reset successfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
	                Stage stage = (Stage) resetButton.getScene().getWindow();
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
                	//Issue
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}