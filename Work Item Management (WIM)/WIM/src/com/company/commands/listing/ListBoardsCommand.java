package com.company.commands.listing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.Reader;
import com.company.core.contracts.WimRepository;
import com.company.core.contracts.Writer;
import com.company.core.providers.ConsoleReader;
import com.company.core.providers.ConsoleWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class ListBoardsCommand implements Command {

    private static final String[] LIST_BOARDS_LINE_INTERFACE = {"Enter team name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WimRepository wimRepository;
    private List<String> boards = new ArrayList<>();

    public ListBoardsCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(LIST_BOARDS_LINE_INTERFACE, parameters);
        }

        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());
        String teamName = parameters.get(0);
        CommandsValidationHelper.checkIfTeamExist(teamName, wimRepository);
        CommandsValidationHelper.checkIfTeamHasBoards(teamName, wimRepository);

        boards.addAll(wimRepository.getTeams().get(teamName).getBoards().keySet());

        Collections.sort(boards);
        boards.add(0, String.format("Registered boards in %s:", teamName));
        boards.add(1, JOIN_DELIMITER);
        boards.add(boards.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), boards);
    }
}
