package com.company.models.workitems;

import com.company.models.ValidationHelper;
import com.company.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkItemsBaseImpl implements WorkItem {

    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 50;
    private static final String TITLE_ERROR_MESSAGE =
            String.format("Title length must be between %d and %d characters", MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);
    private static final int MIN_DESCRIPTION_LENGTH = 10;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final String DESCRIPTION_ERROR_MESSAGE =
            String.format("Description length must be between %d and %d characters", MIN_TITLE_LENGTH, MAX_TITLE_LENGTH);

    private long itemID;
    private String title;
    private String description;
    private List<String> comments;
    private List<String> activityHistory;
    private String board;

    public WorkItemsBaseImpl(long itemID, String title, String description) {
        this.itemID = itemID;
        setTitle(title);
        setDescription(description);
        activityHistory = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public long getItemID() {
        return itemID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public abstract List<String> getComments();

    @Override
    public abstract List<String> getHistory();

    public String toString() {
        return String.format("Title: %s" + System.lineSeparator() +
                        "Item ID: %d" + System.lineSeparator() +
                        "Description: %s" + System.lineSeparator() +
                        "Activity history: " + System.lineSeparator() +
                        String.join(System.lineSeparator(), getHistory()) + System.lineSeparator() +
                        "Comments: " + System.lineSeparator() +
                        String.join(System.lineSeparator(), getComments()) + System.lineSeparator(),
                this.getTitle(),
                this.getItemID(),
                this.getDescription());
    }

    @Override
    public abstract void addComment(String comment);

    public abstract void addActivity(String activity);

    @Override
    public abstract void setBoard(String board);

    private void setDescription(String description) {
        ValidationHelper.checkNameValidation(description, MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH, DESCRIPTION_ERROR_MESSAGE);
        this.description = description;
    }

    private void setTitle(String title) {
        ValidationHelper.checkNameValidation(title, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH, TITLE_ERROR_MESSAGE);
        this.title = title;
    }
}
