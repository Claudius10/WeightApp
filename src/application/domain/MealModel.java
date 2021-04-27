package application.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class MealModel {

   private ObservableMap<String, Aliment> MealModel;
   public MealModel() {
       this.MealModel = FXCollections.observableHashMap();
   }

   public void add(String name, Aliment aliment) {
       this.MealModel.put(name, aliment);
   }

   public Aliment aliments(String name) {
       return this.MealModel.get(name);
   }

   public String alimentGetName(String name) {
       return this.aliments(name).getName();
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
