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
import javax.swing.border.EmptyBorder;

import layout.WrapLayout;
import model.DrinkOption;
import service.DrinkManager;
import service.SaveManager;
import service.WaterCalculator;

public class MainWindow {

    public boolean drinkOptionDelMode = false;
    public boolean drinkEntryDelMode = false;

    public boolean drinkOptionEditMode = false;
    public boolean drinkEntryEditMode = false;

    private int waterGoal = 3000;

    private JLabel waterLabel;
    private ArrayList<JPanel> panelList = new ArrayList<>();
    private ArrayList<JLabel> labelList = new ArrayList<>();

    private DrinkManager drinkManager;
    private SaveManager saveManager;
    private DialogManager dialogManager;
    private WaterCalculator waterCalculator;
    private JFrame frame;

    private JPanel drinkOptionsScrollPanel;
    private JPanel drinkEntrysScrollPanel;

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

        frame = new JFrame();
        frame.setTitle("Water Log");

        // ------------------------------- Panels -------------------------------------

        JPanel rootPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel subHeaderPanel = new JPanel();
        subHeaderPanel.setLayout(new BoxLayout(subHeaderPanel, BoxLayout.Y_AXIS));

        JPanel drinkEntrysPanel = new JPanel(new BorderLayout(10, 0));

        drinkEntrysScrollPanel = new JPanel();
        drinkEntrysScrollPanel.setLayout(new BoxLayout(drinkEntrysScrollPanel, BoxLayout.Y_AXIS));

        JPanel drinkEntrysButtonPanel = new JPanel();
        drinkEntrysButtonPanel.setLayout(new BoxLayout(drinkEntrysButtonPanel, BoxLayout.Y_AXIS));

        JPanel drinkOptionsPanel = new JPanel(new BorderLayout());

        JPanel drinkOptionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        drinkOptionsScrollPanel = new JPanel(new WrapLayout());

