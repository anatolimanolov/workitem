package com.company.commands.showing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.util.ArrayList;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class ShowPersonActivityCommand implements Command {

    private static final String[] SHOW_PERSON_ACTIVITY_LINE_INTERFACE = {"Enter member name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private WimRepository wimRepository;
    private List<String> person = new ArrayList<>();


    public ShowPersonActivityCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    //TODO

    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(SHOW_PERSON_ACTIVITY_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String personName = parameters.get(0);

        CommandsValidationHelper.checkIfMemberExist(personName, wimRepository);

        person.addAll(wimRepository.getPeople().get(personName).getActivityHistory());

        if (person.size() == 0) {
            throw new IllegalArgumentException(String.format(NO_ACTIVITY_ERROR_MESSAGE, personName));
        }

        person.add(0, String.format("%s\nActivity history in %s:", JOIN_DELIMITER, personName));
        person.add(1, JOIN_DELIMITER);
        person.add(person.size(), JOIN_DELIMITER);
        return String.join(System.lineSeparator(), person);
    }
}
