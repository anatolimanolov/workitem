package com.company.commands.listing;

import com.company.core.contracts.WimRepository;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.BugImpl;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortItems {

    public static void sortItems(List<WorkItem> items, WimRepository wimRepository, String parameter) {

        List<Bug> bugs = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getBugs().forEach((c, d) -> bugs.add(d))));

        List<Feedback> feedbacks = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getFeedbacks().forEach((c, d) -> feedbacks.add(d))));

        List<Story> stories = new ArrayList<>();
        wimRepository.getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getStories().forEach((c, d) -> stories.add(d))));
        List<WorkItem> initialItemsList = new ArrayList<>(items);
        items.removeAll(initialItemsList);

        if (parameter.equalsIgnoreCase("title")) {
            List<Bug> sortedBugs = bugs.stream()
                    .sorted(Comparator.comparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedBugs);

            List<Feedback> sortedFeedbacks = feedbacks.stream()
                    .sorted(Comparator.comparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedFeedbacks);

            List<Story> sortedStory = stories.stream()
                    .sorted(Comparator.comparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedStory);
        } else if (parameter.equalsIgnoreCase("priority")) {
            List<Bug> sortedBugs = bugs.stream()
                    .sorted(Comparator.comparing(Bug::getPriority).thenComparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedBugs);

            List<Story> sortedStory = stories.stream()
                    .sorted(Comparator.comparing(Story::getPriority).thenComparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedStory);
        } else if(parameter.equalsIgnoreCase("severity")) {
            List<Bug> sortedBugs = bugs.stream()
                    .sorted(Comparator.comparing(Bug::getSeverity).thenComparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedBugs);
        } else if(parameter.equalsIgnoreCase("size")) {
            List<Story> sortedStory = stories.stream()
                    .sorted(Comparator.comparing(Story::getSize).thenComparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedStory);
        } else if(parameter.equalsIgnoreCase("rating")) {
            List<Feedback> sortedFeedbacks = feedbacks.stream()
                    .sorted(Comparator.comparing(Feedback::getRating).thenComparing(WorkItem::getTitle))
                    .collect(Collectors.toList());
            items.addAll(sortedFeedbacks);
        } else {
            throw new IllegalArgumentException(String.format("Invalid input parameter: %s", parameter));
        }
        items.retainAll(initialItemsList);
    }

}