package com.company.commands.creation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.contracts.Feedback;
import com.company.models.workitems.enums.FeedbackStatus;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class CreateFeedbackCommand implements Command {

    private static final String[] CREATE_FEEDBACK_LINE_INTERFACE = {"Enter title: ",
            "Enter description: ",
            "Enter rating: ",
            "Enter status " + Arrays.toString(FeedbackStatus.values()) + ": ",
            "Enter board name: ",
            "Enter team name: ",
            "Enter author: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;

    private final WimFactory wimFactory;
    private final WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public CreateFeedbackCommand(WimFactory wimFactory, WimRepository wimRepository) {
        this.wimFactory = wimFactory;
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CREATE_FEEDBACK_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        long itemID = wimRepository.getNextItemId();
        String title = parameters.get(0);
        String description = parameters.get(1);
        CommandsValidationHelper.isInputNumeric(parameters.get(2));
        int rating = Integer.parseInt(parameters.get(2));
        FeedbackStatus status = FeedbackStatus.customValueOf(parameters.get(3));
        String boardToAdd = parameters.get(4);
        String teamToAdd = parameters.get(5);
        String author1 = parameters.get(6);
        MemberImpl author = wimRepository.getPeople().get(author1);

        CommandsValidationHelper.checkIfTeamExist(teamToAdd, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(teamToAdd, boardToAdd, wimRepository);
        CommandsValidationHelper.checkIfMemberExist(author1, wimRepository);
        CommandsValidationHelper.checkIfItemAlreadyExistInBoard(title, teamToAdd, boardToAdd, wimRepository);

        return createFeedback(itemID, title, description, rating, status, teamToAdd, boardToAdd, author);
    }

    private String createFeedback(long itemID, String name, String description,
                                  int rating, FeedbackStatus status, String teamToAdd, String boardToAdd, MemberImpl author) {

        Feedback feedback = wimFactory.createFeedback(itemID, name, description, rating, status, author);
        wimRepository.getTeams().get(teamToAdd).getBoards().get(boardToAdd).addFeedback(name, feedback);
        BoardImpl board = wimRepository.getTeams().get(teamToAdd).getBoards().get(boardToAdd);
        board.addFeedback(name, feedback);
        feedback.setBoard(boardToAdd);
        board.addActivity(String.format(FEEDBACK_ADD_TO_BOARD_ACTIVITY_MESSAGE, formatter.format(date), name));
        feedback.addActivity(String.format(FEEDBACK_ADD_TO_BOARD_FEEDBACK_ACTIVITY_MESSAGE,
                formatter.format(date), name, boardToAdd));

        return String.format(FEEDBACK_CREATED_SUCCESS_MESSAGE, itemID, name, boardToAdd);
    }
}