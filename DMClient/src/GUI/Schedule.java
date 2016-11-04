package GUI;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Schedule extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Schedule.fxml"));
        primaryStage.setTitle("Schedule");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static class ScheduleData {//This is a match -- not a schedule

        private final SimpleStringProperty team1;
        private final SimpleStringProperty team2;
        private final SimpleStringProperty referee;
        private final SimpleStringProperty date;
        private final SimpleStringProperty score1;
        private final SimpleStringProperty score2;
        private final int matchId;

        public ScheduleData(int matchId, String t1, String t2, String ref, String date, String s1, String s2) {
        	this.matchId = matchId;
            this.team1 = new SimpleStringProperty(t1);
            this.team2 = new SimpleStringProperty(t2);
            this.referee = new SimpleStringProperty(ref);
            this.date = new SimpleStringProperty(date);
            this.score1 = new SimpleStringProperty(s1);
            this.score2 = new SimpleStringProperty(s2);
        }
        
        public int getMatchId() {
        	return matchId;
        }

        public String getTeam1() {
            return team1.get();
        }

        public void setTeam1(String t1) {
            team1.set(t1);
        }

        public String getTeam2() {
            return team2.get();
        }

        public void setTeam2(String t2) {
            team2.set(t2);
        }

        public String getRef() {
            return referee.get();
        }

        public void setRef(String ref) {
            referee.set(ref);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getScore1() {
            return score1.get();
        }

        public void setScore1(String s1) {
            this. score1.set(s1);
        }

        public String getScore2() {
            return score2.get();
        }

        public void setScore2(String s2) {
            this.score2.set(s2);
        }

        public String dateToYear()
        {
            boolean passedfirst = false;
            boolean passedsecond = false;
            String year = "";
            String date = this.date.get();
            for (int i = 0; i < date.length(); i++) {
                if (passedfirst && passedsecond && date.charAt(i) != '/')
                {
                    year += date.charAt(i);
                }
                if (passedfirst && date.charAt(i) == '/')
                {
                    passedsecond = true;
                }
                if (date.charAt(i) == '/')
                {
                    passedfirst = true;
                }
            }
            return year;
        }

        public String dateToMonth()
        {
            String month = "";
            String date = this.date.get();
            for (int i = 0; i < date.length(); i++)
            {
                if (date.charAt(i) == '/')
                {
                   break;
                }
                month += date.charAt(i);
            }
            return month;
        }

        public String dateToDay()
        {
            boolean passedfirst = false;
            String day = "";
            String date = this.date.get();
            for (int i = 0; i < date.length(); i++) {
                if (passedfirst && date.charAt(i) != '/')
                {
                    day += date.charAt(i);
                }
                if (passedfirst && date.charAt(i) == '/')
                {
                    break;
                }
                if (date.charAt(i) == '/')
                {
                    passedfirst = true;
                }
            }
            return day;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}