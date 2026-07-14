package util;

import javax.swing.ImageIcon;

public enum DrinkIcon {

    DEFAULT_ICON(IconLoader.DEFAULT_ICON_LARGE, IconLoader.DEFAULT_ICON_SMALL),
    WATER_1(IconLoader.WATER_ICON_1_LARGE, IconLoader.WATER_ICON_1_SMALL),
    COFFEE_1(IconLoader.COFFEE_ICON_1_LARGE, IconLoader.COFFEE_ICON_1_SMALL),
    BEER_1(IconLoader.BEER_ICON_1_LARGE, IconLoader.BEER_ICON_1_SMALL),
    CACAO_1(IconLoader.CACAO_ICON_1_LARGE, IconLoader.CACAO_ICON_1_SMALL),
    COKE_1(IconLoader.COKE_ICON_1_LARGE, IconLoader.COKE_ICON_1_SMALL),
    ENERGY_DRINK_1(IconLoader.ENERGY_DRINK_ICON_1_LARGE, IconLoader.ENERGY_DRINK_ICON_1_SMALL),
    FRUIT_WATER_1(IconLoader.FRUIT_WATER_ICON_1_LARGE, IconLoader.FRUIT_WATER_ICON_1_SMALL),
    FRUIT_WATER_2(IconLoader.FRUIT_WATER_ICON_2_LARGE, IconLoader.FRUIT_WATER_ICON_2_SMALL),
    MILK_1(IconLoader.MILK_ICON_1_LARGE, IconLoader.MILK_ICON_1_SMALL),
    SODA_1(IconLoader.SODA_ICON_1_LARGE, IconLoader.SODA_ICON_1_SMALL),
    SODA_2(IconLoader.SODA_ICON_2_LARGE, IconLoader.SODA_ICON_2_SMALL),
    TEA_1(IconLoader.TEA_ICON_1_LARGE, IconLoader.TEA_ICON_1_SMALL),
    JUICE_1(IconLoader.JUICE_ICON_1_LARGE, IconLoader.JUICE_ICON_1_SMALL),
    JUICE_2(IconLoader.JUICE_ICON_2_LARGE, IconLoader.JUICE_ICON_2_SMALL),
    JUICE_3(IconLoader.JUICE_ICON_3_LARGE, IconLoader.JUICE_ICON_3_SMALL),
    JUICE_4(IconLoader.JUICE_ICON_4_LARGE, IconLoader.JUICE_ICON_4_SMALL),
    JUICE_5(IconLoader.JUICE_ICON_5_LARGE, IconLoader.JUICE_ICON_5_SMALL),
    JUICE_6(IconLoader.JUICE_ICON_6_LARGE, IconLoader.JUICE_ICON_6_SMALL),
    JUICE_7(IconLoader.JUICE_ICON_7_LARGE, IconLoader.JUICE_ICON_7_SMALL),
    JUICE_8(IconLoader.JUICE_ICON_8_LARGE, IconLoader.JUICE_ICON_8_SMALL);

    private ImageIcon largeIcon;
    private ImageIcon smallIcon;

    DrinkIcon(ImageIcon largeIcon, ImageIcon smallIcon) {
        this.largeIcon = largeIcon;
        this.smallIcon = smallIcon;
    }

    public ImageIcon getLargeIcon() {
        return largeIcon;
    }

    public ImageIcon getSmallIcon() {
        return smallIcon;
    }

}
