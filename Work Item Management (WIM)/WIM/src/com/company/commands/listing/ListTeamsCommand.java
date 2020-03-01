package com.company.commands.listing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.commands.CommandsConstants.JOIN_DELIMITER;

public class ListTeamsCommand implements Command {

    private WimRepository wimRepository;
    private List<String> teams = new ArrayList<>();

    public ListTeamsCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    public String execute(List<String> parameters) {

        CommandsValidationHelper.checkIfTeamsExist(wimRepository);
        teams.addAll(wimRepository.getTeams().keySet());

        Collections.sort(teams);
        teams.add(0, "Registered teams:");
        teams.add(1, JOIN_DELIMITER);
        teams.add(teams.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), teams);
    }
}
