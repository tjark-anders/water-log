package util;

import java.awt.Image;

import javax.swing.ImageIcon;

import ui.MainWindow;

public class IconLoader {

    public static final ImageIcon WATER_ICON = new ImageIcon(
            new ImageIcon(
                    MainWindow.class.getResource("/img/water.png"))
                    .getImage()
                    .getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE,
                            Image.SCALE_SMOOTH));

    public static final ImageIcon COFFEE_ICON = new ImageIcon(
            new ImageIcon(
                    MainWindow.class.getResource("/img/coffee.png"))
                    .getImage()
                    .getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE,
                            Image.SCALE_SMOOTH));

    public static final ImageIcon COLA_ICON = new ImageIcon(
            new ImageIcon(
                    MainWindow.class.getResource("/img/cola.png"))
                    .getImage()
                    .getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE,
                            Image.SCALE_SMOOTH));

    public static final ImageIcon DEFAULT_ICON = new ImageIcon(
            new ImageIcon(
                    MainWindow.class.getResource("/img/water.png"))
                    .getImage()
                    .getScaledInstance(Constants.ICON_SIZE, Constants.ICON_SIZE,
                            Image.SCALE_SMOOTH));

}
