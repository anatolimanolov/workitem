package com.company.commands.listing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class ListMembersCommand implements Command {

    private static final String[] LIST_MEMBERS_LINE_INTERFACE = {"Enter team name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WimRepository wimRepository;
    private List<String> members = new ArrayList<>();

    public ListMembersCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(LIST_MEMBERS_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String teamName = parameters.get(0);
        CommandsValidationHelper.checkIfTeamExist(teamName, wimRepository);
        CommandsValidationHelper.checkIfTeamHasNoMembers(teamName, wimRepository);

        members.addAll(wimRepository.getTeams().get(teamName).getMembers().keySet());

        Collections.sort(members);
        members.add(0, String.format("Registered members in %s:", teamName));
        members.add(1, JOIN_DELIMITER);
        members.add(members.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), members);
    }
}
