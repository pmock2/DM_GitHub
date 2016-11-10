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
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTeamsController implements Initializable {

    @FXML
    public ChoiceBox<String> cb, ref;
    public TextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    public CheckBox o1, o2, o3, o4, o5, o6, o7, o8, o9, o10;
    public Label l1, l2;
    public DatePicker dpDate;
    public Button submit, menuButton;
    private String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10 = null;
    public static final Pattern VALID_EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public void initialize(URL url, ResourceBundle rb) {
        dpDate.setValue(LocalDate.now());

        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(10)) || item.getDayOfWeek() != DayOfWeek.SATURDAY) {
                    setStyle("-fx-background-color: #A9A9A9;");
                    setDisable(true);
                }
            }
        };

        dpDate.setDayCellFactory(dayCellFactory);
        setData();

        o1.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o1.isSelected())
                {
                    e1.setDisable(false);
                }
                else
                {
                    try {
                        s1 = getEmailForId(0);
                    } catch (Exception e)
                    {
                    }

                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                }
            }
        });

        o2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o2.isSelected())
                {
                    e2.setDisable(false);
                }
                else
                {
                    try {
                        s2 = getEmailForId(1);
                    } catch (Exception e)
                    {
                    }

                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                }
            }
        });

        o3.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o3.isSelected())
                {
                    e3.setDisable(false);
                }
                else
                {
                    try {
                        s3 = getEmailForId(2);
                    } catch (Exception e)
                    {
                    }

                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                }
            }
        });

        o4.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o4.isSelected())
                {
                    e4.setDisable(false);
                }
                else
                {
                    try {
                        s4 = getEmailForId(3);
                    } catch (Exception e)
                    {
                    }

                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                }
            }
        });

        o5.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o5.isSelected())
                {
                    e5.setDisable(false);
                }
                else
                {
                    try {
                        s5 = getEmailForId(4);
                    } catch (Exception e)
                    {
                    }

                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                }
            }
        });

        o6.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o6.isSelected())
                {
                    e6.setDisable(false);
                }
                else
                {
                    try {
                        s6 = getEmailForId(5);
                    } catch (Exception e)
                    {
                    }

                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        o6.setVisible(true);
                    }
                }
            }
        });

        o7.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o7.isSelected())
                {
                    e7.setDisable(false);
                }
                else
                {
                    try {
                        s7 = getEmailForId(6);
                    } catch (Exception e)
                    {
                    }

                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        o7.setVisible(true);
                    }
                }
            }
        });

        o8.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o8.isSelected())
                {
                    e8.setDisable(false);
                }
                else
                {
                    try {
                        s8 = getEmailForId(7);
                    } catch (Exception e)
                    {
                    }

                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        o8.setVisible(true);
                    }
                }
            }
        });

        o9.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o9.isSelected())
                {
                    e9.setDisable(false);
                }
                else
                {
                    try {
                        s9 = getEmailForId(8);
                    } catch (Exception e)
                    {
                    }

                    if (s9 != null)
                    {
                        e9.setText(s9);
                        e9.setDisable(true);
                        o9.setVisible(true);
                    }
                }
            }
        });

        o10.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o10.isSelected())
                {
                    e10.setDisable(false);
                }
                else
                {
                    try {
                        s10 = getEmailForId(9);
                    } catch (Exception e)
                    {
                    }

                    if (s10 != null)
                    {
                        e10.setText(s10);
                        e10.setDisable(true);
                        o10.setVisible(true);
                    }
                }
        }
                                           });
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldSelection, String newSelection) {
                switch ((String) cb.getValue()) {
                    case "2": {
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
                    case "3": {
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
                    case "4": {
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
                    case "5": {
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
                    case "6": {
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
                    case "7": {
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
                    case "8": {
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
                    case "9": {
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
                    case "10": {
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

    ref.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed (ObservableValue < ? extends String > selected, String oldSelection, String newSelection)
        {
            l1.setVisible(true);
            l2.setVisible(true);
            switch ((String) ref.getValue()) {
                case "1": {
                    e1.setVisible(true);
                    e2.setVisible(false);
                    e3.setVisible(false);
                    e4.setVisible(false);
                    e5.setVisible(false);
                    e6.setVisible(false);
                    e7.setVisible(false);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o2.setVisible(false);
                    o3.setVisible(false);
                    o4.setVisible(false);
                    o5.setVisible(false);
                    o6.setVisible(false);
                    o7.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o2.setSelected(false);
                    o3.setSelected(false);
                    o4.setSelected(false);
                    o5.setSelected(false);
                    o6.setSelected(false);
                    o7.setSelected(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e2.clear();
                    e3.clear();
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    break;
                }
                case "2": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(false);
                    e4.setVisible(false);
                    e5.setVisible(false);
                    e6.setVisible(false);
                    e7.setVisible(false);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o3.setVisible(false);
                    o4.setVisible(false);
                    o5.setVisible(false);
                    o6.setVisible(false);
                    o7.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o3.setSelected(false);
                    o4.setSelected(false);
                    o5.setSelected(false);
                    o6.setSelected(false);
                    o7.setSelected(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e3.clear();
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    break;
                }
                case "3": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(false);
                    e5.setVisible(false);
                    e6.setVisible(false);
                    e7.setVisible(false);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o4.setVisible(false);
                    o5.setVisible(false);
                    o6.setVisible(false);
                    o7.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o4.setSelected(false);
                    o5.setSelected(false);
                    o6.setSelected(false);
                    o7.setSelected(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    break;
                }
                case "4": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(false);
                    e6.setVisible(false);
                    e7.setVisible(false);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o5.setVisible(false);
                    o6.setVisible(false);
                    o7.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o5.setSelected(false);
                    o6.setSelected(false);
                    o7.setSelected(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    break;
                }
                case "5": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(true);
                    e6.setVisible(false);
                    e7.setVisible(false);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o6.setVisible(false);
                    o7.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o6.setSelected(false);
                    o7.setSelected(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                        s5 = getEmailForId(4);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                    break;
                }
                case "6": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(true);
                    e6.setVisible(true);
                    e7.setVisible(false);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o7.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o7.setSelected(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                        s5 = getEmailForId(4);
                        s6 = getEmailForId(5);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        o6.setVisible(true);
                    }
                    break;
                }
                case "7": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(true);
                    e6.setVisible(true);
                    e7.setVisible(true);
                    e8.setVisible(false);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o8.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o8.setSelected(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                        s5 = getEmailForId(4);
                        s6 = getEmailForId(5);
                        s7 = getEmailForId(6);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        o6.setVisible(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                    }
                    break;
                }
                case "8": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(true);
                    e6.setVisible(true);
                    e7.setVisible(true);
                    e8.setVisible(true);
                    e9.setVisible(false);
                    e10.setVisible(false);
                    o9.setVisible(false);
                    o10.setVisible(false);
                    o9.setSelected(false);
                    o10.setSelected(false);
                    e9.clear();
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                        s5 = getEmailForId(4);
                        s6 = getEmailForId(5);
                        s7 = getEmailForId(6);
                        s8 = getEmailForId(7);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        o6.setVisible(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        o7.setVisible(true);
                    }
                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        o8.setVisible(true);
                    }
                    break;
                }
                case "9": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(true);
                    e6.setVisible(true);
                    e7.setVisible(true);
                    e8.setVisible(true);
                    e9.setVisible(true);
                    e10.setVisible(false);
                    o10.setVisible(false);
                    o10.setSelected(false);
                    e10.clear();
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                        s5 = getEmailForId(4);
                        s6 = getEmailForId(5);
                        s7 = getEmailForId(6);
                        s8 = getEmailForId(7);
                        s9 = getEmailForId(8);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        o6.setVisible(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        o7.setVisible(true);
                    }
                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        o8.setVisible(true);
                    }
                    if (s9 != null)
                    {
                        e9.setText(s9);
                        e9.setDisable(true);
                        o9.setVisible(true);
                    }
                    break;
                }
                case "10": {
                    e1.setVisible(true);
                    e2.setVisible(true);
                    e3.setVisible(true);
                    e4.setVisible(true);
                    e5.setVisible(true);
                    e6.setVisible(true);
                    e7.setVisible(true);
                    e8.setVisible(true);
                    e9.setVisible(true);
                    e10.setVisible(true);
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(1);
                        s3 = getEmailForId(2);
                        s4 = getEmailForId(3);
                        s5 = getEmailForId(4);
                        s6 = getEmailForId(5);
                        s7 = getEmailForId(6);
                        s8 = getEmailForId(7);
                        s9 = getEmailForId(8);
                        s10 = getEmailForId(9);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        o1.setVisible(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        o2.setVisible(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        o3.setVisible(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        o4.setVisible(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        o5.setVisible(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        o6.setVisible(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        o7.setVisible(true);
                    }
                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        o8.setVisible(true);
                    }
                    if (s9 != null)
                    {
                        e9.setText(s9);
                        e9.setDisable(true);
                        o9.setVisible(true);
                    }
                    if (s10 != null)
                    {
                        e10.setText(s10);
                        e10.setDisable(true);
                        o10.setVisible(true);
                    }
                    break;
                }
            }
        }
    });
}

    private String getEmailForId(int id) throws IOException {
    	JSONObject referees = Client.getReferees();
    	String[] idList = JSONObject.getNames(referees);
        JSONObject data = referees.getJSONObject(idList[id]);

    	return data.getString("Email");
    }

    private int getRefereeCount() throws IOException
    {
        JSONObject referees = Client.getReferees();
        String[] idList = JSONObject.getNames(referees);
        return idList.length;
    }
    
    private void setData()
    {
        cb.getItems().clear();
        ref.getItems().clear();
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
        ref.getItems().addAll(
                "1",
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
    
    private JSONObject newReferees() throws IOException {
    	JSONObject referees = new JSONObject();
    	
    	if (!e1.getText().equals("")) {
    		referees.put("1", new JSONObject("{\"Email\" : " + e1.getText() + "}"));
    	}
    	if (!e2.getText().equals("")) {
    		referees.put("2", new JSONObject("{\"Email\" : " + e2.getText() + "}"));
    	}
    	if (!e3.getText().equals("")) {
    		referees.put("3", new JSONObject("{\"Email\" : " + e3.getText() + "}"));
    	}
    	if (!e4.getText().equals("")) {
    		referees.put("4", new JSONObject("{\"Email\" : " + e4.getText() + "}"));
    	}
    	if (!e5.getText().equals("")) {
    		referees.put("5", new JSONObject("{\"Email\" : " + e5.getText() + "}"));
    	}
    	if (!e6.getText().equals("")) {
    		referees.put("6", new JSONObject("{\"Email\" : " + e6.getText() + "}"));
    	}
    	if (!e7.getText().equals("")) {
    		referees.put("7", new JSONObject("{\"Email\" : " + e7.getText() + "}"));
    	}
    	if (!e8.getText().equals("")) {
    		referees.put("8", new JSONObject("{\"Email\" : " + e8.getText() + "}"));
    	}
    	if (!e9.getText().equals("")) {
    		referees.put("9", new JSONObject("{\"Email\" : " + e9.getText() + "}"));
    	}
    	if (!e10.getText().equals("")) {
    		referees.put("10", new JSONObject("{\"Email\" : " + e10.getText() + "}"));
    	}
    	
    	return referees;
    }

    public void attemptSubmit(ActionEvent event) throws IOException
    {
        if (checkData()) {
            int teamAmount = Integer.parseInt(cb.getValue());
            int dateYear = dpDate.getValue().getYear();
            int dateMonth = dpDate.getValue().getMonthValue();
            int dateDay = dpDate.getValue().getDayOfMonth();

            Client.toServer.println("Create_DB");
            Client.toServer.println(teamAmount);

            switch (cb.getValue()) {
                case "2": {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    break;
                }
                case "3": {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    break;
                }
                case "4": {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    break;
                }
                case "5": {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    break;
                }
                case "6": {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    break;
                }
                case "7": {
                    Client.toServer.println(t1.getText());
                    Client.toServer.println(t2.getText());
                    Client.toServer.println(t3.getText());
                    Client.toServer.println(t4.getText());
                    Client.toServer.println(t5.getText());
                    Client.toServer.println(t6.getText());
                    Client.toServer.println(t7.getText());
                    break;
                }
                case "8": {
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
                case "9": {
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
                case "10": {
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
            
            Client.toServer.println(newReferees().toString());
            
            String result = Client.fromServer.readLine();
            if (result.equals("Success")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Success");
                alert.setContentText("Database successfully created!");
                alert.showAndWait();
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperUser.fxml"));
                Parent root1 = fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root1));
                stage.show();
            } else if (result.equals("Failure")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Whoops");
                alert.setContentText("Something went wrong. The database wasn't created.");
                alert.showAndWait();
            }
        }

    }

    private boolean checkData()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ArrayList<String> teamnames = new ArrayList<>();
        ArrayList<String> teamnames2 = new ArrayList<>();
        Set<String> hs = new HashSet<>();

        teamnames.add(t1.getText());
        teamnames.add(t2.getText());
        teamnames.add(t3.getText());
        teamnames.add(t4.getText());
        teamnames.add(t5.getText());
        teamnames.add(t6.getText());
        teamnames.add(t7.getText());
        teamnames.add(t8.getText());
        teamnames.add(t9.getText());
        teamnames.add(t10.getText());

        hs.addAll(teamnames);
        teamnames2.addAll(hs);

        for (int i = 0; i < teamnames.size(); i++) {
            for (int j = i+1; j < teamnames.size(); j++) {
                if (teamnames.get(i).equals(teamnames.get(j)))
                {
                    if (!teamnames.get(i).equals("") && !teamnames.get(j).equals(""))
                    {
                        alert.setTitle("Error");
                        alert.setHeaderText("Team Name Duplication Detected");
                        alert.setContentText("We have detected that \"" + teamnames.get(i) + "\" is duplicated in the team list. Please remove the duplicate entry and try again");
                        alert.showAndWait();
                        return false;
                    }
                }
            }
        }


        try {


            if (Integer.parseInt(cb.getValue())/2 > Integer.parseInt(ref.getValue())) {

                alert.setTitle("Error");
                alert.setHeaderText("Invalid Referee Amount");
                alert.setContentText("You must have one referee per every two teams.");
                alert.showAndWait();
                return false;
            }
        }
        catch (NumberFormatException e)
            {
                alert.setTitle("Error");
                alert.setHeaderText("Please make a selection");
                alert.setContentText("You must select both a number of teams and a number of referees.");
                alert.showAndWait();
                return false;
            }

        switch(cb.getValue())
        {
            case "2":
            {
                if (t1.getText().equals("") || t2.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "3":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "4":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "5":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "6":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "7":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "8":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "9":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals("") || t9.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "10":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals("") || t9.getText().equals("") || t10.getText().equals(""))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Please enter team names");
                    alert.setContentText("You cannot enter blank team names.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
        }

        switch(ref.getValue())
        {
            case "2":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "3":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "4":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "5":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()) || !validEmail(e5.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "6":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()) || !validEmail(e5.getText()) || !validEmail(e6.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "7":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()) || !validEmail(e5.getText()) || !validEmail(e6.getText()) || !validEmail(e7.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "8":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()) || !validEmail(e5.getText()) || !validEmail(e6.getText()) || !validEmail(e7.getText()) || !validEmail(e8.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "9":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()) || !validEmail(e5.getText()) || !validEmail(e6.getText()) || !validEmail(e7.getText()) || !validEmail(e8.getText()) || !validEmail(e9.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
            case "10":
            {
                if (!validEmail(e1.getText()) || !validEmail(e2.getText()) || !validEmail(e3.getText()) || !validEmail(e4.getText()) || !validEmail(e5.getText()) || !validEmail(e6.getText()) || !validEmail(e7.getText()) || !validEmail(e8.getText()) || !validEmail(e9.getText()) || !validEmail(e10.getText()))
                {
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid E-Mail");
                    alert.setContentText("Please enter a valid E-Mail address.");
                    alert.showAndWait();
                    return false;
                }
                break;
            }
        }


        return true;
    }

    public  boolean equalLists(List<String> one, List<String> two){
        if (one == null && two == null){
            return true;
        }

        if((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()){
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by A. R. S.
        one = new ArrayList<String>(one);
        two = new ArrayList<String>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
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
    private static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL .matcher(emailStr);
        return matcher.find();
    }



}
