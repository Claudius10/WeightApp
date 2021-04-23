package application;

import application.domain.Aliment;
import application.domain.Meal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class newMealWindowController extends Meal {

    @FXML
    protected AnchorPane newMealPane;

    @FXML
    protected ComboBox<Aliment> alimentComboBox;

    @FXML
    protected TextField alimentWeight;

    @FXML
    protected ListView<Aliment> selectedAliments;

    @FXML
    protected Button addAlimentToMeal, deleteAlimentFromMeal, mealFinishButton;

    @FXML
    protected Label mealCalories, mealFat, mealCarbs, mealProtein, mealFiber;

    @FXML
    protected TextField mealNameTextField;

    //This is for meal obsHashMap
    ObservableList<Aliment> alimentsForMeal = FXCollections.observableArrayList();

    public void initialize() {
    }

    public void setAliments(ObservableList<Aliment> aliments) {
        alimentComboBox.getItems().addAll(aliments);
    }

    public void addAliment() {
        double alimentWeightHelper = Double.parseDouble(alimentWeight.getText()) / 100;

        String name = alimentComboBox.getValue().getName();
        double weight = alimentComboBox.getValue().getWeight() * alimentWeightHelper;
        double calories = alimentComboBox.getValue().getCalories() * alimentWeightHelper;
        double fat = alimentComboBox.getValue().getFat() * alimentWeightHelper;
        double carbs = alimentComboBox.getValue().getCarbohydrate() * alimentWeightHelper;
        double protein = alimentComboBox.getValue().getProtein() * alimentWeightHelper;
        double fiber = alimentComboBox.getValue().getFiber() * alimentWeightHelper;

        Aliment aliment = new Aliment(name, calories, fat, carbs, protein, fiber);
        aliment.setWeight(weight);

        alimentsForMeal.add(aliment);
        selectedAliments.setItems(alimentsForMeal);
    }


}
