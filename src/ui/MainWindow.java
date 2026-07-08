package ui;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import util.Constants;
import util.DrinkIcon;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import layout.WrapLayout;
import model.DrinkOption;
import service.DrinkManager;
import service.SaveManager;
import service.WaterCalculator;

public class MainWindow {

    public boolean delMode = false;
    private int waterGoal = 3000;

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

        // ------------------------------- Panels -------------------------------------

        JPanel rootPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel subHeaderPanel = new JPanel();
        subHeaderPanel.setLayout(new BoxLayout(subHeaderPanel, BoxLayout.Y_AXIS));

        JPanel historyPanel = new JPanel(new BorderLayout());

        JPanel historyScrollPanel = new JPanel();
        historyScrollPanel.setLayout(new BoxLayout(historyScrollPanel, BoxLayout.Y_AXIS));

        JPanel historyButtonPanel = new JPanel();
        historyButtonPanel.setLayout(new BoxLayout(historyButtonPanel, BoxLayout.Y_AXIS));

        JPanel selectionPanel = new JPanel(new BorderLayout());

        JPanel selectionButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel selectionScrollPanel = new JPanel(new WrapLayout());

        saveManager.loadDrinkOptions();
        saveManager.loadDailyDrinksLog();

        if (drinkManager.getDrinkOptions().size() == 0) {
            initPresetDrinkOptions();
        }

        // ------------------------------- Labels -------------------------------------

        JLabel headerLabel = new JLabel("WaterLog");
        headerLabel.setFont(new Font("", 1, 80));

        waterLabel = new JLabel("");
        waterLabel.setFont(new Font("", 1, 20));

        JLabel waterStreakLabel = new JLabel("10");
        waterStreakLabel.setFont(new Font("", 1, 20));

        JLabel historyLabel = new JLabel("Verlauf");
        historyLabel.setFont(new Font("", 1, 20));

        JLabel selectionLabel = new JLabel("Getränke");
        selectionLabel.setFont(new Font("", 1, 20));

        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        waterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelList.add(headerLabel);
        labelList.add(waterLabel);
        labelList.add(selectionLabel);
        labelList.add(waterStreakLabel);
        labelList.add(historyLabel);

        updateWaterLabel();

        // ------------------------------- Buttons -------------------------------------

        JButton historyAddButton = DrinkButtonFactory.createHistoryButton("Schneller Eintrag");
        historyAddButton.addActionListener(e -> {
            historyAddAction();
        });

        JButton historyRemoveButton = DrinkButtonFactory.createHistoryButton("Alle löschen");
        historyRemoveButton.addActionListener(e -> {
            historyRemoveAction();
        });

        JButton historyEditButton = DrinkButtonFactory.createHistoryButton("Bearbeiten");
        historyEditButton.addActionListener(e -> {
            historyEditAction();
        });

        JButton selectionAddButton = DrinkButtonFactory.createSelectionButton("Hinzufügen");
        selectionAddButton.addActionListener(e -> {
            selectionAddAction(selectionScrollPanel);
        });

        JButton selectionRemoveButton = DrinkButtonFactory.createSelectionButton("Löschen");
        selectionRemoveButton.addActionListener(e -> {
            selectionRemoveAction(selectionScrollPanel);
        });

        JButton selectionEditButton = DrinkButtonFactory.createSelectionButton("Bearbeiten");
        selectionEditButton.addActionListener(e -> {
            selectionEditAction();
        });

        buildDrinkButtons(selectionScrollPanel);

        // ------------------------------- Scroll-Panes -------------------------------

        JScrollPane historyScrollPane = new JScrollPane(historyScrollPanel);
        historyScrollPane.setPreferredSize(new Dimension(150, 700));
        historyScrollPane.setMaximumSize(historyScrollPane.getPreferredSize());

        JScrollPane selectionScrollPane = new JScrollPane(selectionScrollPanel);
        selectionScrollPane.setPreferredSize(new Dimension(100, 100));
        // selectionScrollPane.setBorder(null);

        // ------------------------------- Lines ---------------------------------------

        // JSeparator headLine = new JSeparator();
        // headLine.setForeground(Color.WHITE);

        // ------------------------------- Panel-fusion -------------------------------

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

        historyPanel.add(historyLabel, BorderLayout.NORTH);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);
        historyPanel.add(historyButtonPanel, BorderLayout.SOUTH);

        historyButtonPanel.add(historyEditButton);
        historyButtonPanel.add(historyAddButton);
        historyButtonPanel.add(historyRemoveButton);

        selectionPanel.add(selectionLabel, BorderLayout.NORTH);
        selectionPanel.add(selectionButtonPanel, BorderLayout.SOUTH);
        selectionPanel.add(selectionScrollPane, BorderLayout.CENTER);

        selectionButtonPanel.add(selectionRemoveButton);
        selectionButtonPanel.add(selectionAddButton);
        selectionButtonPanel.add(selectionEditButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
        frame.setVisible(true);

        for (JPanel panel : panelList) {
            panel.setBackground(Constants.BACKGROUND_COLOR);
            if (!panel.equals(historyButtonPanel)
                    && !panel.equals(selectionButtonPanel)
                    && !panel.equals(selectionScrollPanel)) {

                panel.setBorder(new EmptyBorder(
                        Constants.BORDER_SPACE,
                        Constants.BORDER_SPACE,
                        Constants.BORDER_SPACE,
                        Constants.BORDER_SPACE));
            }
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
        saveManager.saveDrinkOptions();
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
                    saveManager.saveDrinkOptions();
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
        if (currPercent >= 100) {
            waterLabel.setForeground(Color.GREEN);
        } else {
            waterLabel.setForeground(Constants.TEXT_COLOR);
        }
    }

    public void initPresetDrinkOptions() {

        drinkManager.addDrinkOption(new DrinkOption("Wasser", DrinkIcon.WATER, 250, 100));
        drinkManager.addDrinkOption(new DrinkOption("Wasser", DrinkIcon.WATER, 330, 100));
        drinkManager.addDrinkOption(new DrinkOption("Wasser", DrinkIcon.WATER, 500, 100));

        drinkManager.addDrinkOption(new DrinkOption("Kaffee", DrinkIcon.COFFEE, 330, 98));
        drinkManager.addDrinkOption(new DrinkOption("Cola", DrinkIcon.COLA, 330, 90));
    }
}