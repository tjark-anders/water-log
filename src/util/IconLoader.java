package util;

import java.awt.Image;

import javax.swing.ImageIcon;

import ui.MainWindow;

public class IconLoader {

	public static final ImageIcon DEFAULT_ICON_LARGE = createLargeIcon("/img/water_1.png");
	public static final ImageIcon WATER_ICON_1_LARGE = createLargeIcon("/img/water_1.png");
	public static final ImageIcon COFFEE_ICON_1_LARGE = createLargeIcon("/img/coffee_1.png");
	public static final ImageIcon BEER_ICON_1_LARGE = createLargeIcon("/img/beer_1.png");
	public static final ImageIcon CACAO_ICON_1_LARGE = createLargeIcon("/img/cacao_1.png");
	public static final ImageIcon ENERGY_DRINK_ICON_1_LARGE = createLargeIcon("/img/energy_drink_1.png");
	public static final ImageIcon FRUIT_WATER_ICON_1_LARGE = createLargeIcon("/img/fruit_water_1.png");
	public static final ImageIcon FRUIT_WATER_ICON_2_LARGE = createLargeIcon("/img/fruit_water_2.png");
	public static final ImageIcon MILK_ICON_1_LARGE = createLargeIcon("/img/milk_1.png");
	public static final ImageIcon SODA_ICON_1_LARGE = createLargeIcon("/img/soda_1.png");
	public static final ImageIcon SODA_ICON_2_LARGE = createLargeIcon("/img/soda_2.png");
	public static final ImageIcon TEA_ICON_1_LARGE = createLargeIcon("/img/tea_1.png");
	public static final ImageIcon COKE_ICON_1_LARGE = createLargeIcon("/img/coke_1.png");
	public static final ImageIcon JUICE_ICON_1_LARGE = createLargeIcon("/img/juice_1.png");
	public static final ImageIcon JUICE_ICON_2_LARGE = createLargeIcon("/img/juice_2.png");
	public static final ImageIcon JUICE_ICON_3_LARGE = createLargeIcon("/img/juice_3.png");
	public static final ImageIcon JUICE_ICON_4_LARGE = createLargeIcon("/img/juice_4.png");
	public static final ImageIcon JUICE_ICON_5_LARGE = createLargeIcon("/img/juice_5.png");
	public static final ImageIcon JUICE_ICON_6_LARGE = createLargeIcon("/img/juice_6.png");
	public static final ImageIcon JUICE_ICON_7_LARGE = createLargeIcon("/img/juice_7.png");
	public static final ImageIcon JUICE_ICON_8_LARGE = createLargeIcon("/img/juice_8.png");

	public static final ImageIcon DEFAULT_ICON_SMALL = createSmallIcon("/img/water_1.png");
	public static final ImageIcon WATER_ICON_1_SMALL = createSmallIcon("/img/water_1.png");
	public static final ImageIcon COFFEE_ICON_1_SMALL = createSmallIcon("/img/coffee_1.png");
	public static final ImageIcon BEER_ICON_1_SMALL = createSmallIcon("/img/beer_1.png");
	public static final ImageIcon CACAO_ICON_1_SMALL = createSmallIcon("/img/cacao_1.png");
	public static final ImageIcon ENERGY_DRINK_ICON_1_SMALL = createSmallIcon("/img/energy_drink_1.png");
	public static final ImageIcon FRUIT_WATER_ICON_1_SMALL = createSmallIcon("/img/fruit_water_1.png");
	public static final ImageIcon FRUIT_WATER_ICON_2_SMALL = createSmallIcon("/img/fruit_water_2.png");
	public static final ImageIcon MILK_ICON_1_SMALL = createSmallIcon("/img/milk_1.png");
	public static final ImageIcon SODA_ICON_1_SMALL = createSmallIcon("/img/soda_1.png");
	public static final ImageIcon SODA_ICON_2_SMALL = createSmallIcon("/img/soda_2.png");
	public static final ImageIcon TEA_ICON_1_SMALL = createSmallIcon("/img/tea_1.png");
	public static final ImageIcon COKE_ICON_1_SMALL = createSmallIcon("/img/coke_1.png");
	public static final ImageIcon JUICE_ICON_1_SMALL = createSmallIcon("/img/juice_1.png");
	public static final ImageIcon JUICE_ICON_2_SMALL = createSmallIcon("/img/juice_2.png");
	public static final ImageIcon JUICE_ICON_3_SMALL = createSmallIcon("/img/juice_3.png");
	public static final ImageIcon JUICE_ICON_4_SMALL = createSmallIcon("/img/juice_4.png");
	public static final ImageIcon JUICE_ICON_5_SMALL = createSmallIcon("/img/juice_5.png");
	public static final ImageIcon JUICE_ICON_6_SMALL = createSmallIcon("/img/juice_6.png");
	public static final ImageIcon JUICE_ICON_7_SMALL = createSmallIcon("/img/juice_7.png");
	public static final ImageIcon JUICE_ICON_8_SMALL = createSmallIcon("/img/juice_8.png");

	// ------------------------------ helper funktions -----------------------------

	public static ImageIcon createLargeIcon(String path) {
		return new ImageIcon(
				new ImageIcon(
						MainWindow.class.getResource(path))
						.getImage()
						.getScaledInstance(Constants.LARGE_ICON_SIZE, Constants.LARGE_ICON_SIZE,
								Image.SCALE_SMOOTH));
	}

	public static ImageIcon createSmallIcon(String path) {
		return new ImageIcon(
				new ImageIcon(
						MainWindow.class.getResource(path))
						.getImage()
						.getScaledInstance(Constants.SMALL_ICON_SIZE, Constants.SMALL_ICON_SIZE,
								Image.SCALE_SMOOTH));
	}

	public static void loadDrinkIcons() {
		
	}

}
