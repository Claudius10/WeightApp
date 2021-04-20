package application;

import application.domain.Aliment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class newAlimentWindowController extends FoodTabController {

    @FXML
    protected Pane newAlimentPane;

    @FXML
    protected TextField newAlimentSetName, newAlimentSetCal, newAlimentSetFat, newAlimentSetCarbs, newAlimentSetProtein, newAlimentSetFiber;

    @FXML
    protected Button addButton;

    public void initialize() {

    }

    public void addAliment() {
        Aliment aliment =  new Aliment(
                newAlimentSetName.getText(),
                Double.parseDouble(newAlimentSetCal.getText()),
                Double.parseDouble(newAlimentSetFat.getText()),
                Double.parseDouble(newAlimentSetCarbs.getText()),
                Double.parseDouble(newAlimentSetProtein.getText()),
                Double.parseDouble(newAlimentSetFiber.getText())
        );
        ObservableList<Aliment> newAlimentList = FXCollections.observableArrayList();
        newAlimentList.add(aliment);
        
        saveAliment(newAlimentList,false);
        loadAliments();
    }
}