        saveManager.loadDrinkOptions();
        saveManager.loadDrinkEntrys();

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
            drinkEntryAddAction();
        });

        JButton historyRemoveButton = DrinkButtonFactory.createHistoryButton("Löschen");
        historyRemoveButton.addActionListener(e -> {
            drinkEntryRemoveAction();
        });

        JButton historyEditButton = DrinkButtonFactory.createHistoryButton("Bearbeiten");
        historyEditButton.addActionListener(e -> {
            drinkEntryEditAction();
        });

        JButton selectionAddButton = DrinkButtonFactory.createSelectionButton("Hinzufügen");
        selectionAddButton.addActionListener(e -> {
            drinkOptionAddAction();
        });

        JButton selectionRemoveButton = DrinkButtonFactory.createSelectionButton("Löschen");
        selectionRemoveButton.addActionListener(e -> {
            drinkOptionRemoveAction();
        });

        JButton selectionEditButton = DrinkButtonFactory.createSelectionButton("Bearbeiten");
        selectionEditButton.addActionListener(e -> {
            drinkOptionEditAction();
        });

        JButton openDrinkOptionsButton = DrinkButtonFactory.createFunktionButton("Getränke Datei öffnen");
        openDrinkOptionsButton.addActionListener(e -> {
            saveManager.openDrinkOptionsFile();
        });

        JButton openDrinkEntrysButton = DrinkButtonFactory.createFunktionButton("Einträge Datei öffnen");
        openDrinkEntrysButton.addActionListener(e -> {
            saveManager.openDrinkEntrysFile();
        });

        buildDrinkOptionButtons(drinkOptionsScrollPanel);
        buildDrinkEntryButtons(drinkEntrysScrollPanel);

        // ------------------------------- Scroll-Panes -------------------------------

        JScrollPane historyScrollPane = new JScrollPane(drinkEntrysScrollPanel);
        historyScrollPane.setPreferredSize(new Dimension(150, 700));
        historyScrollPane.setMaximumSize(historyScrollPane.getPreferredSize());
        historyScrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        JScrollPane selectionScrollPane = new JScrollPane(drinkOptionsScrollPanel);
        selectionScrollPane.setPreferredSize(new Dimension(100, 100));
        selectionScrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // ------------------------------- Panel-fusion -------------------------------

        panelList.add(rootPanel);
        panelList.add(headerPanel);
        panelList.add(subHeaderPanel);
        panelList.add(drinkEntrysPanel);
        panelList.add(drinkEntrysButtonPanel);
        panelList.add(drinkEntrysScrollPanel);
        panelList.add(drinkOptionsPanel);
        panelList.add(drinkOptionsButtonPanel);
        panelList.add(drinkOptionsScrollPanel);

        // Basics
        headerPanel.add(subHeaderPanel, BorderLayout.CENTER);
        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(drinkEntrysPanel, BorderLayout.WEST);
        rootPanel.add(drinkOptionsPanel, BorderLayout.CENTER);
        frame.add(rootPanel);

        // Elements
        subHeaderPanel.add(headerLabel);
        // subHeaderPanel.add(waterBar)
        subHeaderPanel.add(waterLabel);

        headerPanel.add(waterStreakLabel, BorderLayout.EAST);

        drinkEntrysPanel.add(historyLabel, BorderLayout.NORTH);
        drinkEntrysPanel.add(historyScrollPane, BorderLayout.CENTER);
        drinkEntrysPanel.add(drinkEntrysButtonPanel, BorderLayout.SOUTH);

        drinkEntrysButtonPanel.add(historyEditButton);
        drinkEntrysButtonPanel.add(historyAddButton);
        drinkEntrysButtonPanel.add(historyRemoveButton);

        drinkOptionsPanel.add(selectionLabel, BorderLayout.NORTH);
        drinkOptionsPanel.add(drinkOptionsButtonPanel, BorderLayout.SOUTH);
        drinkOptionsPanel.add(selectionScrollPane, BorderLayout.CENTER);

        drinkOptionsButtonPanel.add(openDrinkEntrysButton);
        drinkOptionsButtonPanel.add(openDrinkOptionsButton);
        drinkOptionsButtonPanel.add(selectionRemoveButton);
        drinkOptionsButtonPanel.add(selectionAddButton);
        drinkOptionsButtonPanel.add(selectionEditButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.WIN_WIDTH, Constants.WIN_HEIGHT);
        frame.setVisible(true);

        for (JPanel panel : panelList) {
            panel.setBackground(Constants.BACKGROUND_COLOR);

            if (!panel.equals(drinkEntrysButtonPanel)
                    && !panel.equals(drinkOptionsButtonPanel)
                    && !panel.equals(drinkOptionsScrollPanel)
                    && !panel.equals(drinkEntrysScrollPanel)) {

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

    private void drinkEntryAddAction() {
        new CreateDrinkOptionDialog(this.frame, drinkManager, true);
        saveManager.saveDrinkEntrys();
        updateEntryButtons();
        updateWaterLabel();
    }

    private void drinkEntryRemoveAction() {
        if (drinkManager.getDrinkEntrys().size() == 0)
            return;
        drinkEntryEditMode = false;
        drinkEntryDelMode = !drinkEntryDelMode;
        updateEntryButtons();
    }

    private void drinkEntryEditAction() {
        if (drinkManager.getDrinkEntrys().size() == 0)
            return;
        drinkEntryDelMode = false;
        drinkEntryEditMode = !drinkEntryEditMode;
        updateEntryButtons();
    }

    private void drinkOptionAddAction() {
        new CreateDrinkOptionDialog(this.frame, drinkManager, false);
        saveManager.saveDrinkOptions();
        updateOptionButtons();
    }

    private void drinkOptionRemoveAction() {
        if (drinkManager.getDrinkOptions().size() == 0)
            return;
        drinkOptionEditMode = false;
        drinkOptionDelMode = !drinkOptionDelMode;
        updateOptionButtons();
    }

    private void drinkOptionEditAction() {
        if (drinkManager.getDrinkOptions().size() == 0)
            return;
        drinkOptionDelMode = false;
        drinkOptionEditMode = !drinkOptionEditMode;
        updateOptionButtons();
    }

    // ------------------------------- Helper-Funktions ---------------------------

    public void buildDrinkOptionButtons(JPanel drinkOptionsScrollPanel) {
        drinkOptionsScrollPanel.removeAll();

        for (DrinkOption drink : drinkManager.getDrinkOptions()) {

            if (drinkOptionDelMode) {
                JButton button = DrinkButtonFactory.createDrinkOptionButton(drink, Constants.DEL_BUTTON_COLOR);
                drinkOptionsScrollPanel.add(button);

                button.addActionListener(e -> {
                    drinkManager.removeDrinkOption(drink);
                    drinkOptionsScrollPanel.removeAll();
                    saveManager.saveDrinkOptions();
                    if (drinkManager.getDrinkOptions().size() == 0) {
                        drinkOptionDelMode = false;
                    }
                    updateOptionButtons();
                });

            } else if (drinkOptionEditMode) {
                JButton button = DrinkButtonFactory.createDrinkOptionButton(drink, Constants.EDIT_BUTTON_COLOR);
                drinkOptionsScrollPanel.add(button);

                button.addActionListener(e -> {

                    dialogManager.showEditDrinkOptionDialog(drink);
                    saveManager.saveDrinkOptions();

                    drinkOptionsScrollPanel.removeAll(); // schmeiß das in updateOptionbuttons
                    updateOptionButtons();
                });

            } else {
                JButton button = DrinkButtonFactory.createDrinkOptionButton(drink, Constants.BUTTON_COLOR);
                drinkOptionsScrollPanel.add(button);

                button.addActionListener(e -> {

                    DrinkPopupMenu menu = new DrinkPopupMenu(drink, drinkManager, saveManager, this, dialogManager);
                    menu.show(button, 0, button.getHeight());
                });
            }
        }
    }

    public void buildDrinkEntryButtons(JPanel drinkEntrysPanel) {
        drinkEntrysPanel.removeAll();

        for (DrinkOption drink : drinkManager.getDrinkEntrys()) {

            if (drinkEntryDelMode) {
                JButton button = DrinkButtonFactory.createDrinkEntryButton(drink, Constants.DEL_BUTTON_COLOR);
                drinkEntrysPanel.add(button);

                button.addActionListener(e -> {
                    drinkManager.removeDrinkEntry(drink);
                    saveManager.saveDrinkEntrys();
                    if (drinkManager.getDrinkEntrys().size() == 0) {
                        drinkEntryDelMode = false;
                    }
                    drinkEntrysPanel.removeAll();
                    updateEntryButtons();
                    updateWaterLabel();
                });
            } else if (drinkEntryEditMode) {
                JButton button = DrinkButtonFactory.createDrinkEntryButton(drink, Constants.EDIT_BUTTON_COLOR);
                drinkEntrysPanel.add(button);

                button.addActionListener(e -> {
                    dialogManager.showEditDrinkEntrysDialog(drink);
                    saveManager.saveDrinkEntrys();
                    if (drinkManager.getDrinkEntrys().size() == 0) {
                        drinkEntryEditMode = false;
                    }
                    drinkEntrysPanel.removeAll();
                    updateEntryButtons();
                    updateWaterLabel();
                });

            } else {
                JButton button = DrinkButtonFactory.createDrinkEntryButton(drink, Constants.BUTTON_COLOR);
                drinkEntrysPanel.add(button);
            }
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

    public void updateEntryButtons() {
        buildDrinkEntryButtons(drinkEntrysScrollPanel);
        updatePanels();
    }

    public void updateOptionButtons() {
        buildDrinkOptionButtons(drinkOptionsScrollPanel);
        updatePanels();
    }

    public void initPresetDrinkOptions() {

        drinkManager.addDrinkOption(new DrinkOption("Wasser", DrinkIcon.WATER_1, 0, 100));
        drinkManager.addDrinkOption(new DrinkOption("Kaffee", DrinkIcon.COFFEE_1, 0, 98));
        drinkManager.addDrinkOption(new DrinkOption("Cola", DrinkIcon.COKE_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Milch", DrinkIcon.MILK_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Bier", DrinkIcon.BEER_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Kakao", DrinkIcon.CACAO_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Energy Drink", DrinkIcon.ENERGY_DRINK_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Fruchtwasser", DrinkIcon.FRUIT_WATER_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Soda", DrinkIcon.SODA_1, 0, 90));
        drinkManager.addDrinkOption(new DrinkOption("Tee", DrinkIcon.TEA_1, 0, 90));
    }

}