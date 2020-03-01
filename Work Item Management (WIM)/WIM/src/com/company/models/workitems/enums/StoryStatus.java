package com.company.models.workitems.enums;

public enum StoryStatus {
    NOTDONE,
    INPROGRESS,
    DONE;

    public static StoryStatus customValueOf(String storyStatus) {
        for (StoryStatus status : values()) {
            if (status.name().equals(storyStatus.toUpperCase())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid story status: " + storyStatus);
    }
}
