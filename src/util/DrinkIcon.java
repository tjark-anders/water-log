package util;

import javax.swing.ImageIcon;

public enum DrinkIcon {

    DEFAULT_ICON(IconLoader.DEFAULT_ICON),
    WATER(IconLoader.WATER_ICON),
    COFFEE(IconLoader.COFFEE_ICON),
    COLA(IconLoader.COLA_ICON);

    private ImageIcon icon;

    DrinkIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getImageIcon() {
        return icon;
    }

}
