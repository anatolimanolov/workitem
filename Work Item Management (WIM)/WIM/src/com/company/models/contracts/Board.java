package com.company.models.contracts;

import java.util.List;
import java.util.Map;

public interface Board {
    String getName();

    String getTeam();

    Map<String, Bug> getBugs();

    Map<String, Story> getStories();

    Map<String, Feedback> getFeedbacks();

    List<String> getActivityHistory();

    void addBug (String name, Bug bug);

    void addStory (String name, Story story);

    void addFeedback (String name, Feedback feedback);

    void addActivity(String activity);
}
