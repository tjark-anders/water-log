package service;

import java.util.ArrayList;

import model.DrinkOption;

public class DrinkManager {

    private ArrayList<DrinkOption> presetDrinkList = new ArrayList<>();
    private ArrayList<DrinkOption> dailyDrinkList = new ArrayList<>();
    private ArrayList<DrinkOption> customDrinkList = new ArrayList<>();

    // add a selected drink to the daily drink List
    public void addDrink(DrinkOption drink) {
        dailyDrinkList.add(drink);
    }

    // remove the last added drink from the drink List
    public void removeLastDrink() {
        if (dailyDrinkList.size() > 0) {
            dailyDrinkList.remove(dailyDrinkList.size() - 1);
        }
    }

    // adds a selected drink to the custom Drink Options
    public void addCustomDrink(DrinkOption drink) {
        customDrinkList.add(drink);
    }

    // removes a selected drink from the custom Drink Options
    public void removeCustomDrink(DrinkOption drink) {
        customDrinkList.remove(drink);
    }

    // adds a selected drink to the custom Drink Options
    public void addPresetDrink(DrinkOption drink) {
        presetDrinkList.add(drink);
    }

    // Getter
    public ArrayList<DrinkOption> getDailyDrinks() {
        return dailyDrinkList;
    }

    public ArrayList<DrinkOption> getCustomDrinks() {
        return customDrinkList;
    }

    public ArrayList<DrinkOption> getPresetDrinks() {
        return presetDrinkList;
    }
}
