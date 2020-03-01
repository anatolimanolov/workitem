package com.company.commands.listing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import static com.company.commands.CommandsConstants.JOIN_DELIMITER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPeopleCommand implements Command {

    private WimRepository wimRepository;
    private List<String> members = new ArrayList<>();

    public ListPeopleCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    public String execute(List<String> parameters) {

        CommandsValidationHelper.checkIfMembersExist(wimRepository);
        members.addAll(wimRepository.getPeople().keySet());

        Collections.sort(members);
        members.add(0, "Registered members:");
        members.add(1, JOIN_DELIMITER);
        members.add(members.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), members);
    }
}
