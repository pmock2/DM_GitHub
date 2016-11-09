package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import teamHarambe.Client;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AuditLogController implements Initializable {

    @FXML
    public TableView<AuditLog.LogData> tv = new TableView<>();
    public TableColumn datecolumn, actioncolumn, refereecolumn;
    private ObservableList<AuditLog.LogData> data = FXCollections.observableArrayList();
    public Button menuButton;


    public void initialize(URL url, ResourceBundle rb) {
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        actioncolumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        refereecolumn.setCellValueFactory(new PropertyValueFactory<>("em"));

        try {
            data = getLogData();
            tv.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AuditLog.LogData> getLogData() throws IOException
    {
        ObservableList<AuditLog.LogData> data = FXCollections.observableArrayList();
        JSONObject standings = importLog();
        String[] keyNames = JSONObject.getNames(standings);
        for (int i=0; i < keyNames.length; i++)
        {
            JSONObject matchData = standings.getJSONObject(keyNames[i]);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(matchData.getLong("Date"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            dateFormat.setTimeZone(c.getTimeZone());
            data.add(new AuditLog.LogData(dateFormat.format(c.getTime()), matchData.getString("ActionType"), matchData.getString("Referee")));
        }
        return data;
    }

    public JSONObject importLog() throws IOException {
        Client.toServer.println("Get_AuditLog");
        String message = Client.fromServer.readLine();
        if (message.equals("Exception_InsufficientPermissions")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Tsk. Tsk. Tsk.");
            alert.setContentText("Looks like you don't have permission to do that.");
            alert.showAndWait();
        } else {
            System.out.println(message);
            return new JSONObject(message);
        }
        return null;
    }

    public void openMainMenu(ActionEvent event) throws NoSuchAlgorithmException
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

    }
