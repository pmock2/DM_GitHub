package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import teamHarambe.Client;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeEmailController implements Initializable {

    @FXML
    public TextField e1, e2;
    public Button resetButton;
    public static final Pattern VALID_EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public void initialize(URL url, ResourceBundle rb)
    {
        e1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (e1.getText().length() > 40) {
                    String s = e1.getText().substring(0, 40);
                    e1.setText(s);
                }
            }
        });

        e2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (e2.getText().length() > 40) {
                    String s = e2.getText().substring(0, 40);
                    e2.setText(s);
                }
            }
        });
    }


    public void back(ActionEvent event) throws NoSuchAlgorithmException
    {
        try
        {
            Stage stage = (Stage) e1.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyAccount.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Account Overview");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attemptChange(ActionEvent event) throws NoSuchAlgorithmException
    {
        try{

            if (!e1.getText().equals(e2.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Email must match");
                alert.setContentText("Please ensure your email matches");
                alert.showAndWait();

            }
            else if (e1.getLength() == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Blank Email");
                alert.setContentText("Please enter a valid Email");
                alert.showAndWait();
            }
            else if (!validEmail(e1.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Email");
                alert.setContentText("Please enter a valid Email");
                alert.showAndWait();
            }
            else if (e1.getText().equals(Client.email))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Same Email");
                alert.setContentText("Why would you want to change to your current email?");
                alert.showAndWait();
            }
            else
            {
                Client.toServer.println("Set_Email");
                Client.toServer.println(e1.getText());
                String response = Client.fromServer.readLine();
                if (response.equals("Success"))
                {
                    Client.email = e1.getText();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success!");
                    alert.setContentText("Email successfully changed.");
                    alert.showAndWait();
                    Stage stage = (Stage) e1.getScene().getWindow();
                    stage.hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyAccount.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setTitle("Account Overview");
                    stage.setScene(new Scene(root1));
                    stage.show();
                }
                else if (response.equals("Exception_ExistingReferee"))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Existing Referee");
                    alert.setContentText("This email already exists in the database. Please enter a new email.");
                    alert.showAndWait();
                }
                else
                {
                    //Something went wrong.
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL .matcher(emailStr);
        return matcher.find();
    }

}
