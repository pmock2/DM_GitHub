package GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.json.JSONObject;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StandingsController implements Initializable {

    @FXML
    public TextArea standingsTextArea;


    public void initialize(URL url, ResourceBundle rb) {
        try {
            setStandingsTextArea();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStandingsTextArea() throws IOException {
        standingsTextArea.clear();
        Client.toServer.println("Get_Standings");
        String message = Client.fromServer.readLine();
        if (message.equals("No_Standings_Found"))
        {
            JOptionPane.showMessageDialog(null, "Standings do not exist. Please log in if you would like to create them.", "Standings error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            JSONObject rankings = new JSONObject(message);
            standingsTextArea.appendText(rankings.toString(1));
            }
        }
    }
