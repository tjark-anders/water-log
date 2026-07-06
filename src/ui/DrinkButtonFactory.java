package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import model.DrinkOption;
import util.Constants;

public class DrinkButtonFactory {

    public static JButton createDrinkButton(DrinkOption drink, Color color) {

        String buttonLabel = "<html><center>" + drink.getName() + "<br>" + drink.getSize() + "ml</center></html>";
        JButton button = new JButton(buttonLabel, drink.getIcon());
        button.setBackground(color);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(Constants.DRINK_BUTTON_SIZE, Constants.DRINK_BUTTON_SIZE));

        return button;
    }

    public static JButton createFunktionButton(String text) {

        JButton button = new JButton(text);
        button.setPreferredSize(Constants.FUN_BUTTON_SIZE);

        return button;
    }
}