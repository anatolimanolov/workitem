package com.company.commands.changing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;
import com.company.models.MemberImpl;
import com.company.models.contracts.Bug;
import com.company.models.workitems.enums.BugSeverity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.company.commands.CommandsConstants.*;

public class ChangeSeverityCommand implements Command {
    private static final String[] CHANGE_SEVERITY_LINE_INTERFACE = {
            "Enter the name of the item which severity you want to change: ",
            "Enter the new severity " + Arrays.toString(BugSeverity.values()) + ": ",
            "Enter the name of the team: ",
            "Enter the name of the board: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    private WimRepository wimRepository;

    public ChangeSeverityCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CHANGE_SEVERITY_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String itemToChangeSeverity = parameters.get(0);
        BugSeverity newSeverity = BugSeverity.customValueOf(parameters.get(1));
        String memberName = parameters.get(2);
        String team = parameters.get(3);
        String board = parameters.get(4);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemToChangeSeverity, wimRepository);

        return changeSeverityStatus(itemToChangeSeverity, newSeverity, team, board);
    }

    private String changeSeverityStatus(String itemTitle, BugSeverity item, String team, String board) {

        if (searchForBug(team, board, itemTitle)) {
            Bug bugToChange = wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle);
            bugToChange.changeSeverity(item);
            addActivityToBoard(itemTitle, item, team, board, BUG_CHANGED_SEVERITY_ACTIVITY_MESSAGE);
            addActivityToMember(itemTitle, item, bugToChange.getAssignees(), BUG_CHANGED_SEVERITY_ACTIVITY_MESSAGE);
            bugToChange.addActivity(String.format(BUG_CHANGED_SEVERITY_BUG_ACTIVITY_MESSAGE,
                    formatter.format(date), itemTitle, item));
        } else {
            return String.format(ITEM_DOES_NOT_HAVE_SEVERITY_ERROR_MESSAGE, itemTitle);
        }
        return String.format(BUG_CHANGED_SEVERITY_SUCCESS_MESSAGE, itemTitle);
    }

    private boolean searchForBug(String team, String board, String itemToChangePriority) {
        final Map<String, Bug> bugs = wimRepository.getTeams().get(team).getBoards().get(board).getBugs();
        return bugs.keySet().stream()
                .anyMatch(bug -> bugs.containsKey(itemToChangePriority));
    }

    private void addActivityToBoard(String name, BugSeverity item, String team, String board, String bugChangeSeverityActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(bugChangeSeverityActivityMessage,
                        formatter.format(date), name, item));
    }

    private void addActivityToMember(String name, BugSeverity item, List<MemberImpl> member, String itemChangedPriorityActivityMessage) {
        for (MemberImpl assignee : member) {
            wimRepository.getPeople().get(assignee.getName())
                    .addActivity(String.format(itemChangedPriorityActivityMessage,
                            formatter.format(date), name, item));
        }

    }

}
