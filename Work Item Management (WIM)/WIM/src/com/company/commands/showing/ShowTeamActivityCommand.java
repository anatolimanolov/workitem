package com.company.commands.showing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.util.ArrayList;
import java.util.List;

import static com.company.commands.CommandsConstants.JOIN_DELIMITER;
import static com.company.commands.CommandsConstants.NO_ACTIVITY_ERROR_MESSAGE;

public class ShowTeamActivityCommand implements Command {

    private static final String[] SHOW_TEAM_ACTIVITY_LINE_INTERFACE = {"Enter team name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WimRepository wimRepository;

    private List<String> teams = new ArrayList<>();


    public ShowTeamActivityCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    //TODO

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(SHOW_TEAM_ACTIVITY_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String teamName = parameters.get(0);

        CommandsValidationHelper.checkIfTeamExist(teamName, wimRepository);

        teams.addAll(wimRepository.getTeams().get(teamName).getActivityHistory());

        if (teams.size() == 0) {
            throw new IllegalArgumentException(String.format(NO_ACTIVITY_ERROR_MESSAGE, teamName));
        }

        teams.add(0, String.format("%s\nActivity history in %s:", JOIN_DELIMITER, teamName));
        teams.add(1, JOIN_DELIMITER);
        teams.add(teams.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), teams);
    }
}
