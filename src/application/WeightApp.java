package application;

import application.domain.Aliment;
import application.domain.Meal;
import application.domain.MealModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WeightApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("foodTab.fxml"));
        Scene main = new Scene(root);
        primaryStage.setScene(main);
        primaryStage.setTitle("App");
        primaryStage.setMinWidth(root.minWidth(-1));
        primaryStage.setMinHeight(root.minHeight(-1));
        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(WeightApp.class);
        Aliment pan = new Aliment("pan", 200, 10, 45, 10 , 5);
        Aliment pan2 = new Aliment("pan2", 200, 10, 45, 10 , 5);
        ObservableList<Aliment> alimentsForMeal = FXCollections.observableArrayList();
        alimentsForMeal.add(pan);
        alimentsForMeal.add(pan2);

        MealModel meal = new MealModel();
        meal.add("meal1", alimentsForMeal);


        System.out.println(meal.getMealName("meal1") + meal.aliments("meal1"));
        System.out.println(meal.aliments("meal1"));


    }
}
