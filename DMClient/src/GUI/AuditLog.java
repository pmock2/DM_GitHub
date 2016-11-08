package GUI;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AuditLog extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Standings.fxml"));
        primaryStage.setTitle("Standings");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }



    public static class LogData {

        private final SimpleStringProperty date;
        private final SimpleStringProperty action;
        private final SimpleStringProperty em;

        public LogData(String d, String a, String r) {
            this.date = new SimpleStringProperty(d);
            this.action = new SimpleStringProperty(a);
            this.em = new SimpleStringProperty(r);
        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String t1) {
            date.set(t1);
        }

        public String getAction() {
            return action.get();
        }

        public void setAction(String w) {
            action.set(w);
        }

        public String getEm() {
            return em.get();
        }

        public void setReferee(String r) {
            em.set(r);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}