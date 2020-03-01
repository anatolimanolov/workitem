package com.company.models;

import com.company.models.contracts.Member;
import com.company.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;

public class MemberImpl implements Member {


    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 15;
    private static final String NAME_ERROR_MESSAGE =
            "Name must be between 5 and 15 characters";

    private String name;
    private List<WorkItem> items;
    private List<String> activityHistory;

    public MemberImpl(String name) {
        setName(name);
        this.items = new ArrayList<>();
        this.activityHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<WorkItem> getItem() {
        return items;
    }

    public List<String> getActivityHistory() {
        return activityHistory;
    }

    public void addActivity(String activity) {
        activityHistory.add(activity);
    }

    public void addWorkItem(WorkItem item) {
        items.add(item);
    }

    private void setName(String name) {
        ValidationHelper.checkNameValidation(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }
}
