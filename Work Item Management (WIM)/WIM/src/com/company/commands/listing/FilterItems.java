package com.company.commands.listing;

import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.FeedbackStatus;
import com.company.models.workitems.enums.StoryStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FilterItems {

    private static final String INVALID_INPUT_PARAMETER_ERROR_MESSAGE = "Invalid input parameter: '%s'.";

    static void filterItemsByOneParameter(List<WorkItem> items, WimRepository wimRepository, String parameter) {

        List<Bug> bugs = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getBugs().forEach((c, d) -> bugs.add(d))));

        List<Feedback> feedbacks = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getFeedbacks().forEach((c, d) -> feedbacks.add(d))));

        List<Story> stories = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getStories().forEach((c, d) -> stories.add(d))));


        if (parameter.equalsIgnoreCase("all")) {
            items.addAll(bugs);
            items.addAll(feedbacks);
            items.addAll(stories);
        } else if (parameter.equalsIgnoreCase("bug")) {
            items.addAll(bugs);
        } else if (parameter.equalsIgnoreCase("feedback")) {
            items.addAll(feedbacks);
        } else if (parameter.equalsIgnoreCase("story")) {
            items.addAll(stories);
        } else if (wimRepository.getPeople().containsKey(parameter)) { //assignee
            for (Bug bug : bugs) {
                if (bug.getAssignees().contains(wimRepository.getPeople().get(parameter))) {
                    items.add(bug);
                }
            }
            for (Story story : stories) {
                if (story.getAssignees().contains(wimRepository.getPeople().get(parameter))) {
                    items.add(story);
                }
            }
            if (items.size() == 0) {
                throw new IllegalArgumentException(String.format("No items are assigned to %s", parameter));
            }
        } else {
            throw new IllegalArgumentException(String.format(INVALID_INPUT_PARAMETER_ERROR_MESSAGE, parameter));
        }

    }

    static void filterItemsByTwoParameters(List<WorkItem> items, WimRepository wimRepository,
                                           String firstParameter, String secondParameter) {

        List<Bug> bugs = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getBugs().forEach((c, d) -> bugs.add(d))));

        List<Feedback> feedbacks = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getFeedbacks().forEach((c, d) -> feedbacks.add(d))));

        List<Story> stories = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getStories().forEach((c, d) -> stories.add(d))));

        String enumConstant = belongsToEnum(secondParameter);

        if (firstParameter.equals("bug") && enumConstant.equals("bug")) {
            filterItemsByOneParameter(items, wimRepository, firstParameter);
            for (Bug bug : bugs) {
                if (!bug.getStatus().equals(BugStatus.valueOf(secondParameter.toUpperCase()))) {
                    items.remove(bug);
                }
            }
        } else if (firstParameter.equals("feedback") && enumConstant.equals("feedback")) {
            filterItemsByOneParameter(items, wimRepository, firstParameter);
            for (Feedback feedback : feedbacks) {
                if (!feedback.getStatus().equals(FeedbackStatus.customValueOf(secondParameter))) {
                    items.remove(feedback);
                }
            }
        } else if (firstParameter.equals("story") && enumConstant.equals("story")) {
            filterItemsByOneParameter(items, wimRepository, firstParameter);
            for (Story story : stories) {
                if (!story.getStatus().equals(StoryStatus.customValueOf(secondParameter))) {
                    items.remove(story);
                }
            }
        } else if (wimRepository.getPeople().containsKey(firstParameter) && enumConstant.equals("bug")) {
            filterItemsByOneParameter(items, wimRepository, firstParameter);
            for (Bug bug : bugs) {
                if (!bug.getStatus().equals(BugStatus.customValueOf(secondParameter))) {
                    items.remove(bug);
                }
            }
        }
    }

    private static String belongsToEnum(String filter) {

        for (BugStatus bugStatus : BugStatus.values()) {
            if (bugStatus.toString().equals(filter.toUpperCase())) {
                return "bug";
            }
        }

        for (FeedbackStatus feedbackStatus : FeedbackStatus.values()) {
            if (feedbackStatus.toString().equals(filter.toUpperCase())) {
                return "feedback";
            }
        }

        for (StoryStatus storyStatus : StoryStatus.values()) {
            if (storyStatus.toString().equals(filter.toUpperCase())) {
                return "story";
            }
        }
        throw new IllegalArgumentException(String.format(INVALID_INPUT_PARAMETER_ERROR_MESSAGE, filter));
    }
}