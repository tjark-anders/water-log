package de.tjark.waterlog.ui;

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

import de.tjark.waterlog.util.Constants;
import de.tjark.waterlog.util.DrinkIcon;
import de.tjark.waterlog.util.IconLoader;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import de.tjark.waterlog.layout.WrapLayout;
import de.tjark.waterlog.model.DrinkOption;
import de.tjark.waterlog.service.DrinkManager;
import de.tjark.waterlog.service.SaveManager;
import de.tjark.waterlog.service.WaterCalculator;

public class MainWindow {

    public boolean drinkOptionDelMode = false;
    public boolean drinkEntryDelMode = false;

    public boolean drinkOptionEditMode = false;
    public boolean drinkEntryEditMode = false;

    private int waterGoal = 3000;
    private final String VERSION_TEXT = "v1.0.1";

    private JLabel waterLabel;
    private JProgressBar waterProgressBar;

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
        frame.setIconImage(IconLoader.APP_ICON.getImage());

        // ------------------------------- Panels -------------------------------------

        JPanel rootPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel topHeaderPanel = new JPanel(new BorderLayout());

        JPanel centerHeaderPanel = new JPanel();
        centerHeaderPanel.setLayout(new BoxLayout(centerHeaderPanel, BoxLayout.Y_AXIS));

        JPanel fileButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel drinkEntrysPanel = new JPanel(new BorderLayout(10, 0));

        drinkEntrysScrollPanel = new JPanel();
        drinkEntrysScrollPanel.setLayout(new BoxLayout(drinkEntrysScrollPanel, BoxLayout.Y_AXIS));

        JPanel drinkEntrysButtonPanel = new JPanel();
        drinkEntrysButtonPanel.setLayout(new BoxLayout(drinkEntrysButtonPanel, BoxLayout.Y_AXIS));
        drinkEntrysButtonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel drinkOptionsPanel = new JPanel(new BorderLayout());

        JPanel drinkOptionsButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        drinkOptionsScrollPanel = new JPanel(new WrapLayout(FlowLayout.LEFT));

        panelList.add(rootPanel);
        panelList.add(headerPanel);
        panelList.add(topHeaderPanel);
        panelList.add(centerHeaderPanel);
        panelList.add(fileButtonPanel);
        panelList.add(drinkEntrysPanel);
        panelList.add(drinkEntrysButtonPanel);
        panelList.add(drinkEntrysScrollPanel);
        panelList.add(drinkOptionsPanel);
        panelList.add(drinkOptionsButtonPanel);
        panelList.add(drinkOptionsScrollPanel);

        // ------------------------------- Loading -------------------------------------

        saveManager.loadDrinkOptions();
        saveManager.loadDrinkEntrys();

        if (drinkManager.getAllDrinkOptions().size() == 0) {
            initPresetDrinkOptions();
        }

        // ------------------------------- Labels -------------------------------------

        JLabel versionLabel = new JLabel(VERSION_TEXT);
        versionLabel.setFont(new Font("", 1, 10));
        versionLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel headerLabel = new JLabel("WaterLog");
        headerLabel.setFont(new Font("", 1, 80));

        waterLabel = new JLabel("");
        waterLabel.setFont(new Font("", 1, 20));

        JLabel drinkEntrysLabel = new JLabel("Verlauf");
        drinkEntrysLabel.setFont(new Font("", 1, 20));

        JLabel drinkOptionsLabel = new JLabel("Getränke");
        drinkOptionsLabel.setFont(new Font("", 1, 20));

        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        waterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelList.add(versionLabel);
        labelList.add(headerLabel);
        labelList.add(waterLabel);
        labelList.add(drinkOptionsLabel);
        labelList.add(drinkEntrysLabel);

        updateWaterLabel();

        // ------------------------------- Water-Bar -------------------------------
        waterProgressBar = new JProgressBar(0, waterGoal);
        waterProgressBar.setPreferredSize(new Dimension(700, 20));
        waterProgressBar.setMaximumSize(waterProgressBar.getPreferredSize());

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

