package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.DrinkOption;
import util.Validator;

public class SaveManager {

    DrinkManager drinkManager;

    public SaveManager(DrinkManager drinkManager) {
        this.drinkManager = drinkManager;
    }

    private final Path BASE_PATH = Path.of(System.getProperty("user.home"), "saves", "WaterLog");
    private final Path DAILY_LOG = BASE_PATH.resolve("DailyDrinksLog.txt");
    private final Path CUSTOM_LOG = BASE_PATH.resolve("CustomDrinkOptions.txt");

    public void loadDailyDrinksLog() {
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
                    this.drinkManager.addDrink(new DrinkOption(name, null, size, waterP));
                }
            }

        } catch (

        IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDailyDrinksLog() {
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
            for (DrinkOption drink : drinkManager.getDailyDrinks()) {
                newLines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP());
            }
            newLines.add("");

            Files.write(path, newLines);

        } catch (

        IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCustomDrinkOptions() {
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
                drinkManager.addDrinkOption(new DrinkOption(name, null, size, waterP)); // fix icon
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomDrinkOptions() {
        try {
            Path path = CUSTOM_LOG;
            List<String> lines = new ArrayList<>();

            for (DrinkOption drink : drinkManager.getDrinkOptions()) {
                lines.add(drink.getName() + ";" + drink.getSize() + ";" + drink.getWaterP());
            }

            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter
    public Path getBasePath() {
        return BASE_PATH;
    }

    public Path getDailyLogPath() {
        return DAILY_LOG;
    }

    public Path getCustomLogPath() {
        return CUSTOM_LOG;
    }
}
