package de.tjark.waterlog.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import de.tjark.waterlog.model.DrinkOption;
import de.tjark.waterlog.util.Constants;
import de.tjark.waterlog.util.DrinkIcon;

public class DrinkButtonFactory {

    public static JButton createDrinkOptionButton(DrinkOption drink, Color color) {

        String buttonLabel = "<html><center>" + drink.getName() + "</center></html>";
        JButton button = new JButton(buttonLabel, drink.getIcon().getLargeIcon());
        button.setBackground(color);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(Constants.DRINK_BUTTON_SIZE);

        return button;
    }

    public static JButton createDrinkEntryButton(DrinkOption drink, Color color) {

        String buttonLabel = "<html><center>" + drink.getName() + "<br>" + drink.getSize()
                + "ml</center></html>";
        JButton button = new JButton(buttonLabel, drink.getIcon().getSmallIcon());
        button.setBackground(color);
        button.setPreferredSize(Constants.DRINK_ENTRY_BUTTON_SIZE);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(15);

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

    public static JButton createFunktionButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(Constants.FUNKTION_BUTTON_SIZE);
        button.setMinimumSize(button.getPreferredSize());
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Constants.BUTTON_COLOR);

        return button;
    }

    public static JButton createIconButton(DrinkIcon icon) {
        JButton button = new JButton(icon.getSmallIcon());
        button.setBackground(Constants.BUTTON_COLOR);
        return button;
    }

}