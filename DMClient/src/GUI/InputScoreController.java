package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InputScoreController implements Initializable {

    @FXML
    public Label matchname, aname, bname;
    public CheckBox aforfeit, bforfeit, rs;
    public TextField ascore, bscore;


    public void initialize(URL url, ResourceBundle rb) {

        try {
            JSONObject matches = importMatches();
            if (matches == null)
            {
                matchname.setText("Something went wrong. Please try again.");
            }
            else
            {
                String[] keyNames = JSONObject.getNames(matches);
                JSONObject matchData = matches.getJSONObject(keyNames[Client.selectedMatch]);
                matchname.setText(matchData.getString("Team0Name") + " vs " + matchData.getString("Team1Name"));
                aname.setText(matchData.getString("Team0Name"));
                bname.setText(matchData.getString("Team1Name"));

            }
            } catch (Exception e) {
            e.printStackTrace();
        }

        aforfeit.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (aforfeit.isSelected())
                {
                    bforfeit.setDisable(true);
                    rs.setSelected(false);
                    rs.setDisable(true);
                    ascore.setText("-1");
                    bscore.setText("0");
                    ascore.setDisable(true);
                    bscore.setDisable(true);

                }
                else
                {
                    bforfeit.setDisable(false);
                    rs.setDisable(false);
                    ascore.setText("");
                    bscore.setText("");
                    ascore.setDisable(false);
                    bscore.setDisable(false);
                }
            }
        });

        bforfeit.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (bforfeit.isSelected())
                {
                    aforfeit.setDisable(true);
                    rs.setSelected(false);
                    rs.setDisable(true);
                    ascore.setText("0");
                    bscore.setText("-1");
                    ascore.setDisable(true);
                    bscore.setDisable(true);

                }
                else
                {
                    aforfeit.setDisable(false);
                    rs.setDisable(false);
                    ascore.setText("");
                    bscore.setText("");
                    ascore.setDisable(false);
                    bscore.setDisable(false);
                }
            }
        });

        rs.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (rs.isSelected())
                {
                    aforfeit.setSelected(false);
                    aforfeit.setDisable(true);
                    bforfeit.setSelected(false);
                    bforfeit.setDisable(true);
                    ascore.setText("");
                    bscore.setText("");
                    ascore.setDisable(true);
                    bscore.setDisable(true);
                }
                else
                {
                    aforfeit.setDisable(false);
                    bforfeit.setDisable(false);
                    ascore.setDisable(false);
                    bscore.setDisable(false);
                }
            }
        });

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

    public void attemptSubmit(ActionEvent event)
    {
        JSONObject matchToSend = new JSONObject();
        try{
            JSONObject matches = importMatches();
            String[] keyNames = JSONObject.getNames(matches);
            JSONObject matchData = matches.getJSONObject(keyNames[Client.selectedMatch]);
            matchToSend.put("MatchId", matchData.getInt("MatchId"));
            matchToSend.put("Team0Score", Integer.parseInt(ascore.getText()));
            matchToSend.put("Team1Score", Integer.parseInt(bscore.getText()));
            //matchToSend.put("Team0Forfeit", aforfeit.isSelected());
            //matchToSend.put("Team1Forfeit", bforfeit.isSelected());
            matchToSend.put("Reschedule", rs.isSelected());

            Client.toServer.println("Set_MatchScore");
            Client.toServer.println(matchToSend.toString());
            String reply = Client.fromServer.readLine();
            if (reply.equals("Success"))
            {
                Stage stage = (Stage) rs.getScene().getWindow();
                stage.hide();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(reply);
                alert.setContentText("Something went wrong. Reply: " +reply);
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
