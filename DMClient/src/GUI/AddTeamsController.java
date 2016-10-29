package GUI;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.json.JSONObject;
import teamHarambe.Client;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddTeamsController implements Initializable {

    @FXML
    public ChoiceBox<String> cb;
    public TextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;
    public DatePicker dpDate;
    public Button submit;


    public void initialize(URL url, ResourceBundle rb) {
    dpDate.setValue(LocalDate.now());

        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
        {
            @Override
            public void updateItem(LocalDate item, boolean empty)
            {
                super.updateItem(item, empty);

                if(item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusYears(1)))
                {
                    setStyle("-fx-background-color: #ffc0cb;");
                    setDisable(true);

                }
            }
        };

        dpDate.setDayCellFactory(dayCellFactory);
    setData();
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
             public void changed(ObservableValue<? extends String> selected, String oldSelection, String newSelection)
            {
                switch((String)cb.getValue())
                {
                    case "2":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(false);
                        t4.setVisible(false);
                        t5.setVisible(false);
                        t6.setVisible(false);
                        t7.setVisible(false);
                        t8.setVisible(false);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t3.clear();
                        t4.clear();
                        t5.clear();
                        t6.clear();
                        t7.clear();
                        t8.clear();
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "3":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(false);
                        t5.setVisible(false);
                        t6.setVisible(false);
                        t7.setVisible(false);
                        t8.setVisible(false);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t4.clear();
                        t5.clear();
                        t6.clear();
                        t7.clear();
                        t8.clear();
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "4":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(false);
                        t6.setVisible(false);
                        t7.setVisible(false);
                        t8.setVisible(false);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t5.clear();
                        t6.clear();
                        t7.clear();
                        t8.clear();
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "5":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(true);
                        t6.setVisible(false);
                        t7.setVisible(false);
                        t8.setVisible(false);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t6.clear();
                        t7.clear();
                        t8.clear();
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "6":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(true);
                        t6.setVisible(true);
                        t7.setVisible(false);
                        t8.setVisible(false);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t7.clear();
                        t8.clear();
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "7":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(true);
                        t6.setVisible(true);
                        t7.setVisible(true);
                        t8.setVisible(false);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t8.clear();
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "8":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(true);
                        t6.setVisible(true);
                        t7.setVisible(true);
                        t8.setVisible(true);
                        t9.setVisible(false);
                        t10.setVisible(false);
                        t9.clear();
                        t10.clear();
                        break;
                    }
                    case "9":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(true);
                        t6.setVisible(true);
                        t7.setVisible(true);
                        t8.setVisible(true);
                        t9.setVisible(true);
                        t10.setVisible(false);
                        t10.clear();
                        break;
                    }
                    case "10":
                    {
                        t1.setVisible(true);
                        t2.setVisible(true);
                        t3.setVisible(true);
                        t4.setVisible(true);
                        t5.setVisible(true);
                        t6.setVisible(true);
                        t7.setVisible(true);
                        t8.setVisible(true);
                        t9.setVisible(true);
                        t10.setVisible(true);
                        break;
                    }
                }
            }
        });
    }

    private void setData()
    {
        cb.getItems().clear();
        cb.getItems().addAll(
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10");

    }

    public void attemptSubmit(ActionEvent event) throws IOException
    {
        if (checkData())
        {
            int teamAmount = Integer.parseInt(cb.getValue());
            int dateYear = dpDate.getValue().getYear();
            int dateMonth = dpDate.getValue().getMonthValue();
            int dateDay = dpDate.getValue().getDayOfMonth();

            Client.toServer.println("Create_DB");
            Client.toServer.println(teamAmount);

            switch(cb.getValue())
            {
                case "2":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    break;
                }
                case "3":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    break;
                }
                case "4":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    break;
                }
                case "5":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    break;
                }
                case "6":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    break;
                }
                case "7":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    Client.toServer.println(t7.getText());
                    break;
                }
                case "8":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    Client.toServer.println(t7.getText());
                    Client.toServer.println(t8.getText());
                    break;
                }
                case "9":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    Client.toServer.println(t7.getText());
                    Client.toServer.println(t8.getText());
                    Client.toServer.println(t9.getText());
                    break;
                }
                case "10":
                {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    Client.toServer.println(t7.getText());
                    Client.toServer.println(t8.getText());
                    Client.toServer.println(t9.getText());
                    Client.toServer.println(t10.getText());
                    break;
                }
            }
            Client.toServer.println(dateYear);
            Client.toServer.println(dateMonth);
            Client.toServer.println(dateDay);
        }
        String result = Client.fromServer.readLine();
        if (result.equals("Success"))
        {
            JOptionPane.showMessageDialog(null, "Database successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Main Menu");
            stage.setScene(new Scene(root1));
            stage.show();
        }
        else if (result.equals("Failure"))
        {
            JOptionPane.showMessageDialog(null, "Database creation failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean checkData()
    {
        switch(cb.getValue())
        {
            case "2":
            {
                break;
            }
        }
        return true;
    }



}
