package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    @FXML
    public TextArea sTextArea;


    public void initialize(URL url, ResourceBundle rb) {
        try {
            setScheduleTextArea();
        }
        catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    public void setScheduleTextArea() throws IOException {
        sTextArea.clear();
        Client.toServer.println("Get_Schedule");
        String message = Client.fromServer.readLine();
        if (message.equals("No_Schedule_Found"))
        {
            JOptionPane.showMessageDialog(null, "Schedule does not exist. Please log in if you would like to create one.", "Schedule error", JOptionPane.ERROR_MESSAGE);
            Stage stage = (Stage) sTextArea.getScene().getWindow();
            stage.hide();

        }
        else {
            while (!message.equals("End_Schedule")) {
                sTextArea.appendText(message + "\n");
                message = Client.fromServer.readLine();
            }
        }
    }
}
