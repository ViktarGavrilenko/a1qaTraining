package utils;

public class StringUtils {
    public static String addSpaceBeforeParenthesis(String str) {
        int numberParenthesis = str.indexOf("(");
        if (numberParenthesis != -1) {
            return str.substring(0, numberParenthesis) + " " + str.substring(numberParenthesis);
        } else {
            return str;
        }
    }
}