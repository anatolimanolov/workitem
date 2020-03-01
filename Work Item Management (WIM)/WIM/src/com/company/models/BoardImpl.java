package com.company.models;

import com.company.models.contracts.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardImpl implements Board {

    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 10;
    private static final String NAME_ERROR_MESSAGE =
            "Board name must be between 5 and 10 characters";


    private String name;
    private Map<String, Bug> bugs;
    private Map<String, Story> stories;
    private Map<String, Feedback> feedbacks;
    private List<String> activityHistory;
    private String team;

    public BoardImpl(String name, String team) {
        setName(name);
        setTeam(team);
        this.bugs = new HashMap<>();
        this.stories = new HashMap<>();
        this.feedbacks = new HashMap<>();
        this.activityHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    @Override
    public Map<String, Bug> getBugs() {
        return new HashMap<>(bugs);
    }

    @Override
    public Map<String, Story> getStories() {
        return new HashMap<>(stories);
    }

    @Override
    public Map<String, Feedback> getFeedbacks() {
        return new HashMap<>(feedbacks);
    }


    public List<String> getActivityHistory() {
        return activityHistory;
    }

    @Override
    public void addBug(String name, Bug bug) {
        this.bugs.put(name, bug);
    }

    @Override
    public void addStory(String name, Story story) {
        this.stories.put(name, story);
    }

    @Override
    public void addFeedback(String name, Feedback feedback) {
        this.feedbacks.put(name, feedback);
    }


    public void addActivity(String activity) {
        activityHistory.add(activity);
    }

    private void setName(String name) {
        ValidationHelper.checkNameValidation(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }

    private void setTeam (String team) {
        ValidationHelper.checkNameValidation(team, MIN_NAME_LENGTH, MAX_NAME_LENGTH, NAME_ERROR_MESSAGE);
        this.team = team;
    }

}
