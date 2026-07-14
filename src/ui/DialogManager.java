package ui;

import javax.swing.JOptionPane;

import model.DrinkOption;
import service.DrinkManager;
import util.DrinkIcon;
import util.Validator;

public class DialogManager {

    private DrinkManager drinkManager;

    public DialogManager(DrinkManager drinkManager) {
        this.drinkManager = drinkManager;
    }

    public void showEditDrinkEntrysDialog(DrinkOption drink) {
        String name = drink.getName();
        int size = drink.getSize();
        int waterP = drink.getWaterP();

        // Name
        name = showWordDialog(name, "Name: ");
        if (name.equals(null)) {
            return;
        }
        drink.setName(name);

        // size
        size = showNumberDialog(size, "Größe: ");
        if (size == 0) {
            return;
        }
        drink.setSize(size);

        // WaterP
        waterP = showNumberDialog(waterP, "WaterP: ");
        if (waterP == 0) {
            return;
        }
        drink.setWaterP(waterP);
    }

    public void showEditDrinkOptionDialog(DrinkOption drink) {
        String name = drink.getName();
        int waterP = drink.getWaterP();

        // Name
        name = showWordDialog(name, "Name: ");
        if (name.equals(null)) {
            return;
        }
        drink.setName(name);

        // Water
        waterP = showNumberDialog(waterP, "WaterP: ");
        if (waterP == 0) {
            return;
        }
        drink.setWaterP(waterP);
    }

    public void showCreateDrinkMenu(boolean quickAdd) {

        String name = "";
        int waterP = 0;

        name = showWordDialog(name, "Name: ");
        if (name.equals(null)) {
            return;
        }

        waterP = showNumberDialog(waterP, "Water%: ");
        if (waterP == 0) {
            return;
        }

        if (quickAdd) {
            drinkManager.addDrinkEntry(new DrinkOption(name, DrinkIcon.DEFAULT_ICON, 0, waterP));
        } else {
            drinkManager.addDrinkOption(new DrinkOption(name, DrinkIcon.DEFAULT_ICON, 0, waterP));
        }
    }

    // --------------------- helper ---------------------------

    public String showWordDialog(String word, String text) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, text, word);

            if (input == null)
                return null;

            String err = Validator.validateString(input, 20);

            if (!err.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        err,
                        "Eingabefehler",
                        JOptionPane.INFORMATION_MESSAGE);
                continue;
            }
            word = input;
            return word;
        }
    }

    public int showNumberDialog(int number, String text) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, text, number);

            if (input == null)
                return 0;

            String err = Validator.validateInt(input, 1, 1000);

            if (!err.equals("")) {
                JOptionPane.showMessageDialog(
                        null,
                        err,
                        "Eingabefehler",
                        JOptionPane.INFORMATION_MESSAGE);
                continue;
            }

            number = Integer.parseInt(input);
            return number;
        }
    }

}
