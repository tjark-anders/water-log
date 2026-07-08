package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import model.DrinkOption;
import util.Constants;
import util.IconLoader;

public class DrinkButtonFactory {

    public static JButton createDrinkButton(DrinkOption drink, Color color) {

        String buttonLabel = "<html><center>" + drink.getName() + "<br>" + drink.getSize() + "ml</center></html>";
        JButton button = new JButton(buttonLabel, drink.getIcon().getImageIcon());
        button.setBackground(color);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(Constants.DRINK_BUTTON_SIZE, Constants.DRINK_BUTTON_SIZE));

        return button;
    }

    public static JButton createSelectionButton(String text) {

        JButton button = new JButton(text);
        button.setPreferredSize(Constants.SELECTION_BUTTON_SIZE);
        button.setMinimumSize(button.getPreferredSize());
        button.setMaximumSize(button.getPreferredSize());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Constants.BUTTON_COLOR);

        return button;
    }

    public static JButton createHistoryButton(String text) {

        JButton button = new JButton(text);
        button.setPreferredSize(Constants.HISTORY_BUTTON_SIZE);
        button.setMinimumSize(button.getPreferredSize());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Constants.BUTTON_COLOR);

        return button;
    }
}