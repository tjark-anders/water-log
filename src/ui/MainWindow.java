package ui;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import util.Constants;
import util.IconLoader;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import layout.WrapLayout;
import model.DrinkOption;
import service.DrinkManager;
import service.SaveManager;
import service.WaterCalculator;

public class MainWindow {

    public boolean delMode = false;
    private int waterGoal = 3000;
    private int kreatinCount = 0;

    private JLabel waterLabel;
    private ArrayList<JPanel> panelList = new ArrayList<>();
    private ArrayList<JLabel> labelList = new ArrayList<>();

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

        // JPanel mainPanel = new JPanel();
        // panelList.add(mainPanel);
        // mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // JPanel headPanel = new JPanel();
        // headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));

        // JPanel funktionPanel = new JPanel(new FlowLayout());
        // JPanel editButtonPanel = new JPanel();

        // editButtonPanel.setLayout(new BoxLayout(editButtonPanel, BoxLayout.Y_AXIS));

        // JPanel mainDrinksPanel = new JPanel(new FlowLayout());
        // JPanel customDrinksPanel = new JPanel(new FlowLayout());
        // JPanel footerPanel = new JPanel(new FlowLayout());

        // ------------------------------- new -------------------------------------
        JPanel rootPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel subHeaderPanel = new JPanel();
        subHeaderPanel.setLayout(new BoxLayout(subHeaderPanel, BoxLayout.Y_AXIS));

