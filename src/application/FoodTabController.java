package application;

import application.domain.Aliment;
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

public class FoodTabController {

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

    protected static ObservableList<Aliment> aliments = FXCollections.observableArrayList();

    public void initialize() {
        alimentsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        alimentsKcalCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
        alimentsFatCol.setCellValueFactory(new PropertyValueFactory<>("fat"));
        alimentsCarbsCol.setCellValueFactory(new PropertyValueFactory<>("carbohydrate"));
        alimentsProteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
        alimentsFiberCol.setCellValueFactory(new PropertyValueFactory<>("fiber"));

        loadAliments();
        alimentsTableView.setItems(aliments);

    }
    // Meals //

    public void newMealWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newMealWindow.fxml"));
        Parent root = loader.load();

        newMealWindowController mealWindowController = loader.getController();
        mealWindowController.setAliments(alimentObservableList());

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

    public ObservableList<Aliment> alimentObservableList() {
        return aliments;
    }

    public void deleteAliment() {
        aliments.remove(alimentsTableView.getSelectionModel().getSelectedItem());
        saveAliment(aliments,true);
    }

    public void saveAliment(ObservableList<Aliment> aliments, boolean remake) {

        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "aliment,calories,fat,carbs,protein,fiber";

        FileWriter fw = null;

        try {

            File newFile = new File("aliments.csv");

            if (newFile.exists() && !remake) {
                //Update current file
                fw = new FileWriter("aliments.csv",true);
            } else {
                fw = new FileWriter("aliments.csv");
                fw.append(FILE_HEADER);
            }

            for (Aliment aliment : aliments) {
                fw.append(NEW_LINE_SEPARATOR);
                fw.append(String.valueOf(aliment.getName()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(aliment.getCalories()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(aliment.getFat()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(aliment.getCarbohydrate()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(aliment.getProtein()));
                fw.append(COMMA_DELIMITER);
                fw.append(String.valueOf(aliment.getFiber()));
           }
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        } finally {
            try {
                assert fw != null;
                fw.flush();
                fw.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing FileWriter.");
                e.printStackTrace();
            }
        }
    }

    public void loadAliments()  {

        aliments.clear();
        String COMMA_DELIMITER = ",";
        int ALIMENT_NAME = 0;
        int ALIMENT_CALORIES = 1;
        int ALIMENT_FAT = 2;
        int ALIMENT_CARBS = 3;
        int ALIMENT_PROTEIN = 4;
        int ALIMENT_FIBER = 5;

        BufferedReader fileReader;

        try {

            File newFile = new File("aliments.csv");

            if (newFile.exists()) {

                fileReader = new BufferedReader(new FileReader("aliments.csv"));
                fileReader.readLine();
                String line;

                while ((line = fileReader.readLine()) != null) {
                    String[] tokens = line.split(COMMA_DELIMITER);
                    Aliment newAliment = new Aliment(String.valueOf(tokens[ALIMENT_NAME]), Double.parseDouble(tokens[ALIMENT_CALORIES]), Double.parseDouble(tokens[ALIMENT_FAT]), Double.parseDouble(tokens[ALIMENT_CARBS]), Double.parseDouble(tokens[ALIMENT_PROTEIN]), Double.parseDouble(tokens[ALIMENT_FIBER]));
                    aliments.add(newAliment);
                }
                fileReader.close();
            }
        } catch (Exception e) {
            System.out.println("Error reading aliments from CSV file");
            e.printStackTrace();
        }
    }
    // Aliments //
}


