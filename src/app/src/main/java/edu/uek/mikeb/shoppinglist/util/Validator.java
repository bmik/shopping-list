package edu.uek.mikeb.shoppinglist.util;

import java.util.regex.Pattern;

/**
 * Created by ahmed on 17.05.2016.
 */
public class Validator {

    private static final Pattern DOUBLE_PATTERN = Pattern.compile(
            "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
                    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
                    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
                    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNumber(String s) {
        return DOUBLE_PATTERN.matcher(s).matches();
    }

}
