package application;

import application.domain.Aliment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class newMealWindowController extends MainController {

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
    protected Label totalFat, totalCarbs, totalProtein, totalKcal, totalWeight, totalFiber;

    @FXML
    protected TextField mealNameTextField;

    ObservableList<Aliment> alimentsForView = FXCollections.observableArrayList();

    public void initialize() {}

    public void setAliments(ObservableList<Aliment> aliments) {
        alimentComboBox.getItems().addAll(aliments);
    }

    public void addAliment() throws Exception {
        double alimentWeightHelper = Double.parseDouble(alimentWeight.getText()) / 100;

        int id = alimentComboBox.getValue().getId();
        String name = alimentComboBox.getValue().getName();
        double weight = alimentComboBox.getValue().getWeight() * alimentWeightHelper;
        double calories = alimentComboBox.getValue().getCalories() * alimentWeightHelper;
        double fat = alimentComboBox.getValue().getFat() * alimentWeightHelper;
        double carbs = alimentComboBox.getValue().getCarbohydrate() * alimentWeightHelper;
        double protein = alimentComboBox.getValue().getProtein() * alimentWeightHelper;
        double fiber = alimentComboBox.getValue().getFiber() * alimentWeightHelper;

        Aliment aliment = new Aliment(name, calories, fat, carbs, protein, fiber);
        aliment.setWeight(weight);
        aliment.setId(id);

        alimentsForView.add(aliment);
        selectedAliments.setItems(alimentsForView);

        DOA doa = new DOA();
        CallableStatement myStmt = doa.connection.prepareCall("{call create_meal_table}");
        myStmt.execute();

        PreparedStatement myStmt1 = doa.connection.prepareStatement("insert into meal_table "
                + " (aliment_name, weight, calories, fat, carbs, protein, fiber)"
                + " values (?, ?, ?, ?, ?, ?, ?)");

        myStmt1.setString(1, name);
        myStmt1.setDouble(2, weight);
        myStmt1.setDouble(3, calories);
        myStmt1.setDouble(4, fat);
        myStmt1.setDouble(5, carbs);
        myStmt1.setDouble(6, protein);
        myStmt1.setDouble(7, fiber);
        myStmt1.execute();
    }

    public void deleteAlimentFromMeal() throws Exception {
        Aliment aliment = selectedAliments.getSelectionModel().getSelectedItem();
        String alimentName = aliment.getName();

        DOA doa = new DOA();
        Statement myStmt = doa.connection.createStatement();
        ResultSet myRs = myStmt.executeQuery("select id from meal_table WHERE (`aliment_name` =" + "'" + alimentName + "');");

        while (myRs.next()) {
            int id = myRs.getInt("id");
            aliment.setId(id);
        }

        int id = aliment.getId();

        PreparedStatement myStmt2 = doa.connection.prepareStatement("delete from meal_table where id=?");
        myStmt2.setInt(1, id);
        myStmt2.execute();

        alimentsForView.remove(aliment);
        selectedAliments.refresh();
    }

    public void finish() throws Exception {
        DOA doa = new DOA();
        PreparedStatement myStmt = doa.connection.prepareStatement("ALTER TABLE meal_table RENAME TO " + "`" + mealNameTextField.getText() + "`");
        myStmt.execute();

        selectedAliments.getItems().clear();
        mealNameTextField.clear();
        refresh();
    }
}