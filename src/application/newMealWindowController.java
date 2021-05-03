package application;

import application.domain.Aliment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class newMealWindowController extends FoodTabController {

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

    ObservableList<Aliment> alimentsForView = FXCollections.observableArrayList();

    public void initialize() {}

    public void setAliments(ObservableList<Aliment> aliments) {
        alimentComboBox.getItems().addAll(aliments);
    }

    public void addAliment() throws Exception {
        double alimentWeightHelper = Double.parseDouble(alimentWeight.getText()) / 100;

        String name = alimentComboBox.getValue().getName();
        double weight = alimentComboBox.getValue().getWeight() * alimentWeightHelper;
        double calories = alimentComboBox.getValue().getCalories() * alimentWeightHelper;
        double fat = alimentComboBox.getValue().getFat() * alimentWeightHelper;
        double carbs = alimentComboBox.getValue().getCarbohydrate() * alimentWeightHelper;
        double protein = alimentComboBox.getValue().getProtein() * alimentWeightHelper;
        double fiber = alimentComboBox.getValue().getFiber() * alimentWeightHelper;

        DOA doa = new DOA();
        CallableStatement myStmt1 = doa.connection.prepareCall("{call create_meal_table}");
        myStmt1.execute();

        //have to prevent SQL injections
        PreparedStatement myStmt = doa.connection.prepareStatement("insert into meal_table "
                + " (aliment_name, weight, calories, fat, carbs, protein, fiber)"
                + " values (?, ?, ?, ?, ?, ?, ?)");

        myStmt.setString(1, name);
        myStmt.setDouble(2, weight);
        myStmt.setDouble(3, calories);
        myStmt.setDouble(4, fat);
        myStmt.setDouble(5, carbs);
        myStmt.setDouble(6, protein);
        myStmt.setDouble(7, fiber);
        myStmt.execute();

        Aliment aliment = new Aliment(name, calories, fat, carbs, protein, fiber);
        aliment.setWeight(weight);

        alimentsForView.add(aliment);
        selectedAliments.setItems(alimentsForView);
    }

    public void finish() throws Exception {
        DOA doa = new DOA();
        PreparedStatement myStmt = doa.connection.prepareStatement("ALTER TABLE meal_table RENAME TO " + "`" + mealNameTextField.getText() + "`");
        myStmt.execute();
        selectedAliments.getItems().clear();
        mealNameTextField.clear();
    }

    public void deleteAliment() {

    }
}
