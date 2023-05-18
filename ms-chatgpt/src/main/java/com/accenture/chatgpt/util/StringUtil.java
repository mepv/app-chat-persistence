package com.accenture.chatgpt.util;

public class StringUtil {
    /**
     * Replace string
     *
     * @param str
     * @param value
     * @return
     */
    public static String replaceText(String str, String value) {
//        String answer = str.substring(str.indexOf("{")+1, str.indexOf("}"));
        return str.replaceAll("\\{[^}]+\\}", value);
    }
}
