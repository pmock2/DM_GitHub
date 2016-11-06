package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MatchSelectController implements Initializable {

    @FXML
    public VBox vb;


    public void initialize(URL url, ResourceBundle rb) {

        try {
            JSONObject matches = importMatches();
            if (matches == null)
            {
                vb.getChildren().add(new Label("You currently have no matches to score."));
            }
            else
            {
                String[] keyNames = JSONObject.getNames(matches);
                for (int i=0; i < keyNames.length; i++)
                {
                    JSONObject matchData = matches.getJSONObject(keyNames[i]);
                    Hyperlink h = new Hyperlink(matchData.getString("Team0Name") + " vs " + matchData.getString("Team1Name"));
                    vb.getChildren().add(h);
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
        if (message.equals("{}"))
        {
            return null;
        }
        return new JSONObject(message);
    }

    }
