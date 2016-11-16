package GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import teamHarambe.Client;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleController implements Initializable {

    @FXML
    public TableView<Schedule.ScheduleData> tv = new TableView<>();
    public TableColumn datecolumn, team1column, team2column, refcolumn, score1column, score2column, slotcolumn;
    public Button saveButton, menuButton;
    private ObservableList<Schedule.ScheduleData> data = FXCollections.observableArrayList();
    public static final Pattern VALID_EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public void initialize(URL url, ResourceBundle rb) {
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        team1column.setCellValueFactory(new PropertyValueFactory<>("team1"));
        team2column.setCellValueFactory(new PropertyValueFactory<>("team2"));
        refcolumn.setCellValueFactory(new PropertyValueFactory<>("ref"));
        score1column.setCellValueFactory(new PropertyValueFactory<>("score1"));
        score2column.setCellValueFactory(new PropertyValueFactory<>("score2"));
        slotcolumn.setCellValueFactory(new PropertyValueFactory<>("slot"));


        try {
            data = getScheduleData();
            tv.setItems(data);

            if (Client.permissionLevel > 1)
            {
                tv.setEditable(true);
                datecolumn.setEditable(true);
                refcolumn.setEditable(true);
                score1column.setEditable(true);
                score2column.setEditable(true);
                slotcolumn.setEditable(true);
                saveButton.setVisible(true);
            }
            else
            {
                tv.setEditable(false);
                datecolumn.setEditable(false);
                refcolumn.setEditable(false);
                score1column.setEditable(false);
                score2column.setEditable(false);
                slotcolumn.setEditable(false);
                saveButton.setVisible(false);
            }
        }
        catch (Exception e)
            {
                e.printStackTrace();
            }


        datecolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        datecolumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDate(t.getNewValue());
                    }
                }
        );

        team1column.setCellFactory(TextFieldTableCell.forTableColumn());
        team1column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTeam1(t.getNewValue());
                    }
                }
        );

        team2column.setCellFactory(TextFieldTableCell.forTableColumn());
        team2column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTeam1(t.getNewValue());
                    }
                }
        );

        score1column.setCellFactory(TextFieldTableCell.forTableColumn());
        score1column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        if (t.getNewValue().matches("\\d*") || t.getNewValue().equals("-1"))
                        {
                            ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setScore1(t.getNewValue());
                        }
                    }
                }
        );

        score2column.setCellFactory(TextFieldTableCell.forTableColumn());
        score2column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        if (t.getNewValue().matches("\\d*") || t.getNewValue().equals("-1"))
                        {
                            ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setScore2(t.getNewValue());
                        }
                    }
                }
        );

        slotcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        slotcolumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        if (t.getNewValue().equals("Morning") || t.getNewValue().equals("Night"))
                        {
                            ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setSlot(t.getNewValue());
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Change Warning");
                            alert.setContentText("The only timeslot changes that are saved are \"Morning\" and \"Night\". Anything else will not be reflected on the schedule.");
                            alert.showAndWait();
                        }
                    }
                }
        );

        refcolumn.setCellFactory(TextFieldTableCell.forTableColumn());
        refcolumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        if (validEmail(t.getNewValue())) {
                            ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setRef(t.getNewValue());
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setHeaderText("Change Warning");
                            alert.setContentText("Only valid emails will be updated through the schedule. Please try again.");
                            alert.showAndWait();
                        }
                    }
                }
        );


    }


    public void attemptSave(ActionEvent event) throws IOException
    {
        JSONObject schedule = new JSONObject();
        for (int i=0; i < data.size(); i++)
        {
        	JSONObject match = new JSONObject();
        	JSONObject date = new JSONObject();

        	match.put("RefereeName", data.get(i).getRef());
            if (data.get(i).getScore1().equals("F"))
            {
                match.put("Team0Score", -1);
            }
            else if (data.get(i).getScore1().equals("W"))
            {
                match.put("Team0Score", 0);
            }
            else
            {
                match.put("Team0Score", Integer.parseInt(data.get(i).getScore1()));
            }
            if (data.get(i).getScore2().equals("F"))
            {
                match.put("Team1Score", -1);
            }
            else if (data.get(i).getScore2().equals("W"))
            {
                match.put("Team1Score", 0);
            }
            else
            {
                match.put("Team1Score", Integer.parseInt(data.get(i).getScore2()));
            }
            date.put("Year", Integer.parseInt(data.get(i).dateToYear()));
            date.put("Month", Integer.parseInt(data.get(i).dateToMonth())-1);
            date.put("Day", Integer.parseInt(data.get(i).dateToDay()));
            match.put("Date", date);
            if (data.get(i).getSlot().equals("Morning"))
            {
                match.put("IsMorning", true);
            }
            if (data.get(i).getSlot().equals("Night"))
            {
                match.put("IsMorning", false);
            }

            
            schedule.put(""+(data.get(i).getMatchId()), match);
        }

        Client.toServer.println("Update_Schedule");
        Client.toServer.println(schedule.toString());
        String response = Client.fromServer.readLine();//Exception_ConflictingDate, Exception_NonexistantReferee or Exception_InsufficientPermissions
        System.out.println("Response: "+response);
        if (response.equals("Exception_ConflictingDate"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Conflicting Dates");
            alert.setContentText("Detected conflicting data. Data did not save.");
            alert.showAndWait();
        }
        else if (response.equals("Exception_NonexistantReferee"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Non-existant Referee");
            alert.setContentText("Changes not saved. Please enter a referee that exists in the database. You may add new referees through account options.");
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success!");
            alert.setContentText("Changes saved successfully.");
            alert.showAndWait();
        }
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

    public ObservableList<Schedule.ScheduleData> getScheduleData() throws IOException
    {
        ObservableList<Schedule.ScheduleData> data = FXCollections.observableArrayList();
        JSONObject schedule = importSchedule();
        String[] keyNames = JSONObject.getNames(schedule);
        for (int i=0; i < keyNames.length; i++)
        {
            JSONObject matchData = schedule.getJSONObject(keyNames[i]);
            JSONObject date = matchData.getJSONObject("Date");
            int year = date.getInt("Year");
            int month = (date.getInt("Month")+1);
            int day = date.getInt("Day");

            data.add(new Schedule.ScheduleData(
                    Integer.parseInt(keyNames[i]), matchData.getString("Team0Name"), matchData.getString("Team1Name"),
                    matchData.getString("RefereeName"), month + "/" + day + "/" + year, Integer.toString(matchData.getInt("Score0")),
                    Integer.toString(matchData.getInt("Score1")), Boolean.toString(matchData.getBoolean("IsMorning"))
                        ));
                    //DISPLAY EDITS
            if (matchData.getInt("Score0") == -1)
            {
                data.get(i).setScore1("F");
                data.get(i).setScore2("W");
            }
            if (matchData.getInt("Score1") == -1)
            {
                data.get(i).setScore1("W");
                data.get(i).setScore2("F");
            }
            if (matchData.getBoolean("IsMorning"))
            {
                data.get(i).setSlot("Morning");
            }
            if (!matchData.getBoolean("IsMorning"))
            {
                data.get(i).setSlot("Night");
            }
        }
        return data;
    }

    public JSONObject importSchedule() throws IOException {
            Client.toServer.println("Get_Schedule");
            String message = Client.fromServer.readLine();
            String scheduleString = "";
            while (!message.equals("End_Schedule")) {
                scheduleString += message;
                message = Client.fromServer.readLine();
            }
            
            return new JSONObject(scheduleString);
        }

    private static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL .matcher(emailStr);
        return matcher.find();
    }

    }
