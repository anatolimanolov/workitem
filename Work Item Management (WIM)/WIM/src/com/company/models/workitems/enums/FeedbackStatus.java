package com.company.models.workitems.enums;

public enum FeedbackStatus {
    NEW,
    UNSCHEDULED,
    SCHEDULED,
    DONE;

    public static FeedbackStatus customValueOf(String feedbackStatus) {
        for (FeedbackStatus status : values()) {
            if (status.name().equals(feedbackStatus.toUpperCase())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid feedback status: " + feedbackStatus);
    }
}
