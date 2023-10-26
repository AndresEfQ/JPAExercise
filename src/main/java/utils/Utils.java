package utils;

import java.util.List;

public class Utils {

    public static void checkEmptyString(String value) throws Exception {
        if (value.isEmpty()) {
            System.out.println("Going back to the previous menu");
            throw new IllegalArgumentException("The value can't be empty");
        }
    }

    public static void checkRepeatedValue(List<String> list, String value) {
        for (String aux : list) {
            if (value.equals(aux)) {
                System.out.println("Going back to the previous menu");
                throw new IllegalArgumentException("The value can't be repeated");
            }
        }
    }
}
