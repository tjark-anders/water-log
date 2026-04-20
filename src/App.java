import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.time.*;

//import core.utils.Numbers;

public class App {
    private static int currWater = 0;
    private static JLabel currWaterLabel;
    private static int waterGoal = 3000;
    private static int iconSize = 60;
    private static boolean delMode = false;
    private static ImageIcon defaultIcon;
    private static ArrayList<DrinkOption> drinkList = new ArrayList<>();
    private static ArrayList<DrinkOption> customDrinkList = new ArrayList<>();
    private static ArrayList<DrinkOption> dailyDrinkList = new ArrayList<>();
    private static ArrayList<JPanel> panelList = new ArrayList<>();

    private static final Path BASE_PATH = Path.of(System.getProperty("user.home"), "saves", "WaterLog");
    private static final Path DAILY_LOG = BASE_PATH.resolve("DailyDrinksLog.txt");
    private static final Path CUSTOM_LOG = BASE_PATH.resolve("CustomDrinkOptions.txt");

    public static void main(String[] args) throws Exception {

        Files.createDirectories(BASE_PATH);

        if (!Files.exists(DAILY_LOG)) {
            Files.createFile(DAILY_LOG);
        }
        if (!Files.exists(CUSTOM_LOG)) {
            Files.createFile(CUSTOM_LOG);
        }

        JFrame frame = new JFrame();
        frame.setTitle("WaterLog");

        // Panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel headPanelTop = new JPanel(new BorderLayout());
        JPanel headPanelBottom = new JPanel(new BorderLayout());
        JPanel mainDrinksPanel = new JPanel(new FlowLayout());
        JPanel customDrinksPanel = new JPanel(new FlowLayout());
        JPanel footerPanel = new JPanel(new FlowLayout());

        panelList.add(mainPanel);
        panelList.add(headPanelTop);
        panelList.add(headPanelBottom);
        panelList.add(mainDrinksPanel);
        panelList.add(customDrinksPanel);
        panelList.add(footerPanel);

        initStandardDrinkOptions();
        loadCustomDrinkOptions();
        loadDailyDrinksLog();

        // Labels
        currWaterLabel = new JLabel("", SwingConstants.CENTER);
        updateCurrWater();
        updateWaterLabel();

        JLabel mainDrinksLabel = new JLabel("Standard Getränke");
        JLabel customDrinksLabel = new JLabel("Eigene Getränke");

        // Open Custom Drinks File
        JButton openCustomDrinksButton = new JButton("Eigene Getränke");
        openCustomDrinksButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(CUSTOM_LOG.toFile());
            } catch (IOException exeption) {
                exeption.printStackTrace();
            }
        });

        // Open Daily Drinks File
        JButton openDailyDrinksButton = new JButton("Getränke Liste");
        openDailyDrinksButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(DAILY_LOG.toFile());
            } catch (IOException exeption) {
                exeption.printStackTrace();
            }
        });

        // Back Button
        JButton backButton = new JButton("↪");
        backButton.addActionListener(e -> {
            removeLastDrink();
            updateCurrWater();
            updateWaterLabel();
            saveDailyDrinksLog();
        });
        backButton.setPreferredSize(new Dimension(100, 20));

        // Add Button
        JButton addButton = new JButton("+");
        addButton.addActionListener(e -> {
            showCreateDrinkMenu();
            saveCustomDrinkOptions();
            generateCustomDrinkButtons(customDrinksPanel, delMode);
        });
        addButton.setPreferredSize(new Dimension(100, 20));

        // Remove Button
        JButton removeButton = new JButton("-");
        removeButton.addActionListener(e -> {
            delMode = !delMode;
            generateCustomDrinkButtons(customDrinksPanel, delMode);
        });
        removeButton.setPreferredSize(new Dimension(100, 20));

        // Drink Buttons
        generateStandardDrinkButtons(mainDrinksPanel);
        generateCustomDrinkButtons(customDrinksPanel, delMode);

        headPanelTop.setMaximumSize(new Dimension(2000, 2000));
        headPanelTop.add(backButton, BorderLayout.WEST);
        headPanelTop.add(currWaterLabel, BorderLayout.CENTER);
        headPanelTop.add(addButton, BorderLayout.EAST);

        headPanelBottom.setMaximumSize(new Dimension(2000, 2000));
        headPanelBottom.add(removeButton, BorderLayout.EAST);

        footerPanel.add(openCustomDrinksButton);
        footerPanel.add(openDailyDrinksButton);

        mainPanel.add(headPanelTop);
        mainPanel.add(headPanelBottom);
        mainPanel.add(mainDrinksLabel);
        mainPanel.add(mainDrinksPanel);
        mainPanel.add(customDrinksLabel);
        mainPanel.add(customDrinksPanel);
        mainPanel.add(footerPanel);

        frame.add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
    }

    public static void updatePanels() {
        for (JPanel panel : panelList) {
            panel.repaint();
            panel.updateUI();
        }
    }

    public static void updateCurrWater() {
        currWater = 0;
        for (DrinkOption daylyDrink : dailyDrinkList) {
            currWater += daylyDrink.getSize() / 100.0 * daylyDrink.getWaterP();
        }
    }

    public static void showCreateDrinkMenu() {
        boolean run = true;
        String name = "";
        int size = 0;
        int waterP = 0;

        // Name
        while (run) {
            String input = JOptionPane.showInputDialog(null, "Name: ");
            if (input == null) {
                run = false;
            } else if (input.length() > 15) {
                JOptionPane.showMessageDialog(null, "Eingabe ist zu lang", null, JOptionPane.INFORMATION_MESSAGE);
            } else if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Eingabe darf nicht leer sein", null,
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                name = input;
                break;
            }
        }

        // Size
        while (run) {
            String input = JOptionPane.showInputDialog(null, "Größe in ml: ");
            if (input == null) {
                run = false;
            } else if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Eingabe darf nicht leer sein", null,
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (!isInteger(input)) {
                JOptionPane.showMessageDialog(null, "Eingabe muss eine Zahl sein", null,
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                size = Integer.parseInt(input);
                break;
            }
        }

        // Water %
        while (run) {
            String input = JOptionPane.showInputDialog(null, "Wassergehalt in %: ");
            if (input == null) {
                run = false;
            } else if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Eingabe darf nicht leer sein", null,
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (!isInteger(input)) {
                JOptionPane.showMessageDialog(null, "Eingabe muss eine Zahl sein", null,
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                waterP = Integer.parseInt(input);
                break;
            }
        }
        if (run) {
            customDrinkList.add(new DrinkOption(name, defaultIcon, size, waterP));
        }
    }

    public static void generateCustomDrinkButtons(JPanel customDrinksPanel, boolean delMode) {

        customDrinksPanel.removeAll();

        for (DrinkOption drink : customDrinkList) {
            String buttonLabel = "<html><center>" + drink.getName() + "<br>" + drink.getSize() + "ml</center></html>";
            JButton button = new JButton(buttonLabel, drink.getIcon());
            if (!delMode) {
                button.addActionListener(e -> {
                    addDrink(drink);
                    updateCurrWater();
                    updateWaterLabel();
                    saveDailyDrinksLog();
                });
            } else {
                button.addActionListener(e -> {
                    customDrinkList.remove(drink);
                    customDrinksPanel.removeAll();
                    updatePanels();
                    saveCustomDrinkOptions();
                });
            }
            if (delMode) {
                button.setBackground(new Color(255, 0, 0, 50));
            }
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setPreferredSize(new Dimension(120, 120));
            customDrinksPanel.add(button);
            updatePanels();
        }
    }

    public static void loadDailyDrinksLog() {
        Path path = DAILY_LOG;
        try {
            List<String> lines = Files.readAllLines(path);
            String[] lineElements = new String[2];
            LocalDate todayDate = LocalDate.now();
            boolean isToday = false;

            for (String line : lines) {
                String name;
                int size;
                int waterP;

                if (line.startsWith(">") && LocalDate.parse(line.substring(1)).equals(todayDate)) {
                    isToday = true;
                    continue;
                }

                if (isToday && !line.isEmpty()) {
                    lineElements = line.split(";");
                    name = lineElements[0];
                    if (isInteger(lineElements[1])) {
                        size = Integer.parseInt(lineElements[1]);
                    } else {
                        System.out.println("Load failed. Reason: Size Parameter corrupted");
                        return;
                    }
                    if (isInteger(lineElements[2])) {
                        waterP = Integer.parseInt(lineElements[2]);

                    } else {
                        System.out.println("Load failed. Reason: Water% Parameter corrupted");
                        return;
                    }
                    dailyDrinkList.add(new DrinkOption(name, null, size, waterP));
                }
            }

        } catch (

        IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDailyDrinksLog() {
        LocalDate todayDate = LocalDate.now();
        try {
            Path path = DAILY_LOG;
            List<String> lines = Files.readAllLines(path);
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {

                if (line.startsWith(">") && LocalDate.parse(line.substring(1)).equals(todayDate))
                    break;
                newLines.add(line);
            }

            newLines.add(">" + LocalDate.now());
            for (DrinkOption drink : dailyDrinkList) {
                newLines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP());
            }
            newLines.add("");

            Files.write(path, newLines);

        } catch (

        IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCustomDrinkOptions() {
        Path path = CUSTOM_LOG;
        try {
            List<String> lines = Files.readAllLines(path);
            String[] lineElements = new String[2];

            for (String line : lines) {
                String name;
                int size;
                int waterP;
                lineElements = line.split(";");
                name = lineElements[0];
                if (isInteger(lineElements[1])) {
                    size = Integer.parseInt(lineElements[1]);
                } else {
                    System.out.println("Load failed. Reason: Size Parameter corrupted");
                    return;
                }
                if (isInteger(lineElements[2])) {
                    waterP = Integer.parseInt(lineElements[2]);
                } else {
                    System.out.println("Load failed. Reason: Water% Parameter corrupted");
                    return;
                }
                customDrinkList.add(new DrinkOption(name, defaultIcon, size, waterP));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveCustomDrinkOptions() {
        try {
            Path path = CUSTOM_LOG;
            List<String> lines = new ArrayList<>();

            for (DrinkOption drink : customDrinkList) {
                lines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP());
            }

            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateStandardDrinkButtons(JPanel mainDrinksPanel) {

        mainDrinksPanel.removeAll();
        for (DrinkOption drink : drinkList) {
            String buttonLabel = "<html><center>" + drink.getName() + "<br>" + drink.getSize() + "ml</center></html>";
            JButton button = new JButton(buttonLabel, drink.getIcon());

            button.addActionListener(e -> {
                addDrink(drink);
                updateCurrWater();
                updateWaterLabel();
                saveDailyDrinksLog();
            });

            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setPreferredSize(new Dimension(120, 120));
            mainDrinksPanel.add(button);
        }
    }

    public static void updateWaterLabel() {
        currWaterLabel.setText("Wasserziel: " + currWater + " / " + waterGoal + "ml");

    }

    public static void addDrink(DrinkOption drink) {
        dailyDrinkList.add(drink);
    }

    public static void removeLastDrink() {
        if (dailyDrinkList.size() > 0) {
            dailyDrinkList.remove(dailyDrinkList.size() - 1);
        }
    }

    public static void initStandardDrinkOptions() {

        ImageIcon waterIcon = new ImageIcon(
                new ImageIcon(App.class.getResource("/img/water.png"))
                        .getImage()
                        .getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        ImageIcon coffeeIcon = new ImageIcon(
                new ImageIcon(App.class.getResource("/img/coffee.png"))
                        .getImage()
                        .getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        ImageIcon colaIcon = new ImageIcon(
                new ImageIcon(App.class.getResource("/img/cola.png"))
                        .getImage()
                        .getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        defaultIcon = new ImageIcon(
                new ImageIcon(App.class.getResource("/img/water.png"))
                        .getImage()
                        .getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));

        drinkList.add(new DrinkOption("Wasser", waterIcon, 250, 100));
        drinkList.add(new DrinkOption("Wasser", waterIcon, 330, 100));
        drinkList.add(new DrinkOption("Wasser", waterIcon, 500, 100));

        drinkList.add(new DrinkOption("Kaffee", coffeeIcon, 330, 98));
        drinkList.add(new DrinkOption("Cola", colaIcon, 330, 90));
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
