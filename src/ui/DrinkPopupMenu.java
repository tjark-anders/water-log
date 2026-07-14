package ui;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import model.DrinkOption;
import service.DrinkManager;
import service.SaveManager;
import util.Validator;

public class DrinkPopupMenu extends JPopupMenu {

    private DrinkManager drinkManager;
    private SaveManager saveManager;
    private MainWindow mainWindow;
    private DialogManager dialogManager;

    public DrinkPopupMenu(
            DrinkOption drink,
            DrinkManager drinkManager,
            SaveManager saveManager,
            MainWindow mainWindow,
            DialogManager dialogManager) {

        this.drinkManager = drinkManager;
        this.saveManager = saveManager;
        this.dialogManager = dialogManager;
        this.mainWindow = mainWindow;

        JMenuItem item250 = new JMenuItem("250 ml");
        JMenuItem item330 = new JMenuItem("330 ml");
        JMenuItem item500 = new JMenuItem("500 ml");
        JMenuItem itemCustomSize = new JMenuItem("Andere Größe");

        add(item250);
        add(item330);
        add(item500);
        addSeparator();
        add(itemCustomSize);

        item250.addActionListener(e -> {
            addDrink(drink, 250);
        });

        item330.addActionListener(e -> {
            addDrink(drink, 330);
        });

        item500.addActionListener(e -> {
            addDrink(drink, 500);
        });

        itemCustomSize.addActionListener(e -> {

            int size = 0;
            size = dialogManager.showNumberDialog(size, "Größe: ");

            addDrink(drink, size);
        });

    }

    private void addDrink(DrinkOption drink, int size) {
        DrinkOption entry = new DrinkOption(
                drink.getName(),
                drink.getIcon(),
                size,
                drink.getWaterP());

        drinkManager.addDrinkEntry(entry);
        saveManager.saveDrinkEntrys();
        mainWindow.updateEntryButtons();
        mainWindow.updateWaterLabel();
    }

}
