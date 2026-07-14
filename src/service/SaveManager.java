package service;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.DrinkOption;
import util.DrinkIcon;
import util.Validator;

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

        } catch (

        IOException e) {
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
            for (DrinkOption drink : drinkManager.getDrinkEntrys()) {
                newLines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP() + ";" + drink.getIcon());
            }
            newLines.add("");

            Files.write(path, newLines);

        } catch (

        IOException e) {
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
            e.printStackTrace();
        }
    }

    public void saveDrinkOptions() {
        try {
            Path path = DRINK_OPTIONS_PATH;
            List<String> lines = new ArrayList<>();

            for (DrinkOption drink : drinkManager.getDrinkOptions()) {
                lines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP() + ";" + drink.getIcon());
            }

            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDrinkOptionsFile() {

        try {
            Desktop.getDesktop().open(this.getCustomLogPath().toFile());
        } catch (IOException exeption) {
        }
    }

    public void openDrinkEntrysFile() {
        try {
            Desktop.getDesktop().open(this.getDailyLogPath().toFile());
        } catch (IOException exeption) {
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
            e.printStackTrace();
        }
    }
}
