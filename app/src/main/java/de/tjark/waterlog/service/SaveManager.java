package de.tjark.waterlog.service;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.tjark.waterlog.model.DrinkOption;
import de.tjark.waterlog.util.DrinkIcon;
import de.tjark.waterlog.util.Validator;

public class SaveManager {

    DrinkManager drinkManager;

    public SaveManager(DrinkManager drinkManager) {
        this.drinkManager = drinkManager;
    }

    private final Path BASE_PATH = Path.of(System.getProperty("user.home"), "saves", "WaterLog");
    private final Path DRINK_ENTRYS_PATH = BASE_PATH.resolve("DrinkEntrys.txt");
    private final Path DRINK_OPTIONS_PATH = BASE_PATH.resolve("DrinkOptions.txt");

    public void loadDrinkEntrys() {
        Path path = DRINK_ENTRYS_PATH;
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
                    if (lineElements.length != 4) {
                        throw new IOException("Beschädigte Zeile: " + line);
                    }
                    name = lineElements[0];
                    if (Validator.isInteger(lineElements[1])) {
                        size = Integer.parseInt(lineElements[1]);
                    } else {
                        System.out.println("Load failed. Reason: Size Parameter corrupted");
                        return;
                    }
                    if (Validator.isInteger(lineElements[2])) {
                        waterP = Integer.parseInt(lineElements[2]);

                    } else {
                        System.out.println("Load failed. Reason: Water% Parameter corrupted");
                        return;
                    }
                    DrinkIcon icon = DrinkIcon.valueOf(lineElements[3]);

                    this.drinkManager.addDrinkEntry(new DrinkOption(name, icon, size, waterP));
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Getränke Einträge konnten nicht geladen werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "löschen sie die 'DrinkEntries.txt' Datei in user/water-log.",
                    "Ladungs Fehler",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void saveDrinkEntrys() {
        LocalDate todayDate = LocalDate.now();
        try {
            Path path = DRINK_ENTRYS_PATH;
            List<String> lines = Files.readAllLines(path);
            List<String> newLines = new ArrayList<>();

            for (String line : lines) {

                if (line.startsWith(">") && LocalDate.parse(line.substring(1)).equals(todayDate))
                    break;
                newLines.add(line);
            }

            newLines.add(">" + LocalDate.now());
            for (DrinkOption drink : drinkManager.getAllDrinkEntrys()) {
                newLines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP() + ";" + drink.getIcon());
            }
            newLines.add("");

            Files.write(path, newLines);

        } catch (

        Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Getränke Einträge konnten nicht gespeichert werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "löschen sie die 'DrinkEntries.txt' Datei in user/water-log.",
                    "Speicher Fehler",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void loadDrinkOptions() {
        Path path = DRINK_OPTIONS_PATH;
        try {
            List<String> lines = Files.readAllLines(path);
            String[] lineElements = new String[2];

            for (String line : lines) {
                String name;
                int size;
                int waterP;

                lineElements = line.split(";");
                if (lineElements.length != 4) {
                    throw new IOException("Beschädigte Zeile: " + line);
                }
                name = lineElements[0];
                if (Validator.isInteger(lineElements[1])) {
                    size = Integer.parseInt(lineElements[1]);
                } else {
                    System.out.println("Load failed. Reason: Size Parameter corrupted");
                    return;
                }
                if (Validator.isInteger(lineElements[2])) {
                    waterP = Integer.parseInt(lineElements[2]);
                } else {
                    System.out.println("Load failed. Reason: Water% Parameter corrupted");
                    return;
                }
                DrinkIcon icon = DrinkIcon.valueOf(lineElements[3]);

                drinkManager.addDrinkOption(new DrinkOption(name, icon, size, waterP));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Getränke Optionen konnten nicht geladen werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "löschen sie die 'DrinkOptions.txt' Datei in user/water-log.",
                    "Ladungs Fehler",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void saveDrinkOptions() {
        try {
            Path path = DRINK_OPTIONS_PATH;
            List<String> lines = new ArrayList<>();

            for (DrinkOption drink : drinkManager.getAllDrinkOptions()) {
                lines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP() + ";" + drink.getIcon());
            }

            Files.write(path, lines);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Getränke Optionen konnten nicht gespeichert werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "löschen sie die 'DrinkOptions.txt' Datei in user/water-log.",
                    "Speicher Fehler",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void openDrinkOptionsFile() {

        try {
            Desktop.getDesktop().open(this.getCustomLogPath().toFile());
        } catch (IOException exeption) {
            JOptionPane.showMessageDialog(
                    null,
                    "Die Getränke Optionen Datei konnten nicht geöffnet werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "löschen sie die 'DrinkOptions.txt' Datei in user/water-log.",
                    "Öffungs Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void openDrinkEntrysFile() {
        try {
            Desktop.getDesktop().open(this.getDailyLogPath().toFile());
        } catch (IOException exeption) {
            JOptionPane.showMessageDialog(
                    null,
                    "Die Getränke Einträge Datei konnten nicht geöffnet werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "löschen sie die 'DrinkEntrys.txt' Datei in user/water-log.",
                    "Öffungs Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter
    public Path getBasePath() {
        return BASE_PATH;
    }

    public Path getDailyLogPath() {
        return DRINK_ENTRYS_PATH;
    }

    public Path getCustomLogPath() {
        return DRINK_OPTIONS_PATH;
    }

    public void createSaveFiles() {
        try {
            Files.createDirectories(this.getBasePath());

            if (!Files.exists(this.getDailyLogPath())) {
                Files.createFile(this.getDailyLogPath());
            }
            if (!Files.exists(this.getCustomLogPath())) {
                Files.createFile(this.getCustomLogPath());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Die Getränke Einträge Datei und Getränke Optionen Datei konnten nicht erzeugt werden.\n\n"
                            + "Bitte starten Sie die Anwendung erneut.\n"
                            + "Falls das Problem bestehen bleibt,\n "
                            + "Falls das Problem bestehen bleibt, wenden Sie sich an den Entwickler.",
                    "Speicher Fehler",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
