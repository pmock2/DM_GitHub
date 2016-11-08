package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AuditLogController implements Initializable {

    @FXML
    public TableView<AuditLog.LogData> tv = new TableView<>();
    public TableColumn datecolumn, actioncolumn;
    private ObservableList<AuditLog.LogData> data = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle rb) {
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        actioncolumn.setCellValueFactory(new PropertyValueFactory<>("action"));

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
            data.add(new AuditLog.LogData(dateFormat.format(c.getTime()), matchData.getString("ActionType")));
        }
        return data;
    }

    public JSONObject importLog() throws IOException {
        Client.toServer.println("Get_AuditLog");
        String message = Client.fromServer.readLine();
        if (message.equals("Exception_InsufficientPermissions")) {
            JOptionPane.showMessageDialog(null, "You don't have permission to do that.", "Permissions error", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println(message);
            return new JSONObject(message);
        }
        return null;
    }

    }
