package de.tjark.waterlog.util;

public class Validator {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static String validateInt(String input, int min, int max) {

        if (input.equals("")) {
            return "Eingabe darf nicht leer sein";
        }

        if (!Validator.isInteger(input)) {
            return "Eingabe muss eine Zahl sein";
        }
        int number = Integer.parseInt(input);

        if (number < min) {
            return "Zahl muss größer oder gleich " + min + " sein";
        }

        if (number > max) {
            return "Zahl muss kleiner oder gleich " + max + " sein";
        }

        return "";

    }

    public static String validateString(String input, int maxLength) {

        if (input.isEmpty()) {
            return "Eingabe darf nicht leer sein";
        }

        if (input.length() > maxLength) {
            return "Eingabe ist zu lang";
        }

        return "";
    }
}
