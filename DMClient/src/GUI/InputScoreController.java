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
import javafx.util.Callback;
import org.json.JSONObject;
import teamHarambe.Client;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InputScoreController implements Initializable {

    @FXML
    public Label matchname, aname, bname;
    public CheckBox aforfeit, bforfeit, rs;
    public TextField ascore, bscore;
    public Button matchesButton;
    public DatePicker dpDate;


    public void initialize(URL url, ResourceBundle rb) {

        try {
            dpDate.setVisible(false);
            JSONObject matches = importMatches();
            if (Client.permissionLevel == 2)
            {
                matches = importReschedules();
            }
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

            if (Client.permissionLevel == 2)
            {
                ascore.setDisable(true);
                bscore.setDisable(true);
                aforfeit.setDisable(true);
                bforfeit.setDisable(true);
                dpDate.setVisible(true);
                rs.setDisable(true);
                rs.setSelected(true);

            }

            dpDate.setValue(LocalDate.now());

            Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(10))) {
                        setStyle("-fx-background-color: #A9A9A9;");
                        setDisable(true);
                    }
                }
            };

            dpDate.setDayCellFactory(dayCellFactory);

            } catch (Exception e) {
            e.printStackTrace();
        }

        ascore.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                   ascore.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        bscore.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                   bscore.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        aforfeit.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (aforfeit.isSelected())
                {
                    bforfeit.setDisable(true);
                    rs.setSelected(false);
                    rs.setDisable(true);
                    ascore.setText("0");
                    bscore.setText("1");
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
                    ascore.setText("1");
                    bscore.setText("0");
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
                    dpDate.setVisible(false);
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
                    dpDate.setVisible(false);
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
        if (checkData())
        {
            JSONObject matchToSend = new JSONObject();
            try {
                JSONObject matches = importMatches();
                if (Client.permissionLevel == 2)
                {
                    matches = importReschedules();
                }
                String[] keyNames = JSONObject.getNames(matches);
                JSONObject matchData = matches.getJSONObject(keyNames[Client.selectedMatch]);
                matchToSend.put("MatchId", matchData.getInt("MatchId"));
                if (ascore.getText().equals("")) {
                    matchToSend.put("Team0Score", 0);
                } else {
                    matchToSend.put("Team0Score", Integer.parseInt(ascore.getText()));
                }
                if (bscore.getText().equals("")) {
                    matchToSend.put("Team1Score", 0);
                } else {
                    matchToSend.put("Team1Score", Integer.parseInt(bscore.getText()));
                }
                if (rs.isSelected())
                {

                    matchToSend.put("Year", dpDate.getValue().getYear());
                    matchToSend.put("Month", dpDate.getValue().getMonthValue());
                    matchToSend.put("Day", dpDate.getValue().getDayOfMonth());
                }
                matchToSend.put("Team0Forfeit", aforfeit.isSelected());
                matchToSend.put("Team1Forfeit", bforfeit.isSelected());
                matchToSend.put("Reschedule", rs.isSelected());

                if (Client.permissionLevel == 2)
                {
                    Client.toServer.println("Set_MatchDate");
                    Client.toServer.println(matchData.getInt("MatchId"));
                    Client.toServer.println(dpDate.getValue().getYear());
                    Client.toServer.println(dpDate.getValue().getMonthValue()-1);
                    Client.toServer.println(dpDate.getValue().getDayOfMonth());
                }
                else
                {
                    Client.toServer.println("Set_MatchScore");
                    Client.toServer.println(matchToSend.toString());
                }
                    String reply = Client.fromServer.readLine();
                    if (reply.equals("Success")) {
                        Stage stage = (Stage) rs.getScene().getWindow();
                        stage.hide();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MatchSelect.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Match Select");
                        stage.setScene(new Scene(root1));
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(reply);
                        alert.setContentText("Something went wrong. Reply: " + reply);
                        alert.showAndWait();
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void backToMatches(ActionEvent event) throws NoSuchAlgorithmException
    {
        try{
            Stage stage = (Stage) matchesButton.getScene().getWindow();
            stage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MatchSelect.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Match Select");
                stage.setScene(new Scene(root1));
                stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkData()
    {
        if ((ascore.getText().equals("") && !rs.isSelected()) || (bscore.getText().equals("") && !rs.isSelected()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please enter a score");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public JSONObject importReschedules() throws IOException {
        Client.toServer.println("Get_MatchesNeedingReschedule");
        String message = Client.fromServer.readLine();
        System.out.println(message);
        if (message.equals("{}"))
        {
            return null;
        }
        return new JSONObject(message);
    }

    }
