package com.company.models.workitems.enums;

public enum BugSeverity {
    CRITICAL,
    MAJOR,
    MINOR;

    public static BugSeverity customValueOf(String bugSeverity) {
        for (BugSeverity severity : values()) {
            if (severity.name().equals(bugSeverity.toUpperCase())) {
                return severity;
            }
        }
        throw new IllegalArgumentException("Invalid severity: " + bugSeverity);
    }
}
