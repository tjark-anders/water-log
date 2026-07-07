package service;

import java.util.ArrayList;

import model.DrinkOption;

public class DrinkManager {

    private ArrayList<DrinkOption> dailyDrinkList = new ArrayList<>();
    private ArrayList<DrinkOption> drinkOptionList = new ArrayList<>();

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

    public void removeAllDrinks() {
        dailyDrinkList.clear();
    }

    // adds a selected drink to the custom Drink Options
    public void addDrinkOption(DrinkOption drink) {
        drinkOptionList.add(drink);
    }

    // removes a selected drink from the custom Drink Options
    public void removeDrinkOption(DrinkOption drink) {
        drinkOptionList.remove(drink);
    }

    // Getter
    public ArrayList<DrinkOption> getDailyDrinks() {
        return dailyDrinkList;
    }

    public ArrayList<DrinkOption> getDrinkOptions() {
        return drinkOptionList;
    }
}
