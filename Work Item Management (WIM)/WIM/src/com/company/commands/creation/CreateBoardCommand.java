package com.company.commands.creation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class CreateBoardCommand implements Command {

    private static final String[] CREATE_BOARD_LINE_INTERFACE = {"Enter board name: ",
            "Enter team name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    private final WimFactory wimFactory;
    private final WimRepository wimRepository;

    public CreateBoardCommand(WimFactory wimFactory, WimRepository wimRepository) {
        this.wimFactory = wimFactory;
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CREATE_BOARD_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String boardName = parameters.get(0);
        String team = parameters.get(1);

        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExistInTeam(boardName, team, wimRepository);

        return createBoard(boardName, team);
    }

    private String createBoard(String boardName, String team) {

        BoardImpl board = wimFactory.createBoard(boardName, team);
//        wimRepository.addBoard(boardName, board);
        wimRepository.getTeams().get(team).addBoard(board);
        addActivityToTeam(boardName, team, BOARD_CREATED_IN_TEAM_ACTIVITY);

        return String.format(BOARD_CREATED_SUCCESS_MESSAGE, boardName, team);
    }

    private void addActivityToTeam(String board, String team, String boardCreatedActivityMessage) {
        wimRepository.getTeams().get(team)
                .addActivity(String.format(boardCreatedActivityMessage,
                        formatter.format(date), board, team));
    }
}
