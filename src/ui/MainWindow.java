package ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Desktop;
import java.awt.FlowLayout;
import util.Constants;
import util.IconLoader;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.DrinkOption;
import service.DrinkManager;
import service.SaveManager;
import service.WaterCalculator;

public class MainWindow {

    public boolean delMode = false;
    private int waterGoal = 3000;
    private int kreatinCount = 0;

    private JLabel currWaterLabel;
    private ArrayList<JPanel> panelList = new ArrayList<>();

    private DrinkManager drinkManager;
    private SaveManager saveManager;
    private DialogManager dialogManager;
    private WaterCalculator waterCalculator;

    public MainWindow(
            DrinkManager drinkManager,
            SaveManager saveManager,
            WaterCalculator waterCalculator,
            DialogManager dialogManager) {

        this.drinkManager = drinkManager;
        this.saveManager = saveManager;
        this.dialogManager = dialogManager;
        this.waterCalculator = waterCalculator;

        run();
    }

    private void run() {

        JFrame frame = new JFrame();
        frame.setTitle("Water Log");

        // Panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Constants.BACKGROUND_COLOR);

        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));

        JPanel funktionPanel = new JPanel(new FlowLayout());
        JPanel editButtonPanel = new JPanel();

        editButtonPanel.setLayout(new BoxLayout(editButtonPanel, BoxLayout.Y_AXIS));

        JPanel mainDrinksPanel = new JPanel(new FlowLayout());
        JPanel customDrinksPanel = new JPanel(new FlowLayout());
        JPanel footerPanel = new JPanel(new FlowLayout());

        // ------------------------------- Panels -------------------------------------

        headPanel.setBackground(Constants.BACKGROUND_COLOR);

        mainDrinksPanel.setBackground(Constants.BACKGROUND_COLOR);
        customDrinksPanel.setBackground(Constants.BACKGROUND_COLOR);
        footerPanel.setBackground(Constants.BACKGROUND_COLOR);

        panelList.add(mainPanel);
        panelList.add(funktionPanel);
        panelList.add(editButtonPanel);
        panelList.add(headPanel);
        panelList.add(mainDrinksPanel);
        panelList.add(customDrinksPanel);
        panelList.add(footerPanel);

        initPresetDrinkOptions();
        saveManager.loadCustomDrinkOptions();
        saveManager.loadDailyDrinksLog();

        // ------------------------------- Labels -------------------------------------

        // Labels
        currWaterLabel = new JLabel("", SwingConstants.CENTER);
        currWaterLabel.setForeground(Constants.TEXT_COLOR);
        updateWaterLabel();

        JLabel mainDrinksLabel = new JLabel("Standard Getränke", SwingConstants.CENTER);
        mainDrinksLabel.setForeground(Constants.TEXT_COLOR);
        JLabel customDrinksLabel = new JLabel("Eigene Getränke", SwingConstants.CENTER);
        customDrinksLabel.setForeground(Constants.TEXT_COLOR);

        // ------------------------------- Buttons -------------------------------------

        // Open Custom Drinks File
        JButton openCustomDrinksButton = DrinkButtonFactory.createFunktionButton("Eigene Getränke");

        openCustomDrinksButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(saveManager.getCustomLogPath().toFile());
            } catch (IOException exeption) {
            }
        });

        // Open Daily Drinks File
        JButton openDailyDrinksButton = DrinkButtonFactory.createFunktionButton("Getränke Liste");

        openDailyDrinksButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(saveManager.getDailyLogPath().toFile());
            } catch (IOException exeption) {
            }
        });

        // Custom Entry Button
        JButton customEntryButton = DrinkButtonFactory.createFunktionButton("Schneller Eintrag");

        customEntryButton.addActionListener(e -> {
            dialogManager.showCreateDrinkMenu(true);
            saveManager.saveDailyDrinksLog();
            updateWaterLabel();
        });

        // Back Button
        JButton backButton = DrinkButtonFactory.createFunktionButton("↪");

        backButton.addActionListener(e -> {
            drinkManager.removeLastDrink();
            updateWaterLabel();
            saveManager.saveDailyDrinksLog();
        });

        // Add Button
        JButton addButton = DrinkButtonFactory.createFunktionButton("+");

        addButton.addActionListener(e -> {
            dialogManager.showCreateDrinkMenu(false);
            saveManager.saveCustomDrinkOptions();
            buildCustomButtons(customDrinksPanel);
        });

        // Remove Button
        JButton deleteButton = DrinkButtonFactory.createFunktionButton("-");

        deleteButton.addActionListener(e -> {
            if (drinkManager.getCustomDrinks().size() == 0)
                return;
            delMode = !delMode;
            buildCustomButtons(customDrinksPanel);
        });

        // Kreatin
        JLabel kreatinLabel = new JLabel("Kreatin");
        JCheckBox kreatinCBox = new JCheckBox();
        JLabel kreatinCountLabel = new JLabel(Integer.toString(kreatinCount));

        // Drink Buttons
        buildPresetButtons(mainDrinksPanel);
        buildCustomButtons(customDrinksPanel);

        // ------------------------------- Panel-fusion -------------------------------

        headPanel.add(backButton);
        headPanel.add(funktionPanel);
        headPanel.add(editButtonPanel);

        funktionPanel.add(openCustomDrinksButton);
        funktionPanel.add(openDailyDrinksButton);
        funktionPanel.add(customEntryButton);

        editButtonPanel.add(addButton);
        editButtonPanel.add(deleteButton);

        footerPanel.add(currWaterLabel);
        footerPanel.add(kreatinLabel);
        footerPanel.add(kreatinCBox);
        footerPanel.add(kreatinCountLabel);

        mainPanel.add(headPanel);
        mainPanel.add(mainDrinksLabel);
        mainPanel.add(mainDrinksPanel);
        mainPanel.add(customDrinksLabel);
        mainPanel.add(customDrinksPanel);
        mainPanel.add(footerPanel);

        frame.add(mainPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
        frame.setVisible(true);

        for (JPanel panel : panelList) {
            panel.setBackground(Constants.BACKGROUND_COLOR);
        }
    }

    // ------------------------------- Helper-Funktions ---------------------------

    public void buildPresetButtons(JPanel mainDrinksPanel) {
        for (DrinkOption drink : drinkManager.getPresetDrinks()) {
            JButton button = DrinkButtonFactory.createDrinkButton(drink, Constants.BUTTON_COLOR);
            mainDrinksPanel.add(button);

            button.addActionListener(e -> {
                drinkManager.addDrink(drink);
                updateWaterLabel();
                saveManager.saveDailyDrinksLog();
            });
        }
    }

    public void buildCustomButtons(JPanel customDrinksPanel) {
        customDrinksPanel.removeAll();

        for (DrinkOption drink : drinkManager.getCustomDrinks()) {
            if (!delMode) {
                JButton button = DrinkButtonFactory.createDrinkButton(drink, Constants.BUTTON_COLOR);
                customDrinksPanel.add(button);

                button.addActionListener(e -> {
                    drinkManager.addDrink(drink);
                    updateWaterLabel();
                    saveManager.saveDailyDrinksLog();
                });
            } else {
                JButton button = DrinkButtonFactory.createDrinkButton(drink, Constants.DEL_BUTTON_COLOR);
                customDrinksPanel.add(button);

                button.addActionListener(e -> {
                    drinkManager.removeCustomDrink(drink);
                    customDrinksPanel.removeAll();
                    saveManager.saveCustomDrinkOptions();
                    delMode = false;
                    buildCustomButtons(customDrinksPanel);
                });
            }
            updatePanels();
        }
    }

    public void updatePanels() {
        for (JPanel panel : panelList) {
            panel.repaint();
            panel.updateUI();
        }
    }

    public void updateWaterLabel() {
        currWaterLabel.setText("Wasserziel: " + waterCalculator.getCurrWater() + " / " + waterGoal + "ml");
    }

    public void initPresetDrinkOptions() {

        drinkManager.addPresetDrink(new DrinkOption("Wasser", IconLoader.WATER_ICON, 250, 100));
        drinkManager.addPresetDrink(new DrinkOption("Wasser", IconLoader.WATER_ICON, 330, 100));
        drinkManager.addPresetDrink(new DrinkOption("Wasser", IconLoader.WATER_ICON, 500, 100));

        drinkManager.addPresetDrink(new DrinkOption("Kaffee", IconLoader.COFFEE_ICON, 330, 98));
        drinkManager.addPresetDrink(new DrinkOption("Cola", IconLoader.COLA_ICON, 330, 90));
    }
}