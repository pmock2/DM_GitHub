package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class RefereeListController implements Initializable {

    @FXML
    public VBox vb;
    public Button menuButton;
    public Label l, l1;


    public void initialize(URL url, ResourceBundle rb) {

        try {
                vb.getChildren().clear();
                JSONObject referees = importReferees();
                if (referees == null)
                {
                        vb.getChildren().add(new Label("There are currently no referees besides yourself."));
                }
                else
                    {
                    String[] keyNames = JSONObject.getNames(referees);
                    for (int i = 0; i < keyNames.length; i++) {
                        int matchSelected = i;
                        JSONObject matchData = referees.getJSONObject(keyNames[i]);
                            Hyperlink h = new Hyperlink(matchData.getString("Email") + " - " + (matchData.getBoolean("IsActive") ? "Active" : "Inactive"));
                            h.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    try {
                                        Client.toServer.println("Set_RefereeActive");
                                        Client.toServer.println(matchData.getBoolean("IsActive") ? "false" : "true");
                                        Client.toServer.println(matchData.getString("Email"));
                                        String result = Client.fromServer.readLine();
                                        System.out.println("Result: "+result);
                                        if (result.equals("Exception_TooLittleReferees"))
                                        {
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle("Error");
                                            alert.setHeaderText("Woah, there");
                                            alert.setContentText("Let's keep at least one referee active, shall we?");
                                            alert.showAndWait();
                                        }
                                        else
                                        {
                                            Stage stage = (Stage) vb.getScene().getWindow();
                                            stage.hide();
                                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RefereeList.fxml"));
                                            Parent root1 = (Parent) fxmlLoader.load();
                                            stage = new Stage();
                                            stage.initModality(Modality.APPLICATION_MODAL);
                                            stage.initStyle(StageStyle.DECORATED);
                                            stage.setTitle("Manage Referees");
                                            stage.setScene(new Scene(root1));
                                            stage.show();
                                        }
                                    } catch (Exception f) {
                                        f.printStackTrace();
                                    }
                                }
                            });
                            vb.getChildren().add(h);
                        }
                        Hyperlink h = new Hyperlink("Add new referee");
                        h.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                try {
                                        Stage stage = (Stage) vb.getScene().getWindow();
                                        stage.hide();
                                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddReferee.fxml"));
                                        Parent root1 = (Parent) fxmlLoader.load();
                                        stage = new Stage();
                                        stage.initModality(Modality.APPLICATION_MODAL);
                                        stage.initStyle(StageStyle.DECORATED);
                                        stage.setTitle("Add New Referee");
                                        stage.setScene(new Scene(root1));
                                        stage.show();

                                } catch (Exception f) {
                                    f.printStackTrace();
                                }
                            }
                        });
                        vb.getChildren().add(h);
                    }
            } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void backToMenu(ActionEvent event) throws NoSuchAlgorithmException
    {
        try{
            Stage stage = (Stage) menuButton.getScene().getWindow();
            stage.hide();
            if (Client.permissionLevel > 0)
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperUser.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Referee Menu");
                stage.setScene(new Scene(root1));
                stage.show();
            }
            else
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root1));
                stage.show();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject importReferees() throws IOException {
        Client.toServer.println("Get_AllReferees");
        String message = Client.fromServer.readLine();
        System.out.println(message);
        if (message.equals("{}"))
        {
            return null;
        }
        return new JSONObject(message);
    }

    }
