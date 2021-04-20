package application.domain;

import java.util.ArrayList;

public class Meal {

    ArrayList<Aliment> meal = new ArrayList<>();

    public void addAliment(Aliment aliment, double weight) {
        String name = aliment.getName();
        double finalWeight = weight * 100;
        double calories = aliment.getCalories() * weight;
        double fat = aliment.getFat() * weight;
        double carbs = aliment.getCarbohydrate() * weight;
        double prot = aliment.getProtein() * weight;
        double fiber = aliment.getFiber() * weight;

        this.meal.add(new Aliment(name, calories, fat, carbs, prot, fiber));
    }

    public void printMealContents() {
        this.meal.stream().forEach(aliment -> {
            System.out.println(aliment);
        });
        // same as this.food.stream().forEach(System.out::println);
    }

    public double getFat() {
        double totalFat = this.meal.stream()
                .map(aliment -> aliment.getFat())
                .reduce(0.0, (previousSum, aliment) -> previousSum + aliment);
        return totalFat;
    }

    public double getCarbs() {
        double totalCarbs = this.meal.stream()
                .map(aliment -> aliment.getCarbohydrate())
                .reduce(0.0, (previousSum, aliment) -> previousSum + aliment);
        return totalCarbs;
    }

    public double getProtein() {
        double totalProtein = this.meal.stream()
                .map(aliment -> aliment.getProtein())
                .reduce(0.0, (previousSum, aliment) -> previousSum + aliment);
        return totalProtein;
    }

    public double getFiber() {
        double totalFiber = this.meal.stream()
                .map(aliment -> aliment.getFiber())
                .reduce(0.0, (previousSum, aliment) -> previousSum + aliment);
        return totalFiber;
    }

    public double getCalories() {
        double totalCalories = this.meal.stream()
                .map(aliment -> aliment.getCalories())
                .reduce(0.0, (previousSum, aliment) -> previousSum + aliment);
        return totalCalories;
    }

    public String getNutriValues() {
        return "Total calories: " + getCalories();
    }

}
