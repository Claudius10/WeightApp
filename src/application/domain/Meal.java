package application.domain;

import application.domain.Aliment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Meal {

    ObservableMap<String, ObservableList<Aliment>> meal = FXCollections.observableHashMap();

    public void add(String mealName, ObservableList<Aliment> aliments) {

        meal.putIfAbsent(mealName, aliments);

    }

    /*
    public Collection<ArrayList<Aliment>> printMealContents() {
        return meals.values();
    }

    public void printMealContents1() {
        for (ArrayList<Aliment> aliments : this.meals.values()) {
            System.out.println(aliments);
        }
    }

    public void printMealContents2() {
        for (ArrayList<Aliment> aliments : this.meals.values()) {
            aliments.stream()
                    .map(aliment -> aliment.getName() + " " + aliment.getWeight() + " " + aliment.getCalories())
                    .forEach(aliment -> System.out.println(aliment));
        }
    }

     */
}
