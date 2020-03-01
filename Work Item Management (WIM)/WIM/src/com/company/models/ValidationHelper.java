package com.company.models;

public class ValidationHelper {

    public static void checkNameValidation(String name, int minLength, int maxLength, String errorMessage) {
        if (name.length() < minLength || name.length() > maxLength) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkRatingRange(int rating, int minValue, int maxValue, String exceptionMessage) {
        if (rating < minValue || rating > maxValue) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
