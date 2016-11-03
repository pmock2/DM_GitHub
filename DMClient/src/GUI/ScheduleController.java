package GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import teamHarambe.Client;

import javax.swing.*;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    @FXML
    public TableView<Schedule.ScheduleData> tv = new TableView<>();
    public TableColumn datecolumn, team1column, team2column, refcolumn, score1column, score2column;
    public Button saveButton;
    private ObservableList<Schedule.ScheduleData> data = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle rb) {
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        team1column.setCellValueFactory(new PropertyValueFactory<>("team1"));
        team2column.setCellValueFactory(new PropertyValueFactory<>("team2"));
        refcolumn.setCellValueFactory(new PropertyValueFactory<>("ref"));
        score1column.setCellValueFactory(new PropertyValueFactory<>("score1"));
        score2column.setCellValueFactory(new PropertyValueFactory<>("score2"));


        try {
            data = getScheduleData();
            tv.setItems(data);

            if (Client.permissionLevel > 1)
            {
                tv.setEditable(true);
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
                        ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setScore1(t.getNewValue());
                    }
                }
        );

        score2column.setCellFactory(TextFieldTableCell.forTableColumn());
        score2column.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Schedule.ScheduleData, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Schedule.ScheduleData, String> t) {
                        ((Schedule.ScheduleData) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setScore2(t.getNewValue());
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
            match.put("Team0Score", Integer.parseInt(data.get(i).getScore1()));
            match.put("Team1Score", Integer.parseInt(data.get(i).getScore2()));
            
            date.put("Year", Integer.parseInt(data.get(i).dateToYear()));
            date.put("Month", Integer.parseInt(data.get(i).dateToMonth())-1);
            date.put("Day", Integer.parseInt(data.get(i).dateToDay()));
            match.put("Date", date);
            
            schedule.put(""+(data.get(i).getMatchId()), match);
        }

        Client.toServer.println(schedule.toString());
        String response = Client.fromServer.readLine();//Exception_ConflictingDate or Exception_InsufficientPermissions
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
            		matchData.getString("RefereeName"), month+"/"+day+"/"+year, Integer.toString(matchData.getInt("Score0")), 
            		Integer.toString(matchData.getInt("Score1")
            )));
        }
        return data;
    }

    public JSONObject importSchedule() throws IOException {
            Client.toServer.println("Get_Schedule");
            String message = Client.fromServer.readLine();
            String scheduleString = "";
            while (!message.equals("End_Schedule")) {
                System.out.println(message);
                scheduleString += message;
                message = Client.fromServer.readLine();
            }
            
            return new JSONObject(scheduleString);
        }

    }
