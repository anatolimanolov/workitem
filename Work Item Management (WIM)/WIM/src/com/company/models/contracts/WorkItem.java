package com.company.models.contracts;

import java.util.List;

public interface WorkItem {

    long getItemID();

    String getTitle();

    String getDescription();

    void setBoard(String board);

    List getComments();

    List<String> getHistory();

    String toString();

    void addComment(String comment);

    void addActivity(String activity);
}
