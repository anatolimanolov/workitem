package com.company.models.workitems.enums;

public enum Priority {
    HIGH,
    MEDIUM,
    LOW;

    public static Priority customValueOf(String priority) {
        for (Priority pr : values()) {
            if (pr.name().equals(priority.toUpperCase())) {
                return pr;
            }
        }
        throw new IllegalArgumentException("Invalid priority: " + priority);
    }
}
