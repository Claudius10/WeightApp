package application;

import application.domain.Aliment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class newAlimentWindowController extends FoodTabController {

    @FXML
    protected Pane newAlimentPane;

    @FXML
    protected TextField newAlimentSetName, newAlimentSetCal, newAlimentSetFat, newAlimentSetCarbs, newAlimentSetProtein, newAlimentSetFiber;

    @FXML
    protected Button addButton;

    public newAlimentWindowController() throws Exception {
    }

    public void initialize() {
    }

    public void addAliment() throws Exception {
        alimentToDB(newAlimentSetName.getText(), newAlimentSetCal.getText(), newAlimentSetFat.getText(),
                newAlimentSetCarbs.getText(), newAlimentSetProtein.getText(), newAlimentSetFiber.getText());
        alimentList(aliments);
    }
}
