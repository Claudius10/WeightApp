package application.domain;

import java.util.Objects;

public class Aliment {

    private int id;
    private String name;
    private double weight, calories, fat, carbohydrate, protein, fiber;

    public Aliment(String name, double calories, double fat, double carbohydrate, double protein, double fiber) {
        this.name = name;
        this.weight = 100;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fiber = fiber;
    }

    public int getId () {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aliment)) return false;
        Aliment aliment = (Aliment) o;
        return weight == aliment.weight && calories == aliment.calories && Double.compare(aliment.fat, fat) == 0 && Double.compare(aliment.carbohydrate, carbohydrate) == 0 && Double.compare(aliment.protein, protein) == 0 && Double.compare(aliment.fiber, fiber) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, calories, fat, carbohydrate, protein, fiber);
    }

    @Override
    public String toString() {
        return this.name + "," + this.calories + "," + this.fat + "," + this.carbohydrate + "," + this.protein + "," + this.fiber;
    }
}


