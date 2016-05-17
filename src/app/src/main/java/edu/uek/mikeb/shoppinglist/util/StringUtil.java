package edu.uek.mikeb.shoppinglist.util;

import java.util.Locale;

/**
 * Created by bmik on 2015-05-26.
 */
public class StringUtil {

    private static final Locale LOCALE = new Locale("pl", "PL");

    public static double parseDoubleValue(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        return Double.parseDouble(s.replace(',', '.'));
    }

    public static String parseStringFromDouble(double d) {
        if (d % 1 == 0) {
            return String.format(LOCALE, "%.0f", d);
        } else {
            return String.format(LOCALE, "%.2f", d);
        }
    }

}