        JButton openDrinkOptionsButton = DrinkButtonFactory.createFunktionButton("Getränke öffnen");
        openDrinkOptionsButton.addActionListener(e -> {
            saveManager.openDrinkOptionsFile();
        });

        JButton openDrinkEntrysButton = DrinkButtonFactory.createFunktionButton("Einträge öffnen");
        openDrinkEntrysButton.addActionListener(e -> {
            saveManager.openDrinkEntrysFile();
        });

        buildDrinkOptionButtons(drinkOptionsScrollPanel);
        buildDrinkEntryButtons(drinkEntrysScrollPanel);

        // ------------------------------- Scroll-Panes -------------------------------

        JScrollPane historyScrollPane = new JScrollPane(drinkEntrysScrollPanel);
        historyScrollPane.setPreferredSize(new Dimension(200, 700));
        historyScrollPane.setMaximumSize(historyScrollPane.getPreferredSize());
        historyScrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        JScrollPane selectionScrollPane = new JScrollPane(drinkOptionsScrollPanel);
        selectionScrollPane.setPreferredSize(new Dimension(100, 100));
        selectionScrollPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        // ------------------------------- Panel-fusion -------------------------------

        // Basics
        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(drinkEntrysPanel, BorderLayout.WEST);
        rootPanel.add(drinkOptionsPanel, BorderLayout.CENTER);
        frame.add(rootPanel);

        // Elements
        headerPanel.add(topHeaderPanel, BorderLayout.NORTH);
        headerPanel.add(centerHeaderPanel, BorderLayout.CENTER);

        topHeaderPanel.add(versionLabel, BorderLayout.EAST);
        topHeaderPanel.add(fileButtonPanel);

        fileButtonPanel.add(openDrinkEntrysButton);
        fileButtonPanel.add(openDrinkOptionsButton);

        centerHeaderPanel.add(headerLabel);
        centerHeaderPanel.add(waterProgressBar);
        centerHeaderPanel.add(waterLabel);

        drinkEntrysPanel.add(drinkEntrysLabel, BorderLayout.NORTH);
        drinkEntrysPanel.add(historyScrollPane, BorderLayout.CENTER);
        drinkEntrysPanel.add(drinkEntrysButtonPanel, BorderLayout.SOUTH);

        drinkEntrysButtonPanel.add(historyEditButton);
        drinkEntrysButtonPanel.add(historyAddButton);
        drinkEntrysButtonPanel.add(historyRemoveButton);

        drinkOptionsPanel.add(drinkOptionsLabel, BorderLayout.NORTH);
        drinkOptionsPanel.add(drinkOptionsButtonPanel, BorderLayout.SOUTH);
        drinkOptionsPanel.add(selectionScrollPane, BorderLayout.CENTER);

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
                    && !panel.equals(topHeaderPanel)
                    && !panel.equals(centerHeaderPanel)
                    && !panel.equals(fileButtonPanel)
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

