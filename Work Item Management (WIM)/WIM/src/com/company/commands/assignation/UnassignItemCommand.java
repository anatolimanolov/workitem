package com.company.commands.assignation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class UnassignItemCommand implements Command {

    private static final String[] UNASSIGN_ITEM_LINE_INTERFACE = {"Enter name of the member you want to unassign the item from: ",
            "Enter the name of the team: ",
            "Enter the name of the board: ",
            "Enter the name of the item you want to unassign: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    private WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public UnassignItemCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(UNASSIGN_ITEM_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String memberName = parameters.get(0);
        String team = parameters.get(1);
        String board = parameters.get(2);
        String itemTitle = parameters.get(3);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemTitle, wimRepository);


        String activityFormat = formatter.format(date) +
                String.format(UNASSIGNED_MEMBER_ACTIVITY, itemTitle, memberName);

        if (wimRepository.getTeams().get(team).getBoards().get(board).getBugs().containsKey(itemTitle)) {
            CommandsValidationHelper.checkIfNotAssigned(memberName, team, board, itemTitle, wimRepository);
            wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle)
                    .unassignMember(wimRepository.getPeople().get(memberName));
            wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle)
                    .addActivity(activityFormat);
            wimRepository.getPeople().get(memberName).addActivity(activityFormat);
        } else if (wimRepository.getTeams().get(team).getBoards().get(board).getStories().containsKey(itemTitle)) {
            CommandsValidationHelper.checkIfNotAssigned(memberName, team, board, itemTitle, wimRepository);
            wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle)
                    .unassignMember(wimRepository.getPeople().get(memberName));
            wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle)
                    .addActivity(activityFormat);
            wimRepository.getPeople().get(memberName).addActivity(activityFormat);
        } else {
            return String.format(ITEM_NOT_ASSIGNABLE_ERROR_MESSAGE, itemTitle);
        }
        return String.format(SUCCESSFULLY_UNASSIGNED_MESSAGE, itemTitle, memberName);
    }
}
