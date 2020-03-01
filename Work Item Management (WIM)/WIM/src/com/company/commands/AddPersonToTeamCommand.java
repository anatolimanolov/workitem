package com.company.commands;

import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class AddPersonToTeamCommand implements Command {

    private static final String[] ADD_PERSON_TO_TEAM_LINE_INTERFACE = {"Enter member name: ", "Enter team name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private static final String ALREADY_A_MEMBER_ERROR_MESSAGE = "%s is already member in %s.";

    private WimRepository wimRepository;

    public AddPersonToTeamCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(ADD_PERSON_TO_TEAM_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String memberName = parameters.get(0);
        String teamName = parameters.get(1);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(teamName, wimRepository);

        if (wimRepository.getTeams().get(teamName).getMembers().containsKey(memberName)) {
            return String.format(ALREADY_A_MEMBER_ERROR_MESSAGE, memberName, teamName);
        }

        wimRepository.getTeams().get(teamName).
                addMember(wimRepository.getPeople().get(memberName));

        return String.format(PERSON_ADDED_TO_TEAM_SUCCESS_MESSAGE, memberName, teamName);
    }
}
