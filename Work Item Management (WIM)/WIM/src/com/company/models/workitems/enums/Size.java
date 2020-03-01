package com.company.models.workitems.enums;

public enum Size {
    LARGE,
    MEDIUM,
    SMALL;

    public static Size customValueOf(String size) {
        for (Size sz : values()) {
            if (sz.name().equals(size.toUpperCase())) {
                return sz;
            }
        }
        throw new IllegalArgumentException("Invalid size: " + size);
    }
}
