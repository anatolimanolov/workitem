package com.company.models.contracts;

import java.util.List;

public interface Member {
    String getName();

    List<WorkItem> getItem();

    List<String> getActivityHistory();

    void addActivity(String activity);

    void addWorkItem(WorkItem item);
}
