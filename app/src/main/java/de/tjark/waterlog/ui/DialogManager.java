package de.tjark.waterlog.ui;

import javax.swing.JOptionPane;

import de.tjark.waterlog.service.DrinkManager;
import de.tjark.waterlog.util.Validator;

public class DialogManager {

    private DrinkManager drinkManager;

    public DialogManager(DrinkManager drinkManager) {
        this.drinkManager = drinkManager;
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
