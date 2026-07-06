package util;

public class Validator {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