        JPanel historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.X_AXIS));
        JPanel historyButtonPanel = new JPanel();
        historyButtonPanel.setLayout(new BoxLayout(historyButtonPanel, BoxLayout.Y_AXIS));
        JPanel historyScrollPanel = new JPanel();
        historyScrollPanel.setLayout(new BoxLayout(historyScrollPanel, BoxLayout.Y_AXIS));

        JPanel selectionPanel = new JPanel(new BorderLayout());
        JPanel selectionButtonPanel = new JPanel();
        selectionButtonPanel.setLayout(new BoxLayout(selectionButtonPanel, BoxLayout.X_AXIS));
        JPanel selectionScrollPanel = new JPanel(new WrapLayout());

        // ------------------------------- Panels -------------------------------------

        // panelList.add(mainPanel);
        // panelList.add(funktionPanel);
        // panelList.add(editButtonPanel);
        // panelList.add(headPanel);
        // panelList.add(mainDrinksPanel);
        // panelList.add(customDrinksPanel);
        // panelList.add(footerPanel);

        initPresetDrinkOptions();
        saveManager.loadCustomDrinkOptions();
        saveManager.loadDailyDrinksLog();

        // ------------------------------- Labels -------------------------------------

        // Labels
        // currWaterLabel = new JLabel("", SwingConstants.CENTER);
        // updateWaterLabel();

        // JLabel mainDrinksLabel = new JLabel("Standard Getränke",
        // SwingConstants.CENTER);
        // JLabel customDrinksLabel = new JLabel("Eigene Getränke",
        // SwingConstants.CENTER);

        // ------------------------------- new -------------------------------------

        JLabel headerLabel = new JLabel("WaterLog");
        headerLabel.setFont(new Font("", 1, 80));
        
        waterLabel = new JLabel("");
        waterLabel.setFont(new Font("", 1, 20));

        JLabel waterStreakLabel = new JLabel("10");
        waterStreakLabel.setFont(new Font("", 1, 20));

        JLabel historyLabel = new JLabel("Verlauf");
        historyLabel.setFont(new Font("", 1, 20));

        labelList.add(headerLabel);
        labelList.add(waterLabel);
        labelList.add(waterStreakLabel);
        labelList.add(historyLabel);

        updateWaterLabel();

        // ------------------------------- Buttons -------------------------------------

        // // Open Custom Drinks File
        // JButton openCustomDrinksButton =
        // DrinkButtonFactory.createFunktionButton("Eigene Getränke");

        // openCustomDrinksButton.addActionListener(e -> {
        // try {
        // Desktop.getDesktop().open(saveManager.getCustomLogPath().toFile());
        // } catch (IOException exeption) {
        // }
        // });

        // // Open Daily Drinks File
        // JButton openDailyDrinksButton =
        // DrinkButtonFactory.createFunktionButton("Getränke Liste");

        // openDailyDrinksButton.addActionListener(e -> {
        // try {
        // Desktop.getDesktop().open(saveManager.getDailyLogPath().toFile());
        // } catch (IOException exeption) {
        // }
        // });

        // Custom Entry Button
        // JButton customEntryButton =
        // DrinkButtonFactory.createFunktionButton("Schneller Eintrag");

        // customEntryButton.addActionListener(e -> {
        // dialogManager.showCreateDrinkMenu(true);
        // saveManager.saveDailyDrinksLog();
        // updateWaterLabel();
        // });

        // Back Button
        // JButton backButton = DrinkButtonFactory.createFunktionButton("↪");

        // backButton.addActionListener(e -> {
        // drinkManager.removeLastDrink();
        // updateWaterLabel();
        // saveManager.saveDailyDrinksLog();
        // });

        // Add Button
        // JButton addButton = DrinkButtonFactory.createFunktionButton("+");

        // addButton.addActionListener(e -> {
        // dialogManager.showCreateDrinkMenu(false);
        // saveManager.saveCustomDrinkOptions();
        // buildCustomButtons(customDrinksPanel);
        // });

        // Remove Button
        // JButton deleteButton = DrinkButtonFactory.createFunktionButton("-");

        // deleteButton.addActionListener(e -> {
        // if (drinkManager.getCustomDrinks().size() == 0)
        // return;
        // delMode = !delMode;
        // buildCustomButtons(customDrinksPanel);
        // });

        // Drink Buttons
        // buildDrinkButtons(customDrinksPanel);

        // ------------------------------- new -------------------------------------

        JButton historyAddButton = DrinkButtonFactory.createFunktionButton("Add");
        historyAddButton.addActionListener(e -> {
            historyAddAction();
        });
        JButton historyRemoveButton = DrinkButtonFactory.createFunktionButton("Del");
        historyRemoveButton.addActionListener(e -> {
            historyRemoveAction();
        });

        JButton historyEditButton = DrinkButtonFactory.createFunktionButton("Edit");
        historyEditButton.addActionListener(e -> {
            historyEditAction();
        });

        JButton selectionAddButton = DrinkButtonFactory.createFunktionButton("Add");
        selectionAddButton.addActionListener(e -> {
            selectionAddAction(selectionScrollPanel);
        });

        JButton selectionRemoveButton = DrinkButtonFactory.createFunktionButton("Del");
        selectionRemoveButton.addActionListener(e -> {
            selectionRemoveAction(selectionScrollPanel);
        });

        JButton selectionEditButton = DrinkButtonFactory.createFunktionButton("Edit");
        selectionEditButton.addActionListener(e -> {
            selectionEditAction();
        });

        buildDrinkButtons(selectionScrollPanel);

        // ------------------------------- Scroll-Panes -------------------------------
        JScrollPane historyScrollPane = new JScrollPane(historyScrollPanel);
        JScrollPane selectionScrollPane = new JScrollPane(selectionScrollPanel);

        // ------------------------------- Panel-fusion -------------------------------

        // headPanel.add(backButton);
        // headPanel.add(funktionPanel);
        // headPanel.add(editButtonPanel);

        // funktionPanel.add(openCustomDrinksButton);
        // funktionPanel.add(openDailyDrinksButton);
        // funktionPanel.add(customEntryButton);

        // editButtonPanel.add(addButton);
        // editButtonPanel.add(deleteButton);

        // footerPanel.add(currWaterLabel);
        // footerPanel.add(kreatinLabel);
        // footerPanel.add(kreatinCBox);
        // footerPanel.add(kreatinCountLabel);

        // mainPanel.add(headPanel);
        // mainPanel.add(mainDrinksLabel);
        // mainPanel.add(mainDrinksPanel);
        // mainPanel.add(customDrinksLabel);
        // mainPanel.add(customDrinksPanel);
        // mainPanel.add(footerPanel);

        // frame.add(mainPanel);
        // ------------------------------- new -------------------------------------
        panelList.add(rootPanel);
        panelList.add(headerPanel);
        panelList.add(subHeaderPanel);
        panelList.add(historyPanel);
        panelList.add(historyButtonPanel);
        panelList.add(historyScrollPanel);
        panelList.add(selectionPanel);
        panelList.add(selectionButtonPanel);
        panelList.add(selectionScrollPanel);

        // Basics
        headerPanel.add(subHeaderPanel, BorderLayout.CENTER);
        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(historyPanel, BorderLayout.WEST);
        rootPanel.add(selectionPanel, BorderLayout.CENTER);
        frame.add(rootPanel);

        // Elements
        subHeaderPanel.add(headerLabel);
        // subHeaderPanel.add(waterBar)
        subHeaderPanel.add(waterLabel);

        headerPanel.add(waterStreakLabel, BorderLayout.EAST);

        historyPanel.add(historyScrollPane);
        historyPanel.add(historyButtonPanel);

        historyScrollPanel.add(historyLabel);

        historyButtonPanel.add(historyEditButton);
        historyButtonPanel.add(historyAddButton);
        historyButtonPanel.add(historyRemoveButton);

        selectionPanel.add(selectionScrollPane, BorderLayout.CENTER);
        selectionPanel.add(selectionButtonPanel, BorderLayout.EAST);

        selectionButtonPanel.add(selectionRemoveButton);
        selectionButtonPanel.add(selectionAddButton);
        selectionButtonPanel.add(selectionEditButton);

        // ------------------------------- new -------------------------------------

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
        frame.setVisible(true);

        for (JPanel panel : panelList) {
            panel.setBackground(Constants.BACKGROUND_COLOR);
        }

        for (JLabel label : labelList) {
            label.setForeground(Constants.TEXT_COLOR);
        }
    }

    // ------------------------------- Button-Actions ---------------------------

    private void historyAddAction() {
        dialogManager.showCreateDrinkMenu(true);
        saveManager.saveDailyDrinksLog();
        updateWaterLabel();
    }

    private void historyRemoveAction() {
        drinkManager.removeAllDrinks();
        updatePanels();
        updateWaterLabel();
    }

    private void historyEditAction() {

    }

    private void selectionAddAction(JPanel panel) {
        dialogManager.showCreateDrinkMenu(false);
        saveManager.saveCustomDrinkOptions();
        buildDrinkButtons(panel);
    }

    private void selectionRemoveAction(JPanel panel) {
        if (drinkManager.getDrinkOptions().size() == 0)
            return;
        delMode = !delMode;
        buildDrinkButtons(panel);
    }

    private void selectionEditAction() {

    }

    // ------------------------------- Helper-Funktions ---------------------------

    public void buildDrinkButtons(JPanel customDrinksPanel) {
        customDrinksPanel.removeAll();

        for (DrinkOption drink : drinkManager.getDrinkOptions()) {
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
                    drinkManager.removeDrinkOption(drink);
                    customDrinksPanel.removeAll();
                    saveManager.saveCustomDrinkOptions();
                    delMode = false;
                    buildDrinkButtons(customDrinksPanel);
                });
            }
            updatePanels();
        }
    }

    public void updatePanels() {
        for (JPanel panel : panelList) {
            panel.revalidate();
            panel.repaint();
        }
    }

    public void updateWaterLabel() {
        int currWater = waterCalculator.getCurrWater();
        int currPercent = 0;

        if (currWater != 0) {
            currPercent = currWater * 100 / waterGoal;
        }

        waterLabel.setText(currWater + " / " + waterGoal + "ml ~ " + currPercent + "%");
    }

    public void initPresetDrinkOptions() {

        drinkManager.addDrinkOption(new DrinkOption("Wasser", IconLoader.WATER_ICON, 250, 100));
        drinkManager.addDrinkOption(new DrinkOption("Wasser", IconLoader.WATER_ICON, 330, 100));
        drinkManager.addDrinkOption(new DrinkOption("Wasser", IconLoader.WATER_ICON, 500, 100));

        drinkManager.addDrinkOption(new DrinkOption("Kaffee", IconLoader.COFFEE_ICON, 330, 98));
        drinkManager.addDrinkOption(new DrinkOption("Cola", IconLoader.COLA_ICON, 330, 90));
    }
}