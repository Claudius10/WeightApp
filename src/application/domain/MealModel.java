package application.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.ArrayList;

public class MealModel {

   private ObservableMap<String, ObservableList<Aliment>> MealModel;

   public MealModel() {
       this.MealModel = FXCollections.observableHashMap();
   }

   public void add(String name, ObservableList<Aliment> aliments) {
       this.MealModel.putIfAbsent(name, aliments);
   }

   public ObservableList<Aliment> aliments(String name) {
       return this.MealModel.get(name);
   }

   public String getMealName(String name) {
       for (String key : MealModel.keySet()) {
           if (name.matches(key)) {
               return key;
           }
       }
       return "Meal not found";
   }


}
