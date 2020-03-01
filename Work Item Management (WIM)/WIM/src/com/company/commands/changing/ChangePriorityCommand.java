package com.company.commands.changing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;
import com.company.models.MemberImpl;
import com.company.models.contracts.Board;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.Priority;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

import static com.company.commands.CommandsConstants.*;

public class ChangePriorityCommand implements Command {

    private static final String[] CHANGE_PRIORITY_LINE_INTERFACE = {
            "Enter member name: ",
            "Enter the title of the item which priority you want to change: ",
            "Enter the new priority " + Arrays.toString(Priority.values()) + ": ",
            "Enter the name of the team:",
            "Enter the name of the board: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    private WimRepository wimRepository;


    public ChangePriorityCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CHANGE_PRIORITY_LINE_INTERFACE, parameters);
        }

        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String memberName = parameters.get(0);
        String itemToChangePriority = parameters.get(1);
        Priority newPriority = Priority.customValueOf(parameters.get(2));
        String team = parameters.get(3);
        String board = parameters.get(4);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemToChangePriority, wimRepository);
        CommandsValidationHelper.checkIfItemAssignedToMember(memberName, team, board, itemToChangePriority, wimRepository);

        return changePriorityStatus(itemToChangePriority, newPriority, team, board);
    }


    private String changePriorityStatus(String itemTitle, Priority item, String team, String board) {

        if (searchForBug(team, board, itemTitle)) {
            Bug bugToChange = wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle);
            bugToChange.changePriority(item);
            addActivityToBoard(itemTitle, item, team, board, BUG_CHANGED_PRIORITY_ACTIVITY_MESSAGE);
            addActivityToMember(itemTitle, item, bugToChange.getAssignees(), BUG_CHANGED_PRIORITY_ACTIVITY_MESSAGE);
            bugToChange.addActivity(String.format(BUG_CHANGED_PRIORITY_BUG_ACTIVITY_MESSAGE,
                    formatter.format(date), itemTitle, item));

        } else if (searchForStory(team, board, itemTitle)) {
            Story storyToChange = wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle);
            storyToChange.changePriority(item);
            addActivityToBoard(itemTitle, item, team, board, STORY_CHANGED_PRIORITY_ACTIVITY_MESSAGE);
            addActivityToMember(itemTitle, item, storyToChange.getAssignees(), STORY_CHANGED_PRIORITY_ACTIVITY_MESSAGE);
            storyToChange.addActivity(String.format(STORY_CHANGED_PRIORITY_STORY_ACTIVITY_MESSAGE,
                    formatter.format(date), itemTitle, item));
        } else {
            return String.format(ITEM_DOES_NOT_HAVE_PRIORITY_ERROR_MESSAGE, itemTitle);
        }
        return String.format(ITEM_CHANGED_PRIORITY_SUCCESS_MESSAGE, itemTitle);
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

    private void addActivityToBoard(String name, Priority item, String team, String board, String bugChangedPriorityActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(bugChangedPriorityActivityMessage,
                        formatter.format(date), name, item));
    }

    private void addActivityToMember(String name, Priority item, List<MemberImpl> member, String itemChangedPriorityActivityMessage) {
        for (MemberImpl assignee : member) {
            wimRepository.getPeople().get(assignee.getName())
                    .addActivity(String.format(itemChangedPriorityActivityMessage,
                            formatter.format(date), name, item));
        }

    }
}
