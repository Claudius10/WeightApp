package application;

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

    public void addAliment() throws Exception {
        alimentToDB(newAlimentSetName.getText(), newAlimentSetCal.getText(), newAlimentSetFat.getText(),
                newAlimentSetCarbs.getText(), newAlimentSetProtein.getText(), newAlimentSetFiber.getText());
        alimentList(aliments);
    }
}
