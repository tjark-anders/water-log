package ui;

import javax.swing.JOptionPane;

import model.DrinkOption;
import service.DrinkManager;
import util.IconLoader;
import util.Validator;

public class DialogManager {

    private DrinkManager drinkManager;

    public DialogManager(DrinkManager drinkManager) {
        this.drinkManager = drinkManager;
    }

    public void showCreateDrinkMenu(boolean quickAdd) {

        String name = "";
        int size = 0;
        int waterP = 0;

        // Name
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Name: ");

            if (input == null)
                return;

            if (!validate(input, false, 15)) {
                continue;
            }
            name = input;
            break;
        }

        // Size
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Size: ");

            if (input == null)
                return;

            if (!validate(input, true, 15)) {
                continue;
            }
            size = Integer.parseInt(input);
            break;
        }

        // WaterP
        while (true) {
            String input = JOptionPane.showInputDialog(null, "Water%: ");

            if (input == null)
                return;

            if (!validate(input, true, 15)) {
                continue;
            }
            waterP = Integer.parseInt(input);
            break;
        }

        if (quickAdd) {
            drinkManager.addDrink(new DrinkOption(name, IconLoader.DEFAULT_ICON, size, waterP));
            return;
        }

        drinkManager.addDrinkOption(new DrinkOption(name, IconLoader.DEFAULT_ICON, size, waterP));
    }

    public boolean validate(String input, boolean isInt, int maxLength) {

        if (input.length() > maxLength) {
            JOptionPane.showMessageDialog(
                    null,
                    "Eingabe ist zu lang",
                    null,
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Eingabe darf nicht leer sein",
                    null,
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        if (isInt && !Validator.isInteger(input)) {
            JOptionPane.showMessageDialog(
                    null,
                    "Eingabe muss eine Zahl sein",
                    null,
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true;
    }
}
