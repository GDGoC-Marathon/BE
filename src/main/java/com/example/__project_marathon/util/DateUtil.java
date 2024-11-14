package com.example.__project_marathon.util;

public class DateUtil {
    public static String formatDate(String date) {
        return "2024-" + date.replaceAll("[^0-9.]", "").replace(".", "-").substring(0, 5);
    }
}