package de.tjark.waterlog.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.tjark.waterlog.layout.WrapLayout;
import de.tjark.waterlog.model.DrinkOption;
import de.tjark.waterlog.service.DrinkManager;
import de.tjark.waterlog.util.Constants;
import de.tjark.waterlog.util.DrinkIcon;
import de.tjark.waterlog.util.Validator;

public class CreateDrinkOptionDialog extends JDialog {

    private ArrayList<JPanel> panelList = new ArrayList<>();
    private ArrayList<JLabel> labelList = new ArrayList<>();
    private ArrayList<JLabel> labelErrList = new ArrayList<>();
    private ArrayList<JButton> buttonList = new ArrayList<>();
    private ArrayList<JTextField> textFieldList = new ArrayList<>();

    private JPanel iconScrollPanel;

    private DrinkIcon icon = DrinkIcon.DEFAULT_ICON;

    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JLabel waterPLabel;

    private JLabel nameErrorLabel;
    private JLabel sizeErrorLabel;
    private JLabel waterPErrorLabel;

    private JTextField nameTextField;
    private JTextField sizeTextField;
    private JTextField waterPTextField;

    GridBagConstraints gbc;
    Insets normalInsets;
    Insets errorInsets;

    DrinkManager drinkManager;
    private boolean isQuickEntry;
    private boolean isEditMode = false;

