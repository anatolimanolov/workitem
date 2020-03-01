package com.company.commands.creation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.models.MemberImpl;

import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class CreatePersonCommand implements Command {

    private static final String[] CREATE_PERSON_LINE_INTERFACE = {"Enter name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final WimFactory wimFactory;
    private final WimRepository wimRepository;

    public CreatePersonCommand(WimFactory wimFactory, WimRepository wimRepository) {
        this.wimFactory = wimFactory;
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CREATE_PERSON_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String memberName = parameters.get(0);
        CommandsValidationHelper.checkIfMemberAlreadyExist(memberName, wimRepository);

        return createMember(memberName);
    }

    private String createMember(String memberName) {

        MemberImpl member = wimFactory.createPerson(memberName);
        wimRepository.addPerson(memberName, member);

        return String.format(MEMBER_CREATED_SUCCESS_MESSAGE, memberName);
    }
}
