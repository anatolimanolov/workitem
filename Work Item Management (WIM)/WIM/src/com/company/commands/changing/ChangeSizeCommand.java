package com.company.commands.changing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;
import com.company.models.MemberImpl;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.Size;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.company.commands.CommandsConstants.*;

public class ChangeSizeCommand implements Command {

    private static final String[] CHANGE_SIZE_LINE_INTERFACE = {
            "Enter the name of the item which size you want to change: ",
            "Enter the new size " + Arrays.toString(Size.values()) + ": ",
            "Enter the name of the team: ",
            "Enter the name of the board: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public ChangeSizeCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CHANGE_SIZE_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String itemToChangeSize = parameters.get(0);
        Size newSize = Size.customValueOf(parameters.get(1));
        String memberName = parameters.get(2);
        String team = parameters.get(3);
        String board = parameters.get(4);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemToChangeSize, wimRepository);

        return changeSizeStatus(itemToChangeSize, newSize, team, board);
    }

    private String changeSizeStatus(String itemTitle, Size item, String team, String board) {

        if (searchForStory(team, board, itemTitle)) {
            Story storyToChange = wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle);
            storyToChange.changeSize(item);
            addActivityToBoard(itemTitle, item, team, board, STORY_SIZE_ACTIVITY_MESSAGE);
            addActivityToMember(itemTitle, item, storyToChange.getAssignees(), STORY_SIZE_ACTIVITY_MESSAGE);
            storyToChange.addActivity(String.format(STORY_SIZE_STORY_ACTIVITY_MESSAGE,
                    formatter.format(date), itemTitle, item));
        } else {
            return String.format(ITEM_DOES_NOT_HAVE_SIZE_ERROR_MESSAGE, itemTitle);
        }

        return String.format(STORY_CHANGED_SIZE_SUCCESS_MESSAGE, itemTitle);
    }

    private boolean searchForStory(String team, String board, String itemToChangePriority) {
        final Map<String, Story> stories = wimRepository.getTeams().get(team).getBoards().get(board).getStories();
        return stories.keySet().stream()
                .anyMatch(story -> stories.containsKey(itemToChangePriority));
    }

    private void addActivityToBoard(String name, Size item, String team, String board, String storyChangeSizeActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(storyChangeSizeActivityMessage,
                        formatter.format(date), name, item));
    }

    private void addActivityToMember(String name, Size item, List<MemberImpl> member, String itemChangedPriorityActivityMessage) {
        for (MemberImpl assignee : member) {
            wimRepository.getPeople().get(assignee.getName())
                    .addActivity(String.format(itemChangedPriorityActivityMessage,
                            formatter.format(date), name, item));
        }

    }
}
