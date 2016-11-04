package GUI;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Standings extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Standings.fxml"));
        primaryStage.setTitle("Standings");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }



    public static class StandingsData {

        private final SimpleStringProperty team1;
        private final SimpleStringProperty wins;

        public StandingsData(String t1, String wins) {
            this.team1 = new SimpleStringProperty(t1);
            this.wins = new SimpleStringProperty(wins);
        }

        public String getTeam1() {
            return team1.get();
        }

        public void setTeam1(String t1) {
            team1.set(t1);
        }

        public String getWins() {
            return wins.get();
        }

        public void setWins(String w) {
            wins.set(w);
        }

    }
    public static void main(String[] args) {
        launch(args);
    }
}