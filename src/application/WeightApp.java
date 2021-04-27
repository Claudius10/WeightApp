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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class WeightApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("foodTab.fxml")));
        Scene main = new Scene(root);
        primaryStage.setScene(main);
        primaryStage.setTitle("App");
        primaryStage.setMinWidth(root.minWidth(-1));
        primaryStage.setMinHeight(root.minHeight(-1));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(WeightApp.class);

    }
}
