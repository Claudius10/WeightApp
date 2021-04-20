package application.domain;

import application.domain.Aliment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MealTest {

    HashMap<String, ArrayList<Aliment>> meals = new HashMap<>();

    public void addToMeal(String mealName, Aliment aliment, double weight) {

        String name = aliment.getName();
        double finalWeight = weight / 100.0;
        double calories = aliment.getCalories() * finalWeight;
        double fat = aliment.getFat() * finalWeight;
        double carbs = aliment.getCarbohydrate() * finalWeight;
        double prot = aliment.getProtein() * finalWeight;
        double fiber = aliment.getFiber() * finalWeight;

        Aliment alimentToAdd = new Aliment(name, calories, fat, carbs, prot, fiber);
        alimentToAdd.setWeight(weight);

        meals.putIfAbsent(name, new ArrayList<>());
        meals.get(name).add(alimentToAdd);
    }

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
}
