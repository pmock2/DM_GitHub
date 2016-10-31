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
    public ChoiceBox<String> cb, ref;
    public TextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
    public CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, o1, o2, o3, o4, o5, o6, o7, o8, o9, o10;
    public Label l1, l2, l3, l4;
    public DatePicker dpDate;
    public Button submit;
    private String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10 = null;


    public void initialize(URL url, ResourceBundle rb) {
        dpDate.setValue(LocalDate.now());

        Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusYears(1))) {
                    setStyle("-fx-background-color: #ffc0cb;");
                    setDisable(true);

                }
            }
        };

        dpDate.setDayCellFactory(dayCellFactory);
        setData();

        o10.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                if (o10.isSelected())
                {
                    e10.setDisable(false);
                    p10.setDisable(false);
                    c10.setDisable(false);
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
            switch ((String) ref.getValue()) {
                case "1": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(false);
                    p2.setVisible(false);
                    c2.setVisible(false);
                    e3.setVisible(false);
                    p3.setVisible(false);
                    c3.setVisible(false);
                    e4.setVisible(false);
                    p4.setVisible(false);
                    c4.setVisible(false);
                    e5.setVisible(false);
                    p5.setVisible(false);
                    c5.setVisible(false);
                    e6.setVisible(false);
                    p6.setVisible(false);
                    c6.setVisible(false);
                    e7.setVisible(false);
                    p7.setVisible(false);
                    c7.setVisible(false);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e2.clear();
                    e3.clear();
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p2.clear();
                    p3.clear();
                    p4.clear();
                    p5.clear();
                    p6.clear();
                    p7.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c2.setSelected(false);
                    c3.setSelected(false);
                    c4.setSelected(false);
                    c5.setSelected(false);
                    c6.setSelected(false);
                    c7.setSelected(false);
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
                    try {
                        s1 = getEmailForId(0);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    break;
                }
                case "2": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(false);
                    p3.setVisible(false);
                    c3.setVisible(false);
                    e4.setVisible(false);
                    p4.setVisible(false);
                    c4.setVisible(false);
                    e5.setVisible(false);
                    p5.setVisible(false);
                    c5.setVisible(false);
                    e6.setVisible(false);
                    p6.setVisible(false);
                    c6.setVisible(false);
                    e7.setVisible(false);
                    p7.setVisible(false);
                    c7.setVisible(false);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e3.clear();
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p3.clear();
                    p4.clear();
                    p5.clear();
                    p6.clear();
                    p7.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c3.setSelected(false);
                    c4.setSelected(false);
                    c5.setSelected(false);
                    c6.setSelected(false);
                    c7.setSelected(false);
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
                    try {
                        s1 = getEmailForId(0);
                        s2 = getEmailForId(0);
                    } catch (Exception e)
                    {
                    }
                    if (s1 != null)
                    {
                        e1.setText(s1);
                        e1.setDisable(true);
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    break;
                }
                case "3": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(false);
                    p4.setVisible(false);
                    c4.setVisible(false);
                    e5.setVisible(false);
                    p5.setVisible(false);
                    c5.setVisible(false);
                    e6.setVisible(false);
                    p6.setVisible(false);
                    c6.setVisible(false);
                    e7.setVisible(false);
                    p7.setVisible(false);
                    c7.setVisible(false);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e4.clear();
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p4.clear();
                    p5.clear();
                    p6.clear();
                    p7.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c4.setSelected(false);
                    c5.setSelected(false);
                    c6.setSelected(false);
                    c7.setSelected(false);
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    break;
                }
                case "4": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(false);
                    p5.setVisible(false);
                    c5.setVisible(false);
                    e6.setVisible(false);
                    p6.setVisible(false);
                    c6.setVisible(false);
                    e7.setVisible(false);
                    p7.setVisible(false);
                    c7.setVisible(false);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e5.clear();
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p5.clear();
                    p6.clear();
                    p7.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c5.setSelected(false);
                    c6.setSelected(false);
                    c7.setSelected(false);
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    break;
                }
                case "5": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(true);
                    p5.setVisible(true);
                    c5.setVisible(true);
                    e6.setVisible(false);
                    p6.setVisible(false);
                    c6.setVisible(false);
                    e7.setVisible(false);
                    p7.setVisible(false);
                    c7.setVisible(false);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e6.clear();
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p6.clear();
                    p7.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c6.setSelected(false);
                    c7.setSelected(false);
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        p5.setDisable(true);
                        c5.setDisable(true);
                    }
                    break;
                }
                case "6": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(true);
                    p5.setVisible(true);
                    c5.setVisible(true);
                    e6.setVisible(true);
                    p6.setVisible(true);
                    c6.setVisible(true);
                    e7.setVisible(false);
                    p7.setVisible(false);
                    c7.setVisible(false);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e7.clear();
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p7.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c7.setSelected(false);
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        p5.setDisable(true);
                        c5.setDisable(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        p6.setDisable(true);
                        c6.setDisable(true);
                    }
                    break;
                }
                case "7": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(true);
                    p5.setVisible(true);
                    c5.setVisible(true);
                    e6.setVisible(true);
                    p6.setVisible(true);
                    c6.setVisible(true);
                    e7.setVisible(true);
                    p7.setVisible(true);
                    c7.setVisible(true);
                    e8.setVisible(false);
                    p8.setVisible(false);
                    c8.setVisible(false);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e8.clear();
                    e9.clear();
                    e10.clear();
                    p8.clear();
                    p9.clear();
                    p10.clear();
                    c8.setSelected(false);
                    c9.setSelected(false);
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        p5.setDisable(true);
                        c5.setDisable(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        p6.setDisable(true);
                        c6.setDisable(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        p7.setDisable(true);
                        c7.setDisable(true);
                    }
                    break;
                }
                case "8": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(true);
                    p5.setVisible(true);
                    c5.setVisible(true);
                    e6.setVisible(true);
                    p6.setVisible(true);
                    c6.setVisible(true);
                    e7.setVisible(true);
                    p7.setVisible(true);
                    c7.setVisible(true);
                    e8.setVisible(true);
                    p8.setVisible(true);
                    c8.setVisible(true);
                    e9.setVisible(false);
                    p9.setVisible(false);
                    c9.setVisible(false);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e9.clear();
                    e10.clear();
                    p9.clear();
                    p10.clear();
                    c9.setSelected(false);
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        p5.setDisable(true);
                        c5.setDisable(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        p6.setDisable(true);
                        c6.setDisable(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        p7.setDisable(true);
                        c7.setDisable(true);
                    }
                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        p8.setDisable(true);
                        c8.setDisable(true);
                    }
                    break;
                }
                case "9": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(true);
                    p5.setVisible(true);
                    c5.setVisible(true);
                    e6.setVisible(true);
                    p6.setVisible(true);
                    c6.setVisible(true);
                    e7.setVisible(true);
                    p7.setVisible(true);
                    c7.setVisible(true);
                    e8.setVisible(true);
                    p8.setVisible(true);
                    c8.setVisible(true);
                    e9.setVisible(true);
                    p9.setVisible(true);
                    c9.setVisible(true);
                    e10.setVisible(false);
                    p10.setVisible(false);
                    c10.setVisible(false);
                    e10.clear();
                    p10.clear();
                    c10.setSelected(false);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        p5.setDisable(true);
                        c5.setDisable(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        p6.setDisable(true);
                        c6.setDisable(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        p7.setDisable(true);
                        c7.setDisable(true);
                    }
                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        p8.setDisable(true);
                        c8.setDisable(true);
                    }
                    if (s9 != null)
                    {
                        e9.setText(s9);
                        e9.setDisable(true);
                        p9.setDisable(true);
                        c9.setDisable(true);
                    }
                    break;
                }
                case "10": {
                    l1.setVisible(true);
                    l2.setVisible(true);
                    l3.setVisible(true);
                    l4.setVisible(true);
                    e1.setVisible(true);
                    p1.setVisible(true);
                    c1.setVisible(true);
                    e2.setVisible(true);
                    p2.setVisible(true);
                    c2.setVisible(true);
                    e3.setVisible(true);
                    p3.setVisible(true);
                    c3.setVisible(true);
                    e4.setVisible(true);
                    p4.setVisible(true);
                    c4.setVisible(true);
                    e5.setVisible(true);
                    p5.setVisible(true);
                    c5.setVisible(true);
                    e6.setVisible(true);
                    p6.setVisible(true);
                    c6.setVisible(true);
                    e7.setVisible(true);
                    p7.setVisible(true);
                    c7.setVisible(true);
                    e8.setVisible(true);
                    p8.setVisible(true);
                    c8.setVisible(true);
                    e9.setVisible(true);
                    p9.setVisible(true);
                    c9.setVisible(true);
                    e10.setVisible(true);
                    p10.setVisible(true);
                    c10.setVisible(true);
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
                        p1.setDisable(true);
                        c1.setDisable(true);
                    }
                    if (s2 != null)
                    {
                        e2.setText(s2);
                        e2.setDisable(true);
                        p2.setDisable(true);
                        c2.setDisable(true);
                    }
                    if (s3 != null)
                    {
                        e3.setText(s3);
                        e3.setDisable(true);
                        p3.setDisable(true);
                        c3.setDisable(true);
                    }
                    if (s4 != null)
                    {
                        e4.setText(s4);
                        e4.setDisable(true);
                        p4.setDisable(true);
                        c4.setDisable(true);
                    }
                    if (s5 != null)
                    {
                        e5.setText(s5);
                        e5.setDisable(true);
                        p5.setDisable(true);
                        c5.setDisable(true);
                    }
                    if (s6 != null)
                    {
                        e6.setText(s6);
                        e6.setDisable(true);
                        p6.setDisable(true);
                        c6.setDisable(true);
                    }
                    if (s7 != null)
                    {
                        e7.setText(s7);
                        e7.setDisable(true);
                        p7.setDisable(true);
                        c7.setDisable(true);
                    }
                    if (s8 != null)
                    {
                        e8.setText(s8);
                        e8.setDisable(true);
                        p8.setDisable(true);
                        c8.setDisable(true);
                    }
                    if (s9 != null)
                    {
                        e9.setText(s9);
                        e9.setDisable(true);
                        p9.setDisable(true);
                        c9.setDisable(true);
                    }
                    if (s10 != null)
                    {
                        e10.setText(s10);
                        e10.setDisable(true);
                        p10.setDisable(true);
                        c10.setDisable(true);
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
        String email = data.getString("Email");

  //  	for (int i=0; i < idList.length; i++)
  //  	{
  //  		int id = Integer.parseInt(idList[i]);
  //  		JSONObject data = referees.getJSONObject(idList[i]);
   // 		String email = data.getString("Email");
    		//Do Stuff Here
   // 	}
    	return email;
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
            String result = Client.fromServer.readLine();
            if (result.equals("Success")) {
                JOptionPane.showMessageDialog(null, "Database successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperUser.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Main Menu");
                stage.setScene(new Scene(root1));
                stage.show();
            } else if (result.equals("Failure")) {
                JOptionPane.showMessageDialog(null, "Database creation failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void overwrite1(ActionEvent event) throws IOException
    {
        e1.setDisable(false);
        p1.setDisable(false);
        c1.setDisable(false);
    }

    public void overwrite2(ActionEvent event) throws IOException
    {
        e2.setDisable(false);
        p2.setDisable(false);
        c2.setDisable(false);
    }

    public void overwrite3(ActionEvent event) throws IOException
    {
        e3.setDisable(false);
        p3.setDisable(false);
        c3.setDisable(false);
    }

    public void overwrite4(ActionEvent event) throws IOException
    {
        e4.setDisable(false);
        p4.setDisable(false);
        c4.setDisable(false);
    }

    public void overwrite5(ActionEvent event) throws IOException
    {
        e5.setDisable(false);
        p5.setDisable(false);
        c5.setDisable(false);
    }

    public void overwrite6(ActionEvent event) throws IOException
    {
        e6.setDisable(false);
        p6.setDisable(false);
        c6.setDisable(false);
    }

    public void overwrite7(ActionEvent event) throws IOException
    {
        e7.setDisable(false);
        p7.setDisable(false);
        c7.setDisable(false);
    }

    public void overwrite8(ActionEvent event) throws IOException
    {
        e8.setDisable(false);
        p8.setDisable(false);
        c8.setDisable(false);
    }

    public void overwrite9(ActionEvent event) throws IOException
    {
        e9.setDisable(false);
        p9.setDisable(false);
        c9.setDisable(false);
    }

    private boolean checkData()
    {
        try {
            if (Integer.parseInt(cb.getValue()) > Integer.parseInt(ref.getValue())) {
                JOptionPane.showMessageDialog(null, "You must have one referee per team.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "You must Select a number of teams and a number of referees", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        switch(cb.getValue())
        {
            case "2":
            {
                if (t1.getText().equals("") || t2.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "3":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "4":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "5":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "6":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "7":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "8":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "9":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals("") || t9.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
            case "10":
            {
                if (t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("") || t5.getText().equals("") || t6.getText().equals("") || t7.getText().equals("") || t8.getText().equals("") || t9.getText().equals("") || t10.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "You cannot enter blank team names. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                break;
            }
        }
        return true;
    }



}
