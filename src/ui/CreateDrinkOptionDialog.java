package ui;

import java.awt.BorderLayout;
import java.awt.Color;
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

import layout.WrapLayout;
import model.DrinkOption;
import service.DrinkManager;
import util.Constants;
import util.DrinkIcon;
import util.Validator;

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

    DrinkManager drinkManager;
    private boolean isQuickEntry;

    public CreateDrinkOptionDialog(JFrame parentFrame, DrinkManager drinkManager, Boolean isQuickEntry) {

        this.drinkManager = drinkManager;
        this.isQuickEntry = isQuickEntry;

        // Panels
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        iconScrollPanel = new JPanel(new WrapLayout());
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
        nameErrorLabel = new JLabel("");
        if (isQuickEntry) {
            sizeLabel = new JLabel("Größe (ml) :");
            sizeErrorLabel = new JLabel("");
            labelList.add(sizeLabel);
            labelErrList.add(sizeErrorLabel);
        }
        waterPLabel = new JLabel("Wasser (%):");
        waterPErrorLabel = new JLabel("");

        labelList.add(nameLabel);
        labelList.add(waterPLabel);

        labelErrList.add(nameErrorLabel);
        labelErrList.add(waterPErrorLabel);

        // Buttons
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(e -> {
            dispose();
        });

        JButton okayButton = new JButton("Ok");

        okayButton.addActionListener(e -> {
            okayButtonAction();
        });

        buttonList.add(cancelButton);
        buttonList.add(okayButton);

        createIconButtons();

        // -------------------------------------------------------------------

        add(inputPanel, BorderLayout.CENTER);
        add(optionPanel, BorderLayout.SOUTH);

        inputPanel.add(gridPanel);
        inputPanel.add(iconScrollPane);

        Insets normalInsets = new Insets(10, 10, 10, 10);
        Insets errorInsets = new Insets(0, 0, 0, 0);

        setGbcLabel(gbc, 0, 0, normalInsets);
        gridPanel.add(nameLabel, gbc);

        setGbcTextfield(gbc, 1, 0, normalInsets);
        gridPanel.add(nameTextField, gbc);

        setGbcLabel(gbc, 0, 1, errorInsets);
        gridPanel.add(nameErrorLabel, gbc);

        if (isQuickEntry) {
            setGbcLabel(gbc, 0, 2, normalInsets);
            gridPanel.add(sizeLabel, gbc);

            setGbcTextfield(gbc, 1, 2, normalInsets);
            gridPanel.add(sizeTextField, gbc);

            setGbcLabel(gbc, 0, 3, errorInsets);
            gridPanel.add(sizeErrorLabel, gbc);
        }

        setGbcLabel(gbc, 0, 4, normalInsets);
        gridPanel.add(waterPLabel, gbc);

        setGbcTextfield(gbc, 1, 4, normalInsets);
        gridPanel.add(waterPTextField, gbc);

        setGbcLabel(gbc, 0, 5, errorInsets);
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
            label.setFont(new Font("", 1, 10));
        }

        for (JButton button : buttonList) {
            button.setBackground(Constants.BUTTON_COLOR);
        }

        for (JTextField textField : textFieldList) {
        }

        pack();
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

        if (isQuickEntry) {
            // Size
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

    public void okayButtonAction() {
        if (checkTextFields()) {
            String name = nameTextField.getText();
            int waterP = Integer.parseInt(waterPTextField.getText());

            if (isQuickEntry) {
                int size = Integer.parseInt(sizeTextField.getText());

                this.drinkManager.addDrinkEntry(new DrinkOption(name, icon, size, waterP));
            } else {
                this.drinkManager.addDrinkOption(new DrinkOption(name, icon, 0, waterP));
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

    private void setGbcLabel(GridBagConstraints gbc, int x, int y, Insets insets) {
        gbc.insets = insets;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
    }

    private void setGbcTextfield(GridBagConstraints gbc, int x, int y, Insets insets) {
        gbc.insets = insets;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }
}
