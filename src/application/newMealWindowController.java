package application;

import application.domain.Aliment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class newMealWindowController  {

    @FXML
    private AnchorPane newMealPane;

    @FXML
    private TextField alimentWeight;

    @FXML
    private ComboBox<Aliment> alimentComboBox;

    @FXML
    private ListView<?> currentAliments;

    @FXML
    private Button addAlimentToMeal, deleteAlimentFromMeal, mealFinishButton;

    @FXML
    private Label mealCalories, mealFat, mealCarbs, mealProtein, mealFiber;

    @FXML
    private TextField mealNameTextField;

    public void initialize() {
    }

    public void setAliments(ObservableList<Aliment> aliments) {
        alimentComboBox.getItems().addAll(aliments);
    }
}
