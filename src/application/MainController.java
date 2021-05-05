package application;

import application.domain.Aliment;
import application.domain.MealModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.sql.*;

public class MainController {

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
    protected TreeTableView<Object> mealsTreeTableView = new TreeTableView<>();

    @FXML
    protected TreeTableColumn<Object, Object> nameCol, weightCol, kcalCol, fatCol, carbsCol, proteinCol, fiberCol;

    protected static Meal meals = new Meal();
    protected static TreeItem<Object> rootTreeItem = new TreeItem<>();
    protected static TreeItem<Object> mealsInTreeTableView = new TreeItem<>();
    protected static TreeItem<Object> alimentsInTreeTableView = new TreeItem<>();

    protected static ObservableList<Aliment> aliments = FXCollections.observableArrayList();

    public void initialize() throws Exception {
        alimentsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        alimentsKcalCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
        alimentsFatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
        alimentsCarbsCol.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
        alimentsProteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
        alimentsFiberCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));

        alimentsTableView.setItems(alimentList(aliments));

        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        weightCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("weight"));
        kcalCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("calories"));
        fatCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("fat"));
        carbsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("carbohydrate"));
        proteinCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("protein"));
        fiberCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("fiber"));

        setMeals();

        mealsTreeTableView.setRoot(rootTreeItem);
        mealsTreeTableView.setShowRoot(false);
    }

    // Meals //
    public void refresh() throws Exception {
        rootTreeItem.getChildren().clear();
        setMeals();
    }

    public void deleteMeal() throws Exception {
        TreeItem<Object> selectedMeal = mealsTreeTableView.getSelectionModel().getSelectedItem();
        MealModel meal = (MealModel) selectedMeal.getValue();
        String mealName = meal.getName();

        DOA doa = new DOA();
        PreparedStatement myStmt = doa.connection.prepareStatement("DROP TABLE " + mealName);
        myStmt.execute();

        selectedMeal.getParent().getChildren().remove(selectedMeal);
    }

    public void setMeals() throws Exception {
        getMealsFromDB(meals);
        for (MealModel meals : meals.getMealList()) {
            rootTreeItem.getChildren().addAll(mealsInTreeTableView = new TreeItem<>(meals));

            for (Aliment aliments : meals.getAliments()) {
                mealsInTreeTableView.getChildren().addAll(alimentsInTreeTableView = new TreeItem<>(aliments));
            }
        }
    }

    public void getMealsFromDB(Meal mealsDB) throws Exception {
        DOA doa = new DOA();
        DatabaseMetaData md = doa.connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = md.getTables(null, null, "%", types);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            //bug: meal duplicates in meals FIXED
            //bug 2: can't get meal names that have spaces in them
            if (!name.equals("aliments") && !meals.getMealNames().contains(name)) {
                MealModel meal = new MealModel(name);
                getAlimentsFromDBForMeal(meal);
                mealsDB.add(meal);
            }
        }
    }

    public void getAlimentsFromDBForMeal(MealModel meal) throws Exception {
        DOA doa = new DOA();
        Statement myStmt = doa.connection.createStatement();
        ResultSet myRs = myStmt.executeQuery("select * from " + meal.getName());

        while (myRs.next()) {
            int id = myRs.getInt("id");
            String name = myRs.getString("aliment_name");
            double weight = myRs.getDouble("weight");
            double calories = myRs.getDouble("calories");
            double fat = myRs.getDouble("fat");
            double carbs = myRs.getDouble("carbs");
            double protein = myRs.getDouble("protein");
            double fiber = myRs.getDouble("fiber");
            Aliment aliment = new Aliment(name, calories, fat, carbs, protein, fiber);
            aliment.setId(id);
            aliment.setWeight(weight);
            meal.add(aliment);
        }
    }

    public void newMealWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newMealWindow.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        newMealWindowController mealWindowController = loader.getController();
        mealWindowController.setAliments(aliments);

        DOA doa = new DOA();
        CallableStatement myStmt = doa.connection.prepareCall("{call delete_meal_table}");
        stage.setOnCloseRequest(windowEvent -> {
            try {
                myStmt.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

    protected void alimentToDB(String name, String calories, String fat, String carbs, String protein, String fiber) throws Exception {
        int weight = 100;

        DOA doa = new DOA();
        PreparedStatement myStmt = doa.connection.prepareStatement("insert into aliments "
                + " (aliment_name, weight, calories, fat, carbs, protein, fiber)"
                + " values (?, ?, ?, ?, ?, ?, ?)");

        myStmt.setString(1, name);
        myStmt.setDouble(2, weight);
        myStmt.setDouble(3, Double.parseDouble(calories));
        myStmt.setDouble(4, Double.parseDouble(fat));
        myStmt.setDouble(5, Double.parseDouble(carbs));
        myStmt.setDouble(6, Double.parseDouble(protein));
        myStmt.setDouble(7, Double.parseDouble(fiber));
        myStmt.execute();
    }

    protected ObservableList<Aliment> alimentList(ObservableList<Aliment> aliments) throws Exception {
        aliments.clear();

        DOA doa = new DOA();
        Statement myStmt = doa.connection.createStatement();
        ResultSet myRs = myStmt.executeQuery("select * from aliments");

        while (myRs.next()) {
            int id = myRs.getInt("id");
            String name = myRs.getString("aliment_name");
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

    public void deleteAliment() throws Exception {
        Aliment aliment = alimentsTableView.getSelectionModel().getSelectedItem();
        int id = aliment.getId();

        DOA doa = new DOA();
        PreparedStatement myStmt = doa.connection.prepareStatement("delete from aliments where id=?");
        myStmt.setInt(1, id);
        myStmt.executeUpdate();

        alimentList(aliments);
    }

    // Aliments //
}