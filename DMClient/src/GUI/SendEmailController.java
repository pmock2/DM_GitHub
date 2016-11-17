package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (email.getText().length() > 40) {
                    String s = email.getText().substring(0, 40);
                    email.setText(s);
                }
            }
        });
    }

    public void attemptSend(ActionEvent event) throws NoSuchAlgorithmException, IOException
    {
        Client.toServer.println("RequestPasswordReset");
        Client.toServer.println(email.getText());
        String message = Client.fromServer.readLine();
            if (message.equals("Reset_Success"))
            {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success!");
                    alert.setContentText("Email sent successfully.");
                    alert.showAndWait();
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
            else if (message.equals("Reset_Fail"))
            {
                error.setVisible(true);
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Whoops!");
                alert.setContentText("Something went wrong.");
                alert.showAndWait();
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
