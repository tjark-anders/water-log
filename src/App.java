import java.nio.file.Files;

import service.DrinkManager;
import service.SaveManager;
import service.WaterCalculator;
import ui.DialogManager;
import ui.MainWindow;
import util.IconLoader;

public class App {

    public static void main(String[] args) throws Exception {

        DrinkManager drinkManager = new DrinkManager();
        SaveManager saveManager = new SaveManager(drinkManager);
        WaterCalculator waterCalculator = new WaterCalculator(drinkManager);
        DialogManager dialogManager = new DialogManager(drinkManager);

        saveManager.createSaveFiles();
        IconLoader.loadDrinkIcons();

        new MainWindow(drinkManager, saveManager, waterCalculator, dialogManager);
    }

}