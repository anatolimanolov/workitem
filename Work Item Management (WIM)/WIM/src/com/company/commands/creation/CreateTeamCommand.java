package com.company.commands.creation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.models.TeamImpl;

import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class CreateTeamCommand implements Command {

    private static final String[] CREATE_TEAM_LINE_INTERFACE = {"Enter team name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final WimFactory wimFactory;
    private final WimRepository wimRepository;

    public CreateTeamCommand(WimFactory wimFactory, WimRepository wimRepository) {
        this.wimFactory = wimFactory;
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CREATE_TEAM_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String teamName = parameters.get(0);
        CommandsValidationHelper.checkIfTeamAlreadyExist(teamName, wimRepository);

        return createTeam(teamName);
    }

    private String createTeam(String teamName) {
        TeamImpl team = wimFactory.createTeam(teamName);
        wimRepository.addTeam(teamName, team);

        return String.format(TEAM_CREATED_SUCCESS_MESSAGE, teamName);
    }
}
