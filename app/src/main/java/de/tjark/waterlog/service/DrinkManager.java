package de.tjark.waterlog.service;

import java.util.ArrayList;

import de.tjark.waterlog.model.DrinkOption;

public class DrinkManager {

    private ArrayList<DrinkOption> drinkEntryList = new ArrayList<>();
    private ArrayList<DrinkOption> drinkOptionList = new ArrayList<>();

    public void addDrinkEntry(DrinkOption drink) {
        drinkEntryList.add(drink);
    }

    public void removeLastDrinkEntry() {
        if (drinkEntryList.size() > 0) {
            drinkEntryList.remove(drinkEntryList.size() - 1);
        }
    }

    public void removeDrinkEntry(DrinkOption drink) {
        drinkEntryList.remove(drink);
    }

    public void removeAllDrinkEntrys() {
        drinkEntryList.clear();
    }

    public void addDrinkOption(DrinkOption drink) {
        drinkOptionList.add(drink);
    }

    public void removeDrinkOption(DrinkOption drink) {
        drinkOptionList.remove(drink);
    }

    // Getter
    public ArrayList<DrinkOption> getDrinkEntrys() {
        return drinkEntryList;
    }

    public ArrayList<DrinkOption> getDrinkOptions() {
        return drinkOptionList;
    }
}
