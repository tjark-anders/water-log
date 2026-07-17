package de.tjark.waterlog.service;

import de.tjark.waterlog.model.DrinkOption;

public class WaterCalculator {

    private DrinkManager drinkManager;

    public WaterCalculator(DrinkManager drinkManager) {
        this.drinkManager = drinkManager;
    }

    public int getCurrWater() {
        int currWater = 0;
        for (DrinkOption daylyDrink : drinkManager.getDrinkEntrys()) {
            currWater += daylyDrink.getSize() / 100.0 * daylyDrink.getWaterP();
        }
        return currWater;
    }
}
