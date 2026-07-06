import java.nio.file.Files;

import service.DrinkManager;
import service.SaveManager;
import service.WaterCalculator;
import ui.DialogManager;
import ui.MainWindow;

public class App {

    public static void main(String[] args) throws Exception {

        DrinkManager drinkManager = new DrinkManager();
        SaveManager saveManager = new SaveManager(drinkManager);
        WaterCalculator waterCalculator = new WaterCalculator(drinkManager);
        DialogManager dialogManager = new DialogManager(drinkManager);

        Files.createDirectories(saveManager.getBasePath());

        if (!Files.exists(saveManager.getDailyLogPath())) {
            Files.createFile(saveManager.getDailyLogPath());
        }
        if (!Files.exists(saveManager.getCustomLogPath())) {
            Files.createFile(saveManager.getCustomLogPath());
        }

        new MainWindow(drinkManager, saveManager, waterCalculator, dialogManager);
    }

}