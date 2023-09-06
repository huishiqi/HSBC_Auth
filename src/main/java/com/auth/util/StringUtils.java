package com.auth.util;

public class StringUtils {
    public static boolean isBlank(String input) {
        return input == null || input.isEmpty() || input.trim().isEmpty();
    }
}
