package com.company.commands.creation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.contracts.Bug;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.Priority;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class CreateBugCommand implements Command {

    private static final String[] CREATE_BUG_LINE_INTERFACE = {"Enter bug title: ",
            "Enter bug description: ",
            "Enter steps to reproduce [enter end for termination command]: " + System.lineSeparator(),
            "Enter priority " + Arrays.toString(Priority.values()) + ": ",
            "Enter severity" + Arrays.toString(BugSeverity.values()) + ": ",
            "Enter status" + Arrays.toString(BugStatus.values()) + ": ",
            "Enter team name: ",
            "Enter board name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;

    private final WimFactory wimFactory;
    private final WimRepository wimRepository;

    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public CreateBugCommand(WimFactory wimFactory, WimRepository wimRepository) {
        this.wimFactory = wimFactory;
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        List<String> stepsToReproduce = new ArrayList<>();

        if (parameters.size() == 0) {
            LineInterface.getBoardParametersFromLineInterface(CREATE_BUG_LINE_INTERFACE, parameters, stepsToReproduce);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        long itemID = wimRepository.getNextItemId();
        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = Priority.customValueOf(parameters.get(2));
        BugSeverity severity = BugSeverity.customValueOf(parameters.get(3));
        BugStatus status = BugStatus.customValueOf(parameters.get(4));
        String teamToAdd = parameters.get(5);
        String boardToAdd = parameters.get(6);

        CommandsValidationHelper.checkIfTeamExist(teamToAdd, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(teamToAdd, boardToAdd, wimRepository);
        CommandsValidationHelper.checkIfItemAlreadyExistInBoard(title, teamToAdd, boardToAdd, wimRepository);

        return createBug(itemID, title, description, priority, severity, status, teamToAdd, boardToAdd, stepsToReproduce);
    }


    private String createBug(long itemID, String title, String description, Priority priority,
                             BugSeverity severity, BugStatus status, String teamToAdd, String boardToAdd, List<String> stepsToReproduce) {

        Bug bug = wimFactory.createBug(itemID, title, description, priority, severity, status, stepsToReproduce);
        wimRepository.getTeams().get(teamToAdd).getBoards().get(boardToAdd).addBug(title, bug);
        BoardImpl board = wimRepository.getTeams().get(teamToAdd).getBoards().get(boardToAdd);
        board.addBug(title, bug);
        bug.setBoard(boardToAdd);
        board.addActivity(String.format(BUG_ADD_TO_BOARD_ACTIVITY_MESSAGE, formatter.format(date), title));
        bug.addActivity(String.format(BUG_ADD_TO_BOARD_BUG_ACTIVITY_MESSAGE, formatter.format(date), title, boardToAdd));

        return String.format(BUG_CREATED_SUCCESS_MESSAGE, itemID, title, boardToAdd);
    }
}