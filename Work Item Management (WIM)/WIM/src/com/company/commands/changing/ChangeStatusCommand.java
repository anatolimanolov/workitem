package com.company.commands.changing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;
import com.company.models.MemberImpl;
import com.company.models.contracts.Member;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.FeedbackStatus;
import com.company.models.workitems.enums.StoryStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.company.commands.CommandsConstants.*;

public class ChangeStatusCommand implements Command {

    private static final String[] CHANGE_STATUS_LINE_INTERFACE = {
            "Enter the name of the item which status you want to change: ",
            "Enter the new status: ",
            "Enter the member name: ",
            "Enter the name of the team: ",
            "Enter the name of the board: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public ChangeStatusCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CHANGE_STATUS_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String itemToChangeStatus = parameters.get(0);
        String newStatus = parameters.get(1);
        String memberName = parameters.get(2);
        String team = parameters.get(3);
        String board = parameters.get(4);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemToChangeStatus, wimRepository);

        if (searchForBug(team, board, itemToChangeStatus)) {
            BugStatus newBugStatus = BugStatus.customValueOf(newStatus);
            return changeStatusBug(itemToChangeStatus, newBugStatus, team, board);
        } else if (searchForStory(team, board, itemToChangeStatus)) {
            StoryStatus newStoryStatus = StoryStatus.customValueOf(newStatus);
            return changeStatusStory(itemToChangeStatus, newStoryStatus, team, board);
        } else {
            FeedbackStatus newFeedbackStatus = FeedbackStatus.customValueOf(newStatus);
            return changeStatusFeedback(itemToChangeStatus, newFeedbackStatus, team, board);
        }
    }

    private String changeStatusBug(String name, BugStatus item, String team, String board) {

        if (searchForBug(team, board, name)) {
            Bug bugToChange = wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(name);
            bugToChange.changeStatus(item);
            addBugActivityToBoard(name, item, team, board, BUG_CHANGED_STATUS_ACTIVITY_MESSAGE);
            addBugActivityToMember(name, item, bugToChange.getAssignees(), BUG_CHANGED_STATUS_ACTIVITY_MESSAGE);
            bugToChange.addActivity(String.format(BUG_CHANGED_STATUS_BUG_ACTIVITY_MESSAGE,
                    formatter.format(date), name, item));
        }
        return String.format(BUG_CHANGED_STATUS_SUCCESS_MESSAGE, name);
    }

    private String changeStatusStory(String name, StoryStatus item, String team, String board) {

        if (searchForStory(team, board, name)) {
            Story storyToChange = wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(name);
            storyToChange.changeStatus(item);
            addStoryActivityToBoard(name, item, team, board, STORY_CHANGED_STATUS_ACTIVITY_MESSAGE);
            addStoryActivityToMember(name, item, storyToChange.getAssignees(), STORY_CHANGED_STATUS_ACTIVITY_MESSAGE);
            storyToChange.addActivity(String.format(STORY_CHANGED_STATUS_STORY_ACTIVITY_MESSAGE,
                    formatter.format(date), name, item));
        }
        return String.format(STORY_CHANGED_STATUS_SUCCESS_MESSAGE, name);
    }

    private String changeStatusFeedback(String name, FeedbackStatus item, String team, String board) {

        if (searchForFeedback(team, board, name)) {
            Feedback feedbackToChange = wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks().get(name);
            feedbackToChange.changeStatus(item);
            addFeedbackActivityToBoard(name, item, team, board, FEEDBACK_CHANGED_STATUS_ACTIVITY_MESSAGE);
            addFeedbackActivityToMember(name, item, feedbackToChange.getAuthor(), FEEDBACK_CHANGED_STATUS_ACTIVITY_MESSAGE);
            feedbackToChange.addActivity(String.format(FEEDBACK_CHANGED_STATUS_FEEDBACK_ACTIVITY_MESSAGE,
                    formatter.format(date), name, item));
        }
        return String.format(FEEDBACK_CHANGED_STATUS_SUCCESS_MESSAGE, name);
    }


    private boolean searchForBug(String team, String board, String itemToChangePriority) {
        final Map<String, Bug> bugs = wimRepository.getTeams().get(team).getBoards().get(board).getBugs();
        return bugs.keySet().stream()
                .anyMatch(bug -> bugs.containsKey(itemToChangePriority));
    }

    private boolean searchForStory(String team, String board, String itemToChangePriority) {
        final Map<String, Story> stories = wimRepository.getTeams().get(team).getBoards().get(board).getStories();
        return stories.keySet().stream()
                .anyMatch(story -> stories.containsKey(itemToChangePriority));
    }

    private boolean searchForFeedback(String team, String board, String itemToChangeRating) {
        final Map<String, Feedback> feedbacks = wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks();
        return feedbacks.keySet().stream()
                .anyMatch(feedback -> feedbacks.containsKey(itemToChangeRating));
    }

    private void addBugActivityToBoard(String name, BugStatus item, String team, String board, String itemChangedStatusActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(itemChangedStatusActivityMessage,
                        formatter.format(date), name, item));
    }

    private void addStoryActivityToBoard(String name, StoryStatus item, String team, String board, String itemChangedStatusActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(itemChangedStatusActivityMessage,
                        formatter.format(date), name, item));
    }

    private void addFeedbackActivityToBoard(String name, FeedbackStatus item, String team, String board, String itemChangedStatusActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(itemChangedStatusActivityMessage,
                        formatter.format(date), name, item));
    }


    private void addBugActivityToMember(String name, BugStatus item, List<MemberImpl> member, String itemChangedPriorityActivityMessage) {
        for (MemberImpl assignee : member) {
            wimRepository.getPeople().get(assignee.getName())
                    .addActivity(String.format(itemChangedPriorityActivityMessage,
                            formatter.format(date), name, item));
        }

    }

    private void addStoryActivityToMember(String name, StoryStatus item, List<MemberImpl> member, String itemChangedPriorityActivityMessage) {
        for (MemberImpl assignee : member) {
            wimRepository.getPeople().get(assignee.getName())
                    .addActivity(String.format(itemChangedPriorityActivityMessage,
                            formatter.format(date), name, item));
        }

    }

    private void addFeedbackActivityToMember(String name, FeedbackStatus item, Member member, String itemChangedPriorityActivityMessage) {
        wimRepository.getPeople().get(member.getName())
                .addActivity(String.format(itemChangedPriorityActivityMessage,
                        formatter.format(date), name, item));
    }

}
