package com.accenture.chatgpt.util;

public class StringUtil {

    private StringUtil() {}

    /**
     * Replace string
     *
     * @param str
     * @param value
     * @return
     */
    public static String replaceText(String str, String value) {
        return str.replaceAll("\\{[^}]}", value);
    }
}
