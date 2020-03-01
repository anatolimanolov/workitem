package com.company.models.workitems.enums;

public enum BugStatus {
    ACTIVE,
    FIXED;

    public static BugStatus customValueOf(String bugStatus) {
        for (BugStatus status : values()) {
            if (status.name().equals(bugStatus.toUpperCase())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid bug status: " + bugStatus);
    }
}
