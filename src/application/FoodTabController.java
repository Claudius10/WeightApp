package application;

import application.domain.Aliment;
import application.domain.Meal;
import application.domain.MealModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.sql.*;

public class FoodTabController extends DOA {

    @FXML
    protected AnchorPane app, foodTab, foodButtonBar;

    @FXML
    protected TabPane mainWindow;

    @FXML
    protected Tab summaryTabLabel, foodTabLabel;

    @FXML
    protected Label alimentsLabel;

    @FXML
    protected Button deleteButton, newButton, newMealWindow;

    @FXML
    protected TableView<Aliment> alimentsTableView;

    @FXML
    protected TableColumn<Aliment, String> alimentsNameCol;

    @FXML
    protected TableColumn<Aliment, Double> alimentsKcalCol, alimentsFatCol, alimentsCarbsCol, alimentsProteinCol, alimentsFiberCol;

    @FXML
    protected TreeTableView<MealModel> mealTreeTableView;

    @FXML
    protected TreeTableColumn<MealModel, String> mealNameCol;

    @FXML
    protected TreeTableColumn<MealModel, Double> mealKcalCol, mealFatCol, mealCarbsCol, mealProteinCol, mealFiberCol;

    TreeItem<String> mealRoot = new TreeItem<>("Meals");

    TreeItem<String> mealName = new TreeItem<>();
    TreeItem<String> mealCalories = new TreeItem<>();
    TreeItem<String> mealFat = new TreeItem<>();
    TreeItem<String> mealCarbs = new TreeItem<>();
    TreeItem<String> mealProtein = new TreeItem<>();
    TreeItem<String> mealFiber = new TreeItem<>();

    TreeItem<String> alimentsRoot = new TreeItem<>("Aliments");

    TreeItem<String> alimentName = new TreeItem<>();
    TreeItem<String> alimentCalories = new TreeItem<>();
    TreeItem<String> alimentFat = new TreeItem<>();
    TreeItem<String> alimentCarbs = new TreeItem<>();
    TreeItem<String> alimentProtein = new TreeItem<>();
    TreeItem<String> alimentFiber = new TreeItem<>();

    protected static ObservableList<Aliment> aliments = FXCollections.observableArrayList();
    protected static ObservableList<Aliment> alimentsInMeal = FXCollections.observableArrayList();
    protected static MealModel meal = new MealModel();

    public FoodTabController() throws Exception {}

    public void initialize() throws SQLException {
        alimentsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        alimentsKcalCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
        alimentsFatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
        alimentsCarbsCol.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
        alimentsProteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
        alimentsFiberCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));

        alimentsTableView.setItems(alimentList(aliments));

        mealRoot.getChildren().setAll(mealName, mealCalories, mealFat, mealCarbs, mealProtein, mealFiber);
        alimentsRoot.getChildren().setAll(alimentName, alimentCalories, alimentFat, alimentCarbs, alimentProtein, alimentFiber);
    }
    // Meals //

    public void newMealWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newMealWindow.fxml"));
        Parent root = loader.load();

        newMealWindowController mealWindowController = loader.getController();
        //mealWindowController.setAliments();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    // Meals //

    // Aliments //


    public void newAlimentWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newAlimentWindow.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    protected void alimentToDB(String name, String calories, String fat, String carbs, String protein, String fiber) throws SQLException {
        int weight = 100;
        //connect to database

        PreparedStatement myStmt = connection.prepareStatement("insert into aliments "
                + " (name, weight, calories, fat, carbs, protein, fiber)"
                + " values (?, ?, ?, ?, ?, ?, ?)");

        myStmt.setString(1, name);
        myStmt.setDouble(2, weight);
        myStmt.setDouble(3, Double.parseDouble(calories));
        myStmt.setDouble(4, Double.parseDouble(fat));
        myStmt.setDouble(5, Double.parseDouble(carbs));
        myStmt.setDouble(6, Double.parseDouble(protein));
        myStmt.setDouble(7, Double.parseDouble(fiber));

        myStmt.executeUpdate();
    }

    protected ObservableList<Aliment> alimentList(ObservableList<Aliment> aliments) throws SQLException {
        aliments.clear();
        Statement myStmt = connection.createStatement();
        ResultSet myRs = myStmt.executeQuery("select * from aliments");

        while (myRs.next()) {
            int id = myRs.getInt("id");
            String name = myRs.getString("name");
            double calories = myRs.getDouble("calories");
            double fat = myRs.getDouble("fat");
            double carbs = myRs.getDouble("carbs");
            double protein = myRs.getDouble("protein");
            double fiber = myRs.getDouble("fiber");
            Aliment aliment = new Aliment(name, calories, fat, carbs, protein, fiber);
            aliment.setId(id);
            aliments.add(aliment);
        }
        return aliments;
    }

    public void deleteAliment() throws SQLException {
        Aliment aliment = alimentsTableView.getSelectionModel().getSelectedItem();
        int id = aliment.getId();
        PreparedStatement myStmt = connection.prepareStatement("delete from aliments where id=?");
        myStmt.setInt(1, id);
        myStmt.executeUpdate();
        alimentList(aliments);
    }

    // Aliments //
}


