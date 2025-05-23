package application.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MealModel {

    private String mealName;
    ObservableList<Aliment> alimentList;
    private int id;

    public MealModel(String name) {
        this.mealName = name;
        this.alimentList = FXCollections.observableArrayList();
    }

    public void setMealName(String name) {
        this.mealName = name;
    }

    public void add(Aliment aliment) {
        alimentList.addAll(aliment);
    }

    public ObservableList<Aliment> getAliments() {
       return this.alimentList;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
       return this.id;
    }
    public String getName() {
        return this.mealName;
    }

    public double getWeight() {
        return this.alimentList.stream()
                .map(Aliment::getWeight)
                .reduce(0.0, Double::sum);
    }

    public double getCalories() {
        return this.alimentList.stream()
                .map(Aliment::getCalories)
                .reduce(0.0, Double::sum);
    }

    public double getFat() {
        return this.alimentList.stream()
                .map(Aliment::getFat)
                .reduce(0.0, Double::sum);
    }

    public double getCarbohydrate() {
        return this.alimentList.stream()
                .map(Aliment::getCarbohydrate)
                .reduce(0.0, Double::sum);
    }

    public double getProtein() {
        return this.alimentList.stream()
                .map(Aliment::getProtein)
                .reduce(0.0, Double::sum);
    }

    public double getFiber() {
        return this.alimentList.stream()
                .map(Aliment::getFiber)
                .reduce(0.0, Double::sum);
    }

    public String toString() {
        return this.mealName + this.alimentList;
    }
}