        updateWater();
    }

    // ------------------------------- Button-Actions ---------------------------

    private void drinkEntryAddAction() {
        new CreateDrinkOptionDialog(this.frame, drinkManager, null, true);
        saveManager.saveDrinkEntrys();
        updateEntryButtons();
        updateWaterLabel();
    }

    private void drinkEntryRemoveAction() {
        if (drinkManager.getAllDrinkEntrys().size() == 0)
            return;
        drinkEntryEditMode = false;
        drinkEntryDelMode = !drinkEntryDelMode;
        updateEntryButtons();
    }

    private void drinkEntryEditAction() {
        if (drinkManager.getAllDrinkEntrys().size() == 0)
            return;
        drinkEntryDelMode = false;
        drinkEntryEditMode = !drinkEntryEditMode;
        updateEntryButtons();
    }

    private void drinkOptionAddAction() {
        new CreateDrinkOptionDialog(this.frame, drinkManager, null, false);
        saveManager.saveDrinkOptions();
        updateOptionButtons();
    }

    private void drinkOptionRemoveAction() {
        if (drinkManager.getAllDrinkOptions().size() == 0)
            return;
        drinkOptionEditMode = false;
        drinkOptionDelMode = !drinkOptionDelMode;
        updateOptionButtons();
    }

    private void drinkOptionEditAction() {
        if (drinkManager.getAllDrinkOptions().size() == 0)
            return;
        drinkOptionDelMode = false;
        drinkOptionEditMode = !drinkOptionEditMode;
        updateOptionButtons();
    }

    // ------------------------------- Helper-Funktions ---------------------------

    public void buildDrinkOptionButtons(JPanel drinkOptionsScrollPanel) {
        drinkOptionsScrollPanel.removeAll();

        for (DrinkOption drink : drinkManager.getAllDrinkOptions()) {

            if (drinkOptionDelMode) {
                JButton button = DrinkButtonFactory.createDrinkOptionButton(drink, Constants.DEL_BUTTON_COLOR);
                drinkOptionsScrollPanel.add(button);

                button.addActionListener(e -> {
                    drinkManager.removeDrinkOption(drink);
                    drinkOptionsScrollPanel.removeAll();
                    saveManager.saveDrinkOptions();
                    if (drinkManager.getAllDrinkOptions().size() == 0) {
                        drinkOptionDelMode = false;
                    }
                    updateOptionButtons();
                });

            } else if (drinkOptionEditMode) {
                JButton button = DrinkButtonFactory.createDrinkOptionButton(drink, Constants.EDIT_BUTTON_COLOR);
                drinkOptionsScrollPanel.add(button);

                button.addActionListener(e -> {

                    new CreateDrinkOptionDialog(frame, drinkManager, drink, false);
                    saveManager.saveDrinkOptions();
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

        for (int i = drinkManager.getAllDrinkEntrys().size() - 1; i >= 0; i--) {
            DrinkOption drink = drinkManager.getDrinkEntry(i);

            if (drinkEntryDelMode) {
                JButton button = DrinkButtonFactory.createDrinkEntryButton(drink, Constants.DEL_BUTTON_COLOR);
                drinkEntrysPanel.add(button);

                button.addActionListener(e -> {
                    drinkManager.removeDrinkEntry(drink);
                    saveManager.saveDrinkEntrys();
                    if (drinkManager.getAllDrinkEntrys().size() == 0) {
                        drinkEntryDelMode = false;
                    }
                    drinkEntrysPanel.removeAll();
                    updateEntryButtons();
                    updateWater();
                });
            } else if (drinkEntryEditMode) {
                JButton button = DrinkButtonFactory.createDrinkEntryButton(drink, Constants.EDIT_BUTTON_COLOR);
                drinkEntrysPanel.add(button);

                button.addActionListener(e -> {
                    new CreateDrinkOptionDialog(frame, drinkManager, drink, true);
                    saveManager.saveDrinkEntrys();
                    drinkEntrysPanel.removeAll();
                    updateEntryButtons();
                    updateWater();
                });

            } else {
                JButton button = DrinkButtonFactory.createDrinkEntryButton(drink, Constants.BUTTON_COLOR);
                drinkEntrysPanel.add(button);
                updateWater();
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

    public void updateWaterProgressBar() {
        int currWater = waterCalculator.getCurrWater();
        if (currWater <= waterGoal) {
            waterProgressBar.setValue(currWater);
        } else {
            waterProgressBar.setValue(waterGoal);
        }
    }

    private void updateWater() {
        updateWaterLabel();
        updateWaterProgressBar();
        updatePanels();
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

        drinkManager.addDrinkOption(new DrinkOption("Wasser", DrinkIcon.WATER, 0, 100));
        drinkManager.addDrinkOption(new DrinkOption("Kaffee", DrinkIcon.COFFEE, 0, 98));
        drinkManager.addDrinkOption(new DrinkOption("Milch", DrinkIcon.MILK, 0, 100));
    }

}