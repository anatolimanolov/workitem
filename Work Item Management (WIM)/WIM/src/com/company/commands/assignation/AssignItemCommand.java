package com.company.commands.assignation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class AssignItemCommand implements Command {

    private static final String[] ASSIGN_ITEM_LINE_INTERFACE = {
            "Enter name of the member you want to assign the item to: ",
            "Enter the name of the team: ",
            "Enter the name of the board: ",
            "Enter the name of the item you want to assign: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 4;

    private WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public AssignItemCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(ASSIGN_ITEM_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String memberName = parameters.get(0);
        String team = parameters.get(1);
        String board = parameters.get(2);
        String itemTitle = parameters.get(3);

        CommandsValidationHelper.checkIfItemExist(team, board, itemTitle, wimRepository);
        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfMemberIsInTeam(team, memberName, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemTitle, wimRepository);

        String activityFormat = formatter.format(date) +
                String.format(ASSIGNED_MEMBER_ACTIVITY, itemTitle, memberName);

        if (wimRepository.getTeams().get(team).getBoards().get(board).getBugs().containsKey(itemTitle)) {
            CommandsValidationHelper.checkIfAlreadyAssigned(memberName, team, board, itemTitle, wimRepository);
            wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle)
                    .assignMember(wimRepository.getPeople().get(memberName));
            wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle)
                    .addActivity(activityFormat);
            wimRepository.getPeople().get(memberName).addActivity(activityFormat);
        } else if (wimRepository.getTeams().get(team).getBoards().get(board).getStories().containsKey(itemTitle)) {
            CommandsValidationHelper.checkIfAlreadyAssigned(memberName, team, board, itemTitle, wimRepository);
            wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle)
                    .assignMember(wimRepository.getPeople().get(memberName));
            wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle)
                    .addActivity(activityFormat);
            wimRepository.getPeople().get(memberName).addActivity(activityFormat);
        } else {
            throw new IllegalArgumentException(String.format(ITEM_NOT_ASSIGNABLE_ERROR_MESSAGE, itemTitle));
        }
        return String.format(SUCCESSFULLY_ASSIGNED_MESSAGE, itemTitle, memberName);
    }
}
