package utils;

import aquality.selenium.core.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String INVALID_STR = "Invalid string";

    public static int getNumberFromStr(String str) {
        Matcher matcher = Pattern.compile("\\d+").matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        } else {
            Logger.getInstance().info(INVALID_STR);
            throw new IllegalArgumentException(INVALID_STR);
        }
    }

    public static float getPriceFromStr(String str) {
        Matcher matcher = Pattern.compile("\\d+[.,]\\d+").matcher(str);
        if (matcher.find()) {
            return Float.parseFloat(matcher.group());
        } else {
            Logger.getInstance().info(INVALID_STR);
            throw new IllegalArgumentException(INVALID_STR);
        }
    }

    public static String getNameFileFromUrl(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static String addSpaceBeforeParenthesis(String str) {
        int numberParenthesis = str.indexOf("(");
        if (numberParenthesis != -1) {
            return str.substring(0, numberParenthesis) + " " + str.substring(numberParenthesis);
        } else {
            return str;
        }
    }
}