    public CreateDrinkOptionDialog(JFrame parentFrame, DrinkManager drinkManager, DrinkOption drink,
            Boolean isQuickEntry) {

        this.drinkManager = drinkManager;
        this.isQuickEntry = isQuickEntry;

        if (drink != null) {
            isEditMode = true;
        }

        // Panels
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        iconScrollPanel = new JPanel(new WrapLayout(FlowLayout.LEFT));
        JPanel optionPanel = new JPanel(new FlowLayout());

        panelList.add(inputPanel);
        panelList.add(gridPanel);
        panelList.add(iconScrollPanel);
        panelList.add(optionPanel);

        // Scroll Pane
        JScrollPane iconScrollPane = new JScrollPane(iconScrollPanel);

        // Text fields
        nameTextField = new JTextField(15);
        sizeTextField = new JTextField(15);
        waterPTextField = new JTextField(15);

        textFieldList.add(nameTextField);
        textFieldList.add(sizeTextField);
        textFieldList.add(waterPTextField);

        // Labels
        nameLabel = new JLabel("Name:");
        nameErrorLabel = new JLabel(" ");
        if (isQuickEntry) {
            sizeLabel = new JLabel("Größe (ml) :");
            sizeErrorLabel = new JLabel(" ");
            labelList.add(sizeLabel);
            labelErrList.add(sizeErrorLabel);
            sizeErrorLabel.setHorizontalAlignment(JLabel.RIGHT);

        }
        waterPLabel = new JLabel("Wasser (%):");
        waterPErrorLabel = new JLabel(" ");

        labelList.add(nameLabel);
        labelList.add(waterPLabel);

        labelErrList.add(nameErrorLabel);
        labelErrList.add(waterPErrorLabel);

        nameErrorLabel.setHorizontalAlignment(JLabel.RIGHT);
        waterPErrorLabel.setHorizontalAlignment(JLabel.RIGHT);

        // Buttons
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        JButton okayButton = new JButton("Ok");

        okayButton.addActionListener(e -> {
            okayButtonAction(drink);
        });

        buttonList.add(cancelButton);
        buttonList.add(okayButton);

        // -------------------------------------------------------------------

        add(inputPanel, BorderLayout.CENTER);
        add(optionPanel, BorderLayout.SOUTH);

        inputPanel.add(gridPanel);
        inputPanel.add(iconScrollPane);

        normalInsets = new Insets(10, 10, 10, 10);
        errorInsets = new Insets(0, 0, 0, 0);

        setGbcLabel(0, 0);
        gridPanel.add(nameLabel, gbc);

        setGbcTextfield(1, 0);
        gridPanel.add(nameTextField, gbc);

        setGbcErrLabel(0, 1);
        gridPanel.add(nameErrorLabel, gbc);

        if (isQuickEntry) {
            setGbcLabel(0, 2);
            gridPanel.add(sizeLabel, gbc);

            setGbcTextfield(1, 2);
            gridPanel.add(sizeTextField, gbc);

            setGbcErrLabel(0, 3);
            gridPanel.add(sizeErrorLabel, gbc);
        }

        setGbcLabel(0, 4);
        gridPanel.add(waterPLabel, gbc);

        setGbcTextfield(1, 4);
        gridPanel.add(waterPTextField, gbc);

        setGbcErrLabel(0, 5);
        gridPanel.add(waterPErrorLabel, gbc);

        optionPanel.add(cancelButton);
        optionPanel.add(okayButton);

        // -------------------------------------------------------------------
        for (JPanel panel : panelList) {
            panel.setBackground(Constants.BACKGROUND_COLOR);
            panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        }

        for (JLabel label : labelList) {
            label.setForeground(Constants.TEXT_COLOR);
            label.setFont(new Font("", 1, 15));
        }

        for (JLabel label : labelErrList) {
            label.setForeground(Color.RED);
            label.setFont(new Font("", 1, 12));
        }

        for (JButton button : buttonList) {
            button.setBackground(Constants.BUTTON_COLOR);
        }

        for (JTextField textField : textFieldList) {
        }

        if (isEditMode) {
            setDrinkValues(drink);
            updatePanels();
        }
        createIconButtons();

        setSize(new Dimension(300, 500));
        setTitle("Getränk hinzufügen");
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parentFrame);
        setVisible(true);
    }

    private void createIconButtons() {

        iconScrollPanel.removeAll();

        for (DrinkIcon icon : DrinkIcon.values()) {
            if (icon.equals(DrinkIcon.DEFAULT_ICON)) {
                continue;
            }
            JButton button = DrinkButtonFactory.createIconButton(icon);
            iconScrollPanel.add(button);
            if (icon.equals(this.icon)) {
                button.setBackground(Color.CYAN);
            }

            button.addActionListener(e -> {
                this.icon = icon;
                createIconButtons();
                updatePanels();
            });
        }
    }

    private boolean checkTextFields() {

        boolean check = true;
        int minSize = 1;
        int maxSize = 5000;

        // Name
        String err = Validator.validateString(nameTextField.getText(), 20);

        if (!err.equals("")) {
            nameErrorLabel.setText(err);
            check = false;
        }
        // Size
        if (isQuickEntry) {
            err = Validator.validateInt(sizeTextField.getText(), minSize, maxSize);

            if (!err.equals("")) {
                sizeErrorLabel.setText(err);
                check = false;
            }
        }

        // WaterP
        err = Validator.validateInt(waterPTextField.getText(), 1, 100);

        if (!err.equals("")) {
            waterPErrorLabel.setText(err);
            check = false;
        }

        return check;
    }

    public DrinkIcon geticon() {
        return this.icon;
    }

    public void okayButtonAction(DrinkOption drink) {

        if (checkTextFields()) {
            String name = nameTextField.getText();
            int waterP = Integer.parseInt(waterPTextField.getText());

            if (isEditMode) {
                drink.setName(name);
                drink.setWaterP(waterP);
                drink.setIcon(icon);

                if (isQuickEntry) {
                    int size = Integer.parseInt(sizeTextField.getText());
                    drink.setSize(size);
                }
            } else {
                if (isQuickEntry) {
                    int size = Integer.parseInt(sizeTextField.getText());

                    this.drinkManager.addDrinkEntry(new DrinkOption(name, icon, size, waterP));
                } else {
                    this.drinkManager.addDrinkOption(new DrinkOption(name, icon, 0, waterP));
                }
            }

            dispose();
        } else {
            updatePanels();
        }
    }

    public void updatePanels() {

        for (JPanel panel : panelList) {
            panel.revalidate();
            panel.repaint();
        }
    }

    private void setGbcLabel(int x, int y) {

        gbc.insets = normalInsets;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
    }

    private void setGbcErrLabel(int x, int y) {

        gbc.insets = errorInsets;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
    }

    private void setGbcTextfield(int x, int y) {

        gbc.insets = normalInsets;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }

    private void setDrinkValues(DrinkOption drink) {

        nameTextField.setText(drink.getName());
        waterPTextField.setText(Integer.toString(drink.getWaterP()));
        if (isQuickEntry) {
            sizeTextField.setText(Integer.toString(drink.getSize()));
        }
        icon = drink.getIcon();
    }
}
