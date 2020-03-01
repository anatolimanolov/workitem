package com.company.commands.showing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.util.ArrayList;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class ShowBoardActivityCommand implements Command {

    private static final String[] SHOW_BOARD_ACTIVITY_LINE_INTERFACE = {"Enter team name: ",
            "Enter board name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private WimRepository wimRepository;
    private List<String> boards = new ArrayList<>();

    public ShowBoardActivityCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    //TODO

    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(SHOW_BOARD_ACTIVITY_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String teamName = parameters.get(0);
        String boardName = parameters.get(1);

        CommandsValidationHelper.checkIfTeamExist(teamName, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(teamName, boardName, wimRepository);

        boards.addAll(wimRepository.getTeams().get(teamName).getBoards().get(boardName).getActivityHistory());

        if (boards.size() == 0) {
            throw new IllegalArgumentException(String.format(NO_ACTIVITY_ERROR_MESSAGE, boardName));
        }

        boards.add(0, String.format("%s\nActivity history in %s:", JOIN_DELIMITER, boardName));
        boards.add(1, JOIN_DELIMITER);
        boards.add(boards.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), boards);
    }
}
