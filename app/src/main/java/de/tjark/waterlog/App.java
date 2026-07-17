package de.tjark.waterlog;

import javax.swing.JOptionPane;

import de.tjark.waterlog.service.DrinkManager;
import de.tjark.waterlog.service.SaveManager;
import de.tjark.waterlog.service.WaterCalculator;
import de.tjark.waterlog.ui.DialogManager;
import de.tjark.waterlog.ui.MainWindow;

public class App {

    public static void main(String[] args) {

        try {
            DrinkManager drinkManager = new DrinkManager();
            SaveManager saveManager = new SaveManager(drinkManager);
            WaterCalculator waterCalculator = new WaterCalculator(drinkManager);
            DialogManager dialogManager = new DialogManager(drinkManager);

            saveManager.createSaveFiles();

            new MainWindow(drinkManager, saveManager, waterCalculator, dialogManager);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Anwendung konnte nicht gestartet werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut. "
                            + "Falls das Problem bestehen bleibt, wenden Sie sich an den Entwickler.",
                    "Unerwarteter Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}