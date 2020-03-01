package com.company.commands.changing;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;
import com.company.models.contracts.Member;
import com.company.models.contracts.Feedback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.company.commands.CommandsConstants.*;

public class ChangeRatingCommand implements Command {

    private static final String[] CHANGE_RATING_LINE_INTERFACE = {
            "Enter the name of the item which rating you want to change: ",
            "Enter the new rating: ",
            "Enter member name: ",
            "Enter the name of the team: ",
            "Enter the name of the board: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 5;

    private WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public ChangeRatingCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CHANGE_RATING_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        String itemToChangeRating = parameters.get(0);
        int newRating = Integer.parseInt(parameters.get(1));
        String memberName = parameters.get(2);
        String team = parameters.get(3);
        String board = parameters.get(4);

        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemToChangeRating, wimRepository);
        CommandsValidationHelper.isInputNumeric(parameters.get(1));


        return changeRating(memberName, itemToChangeRating, newRating, team, board);
    }

    private String changeRating(String memberName, String itemTitle, int rating, String team, String board) {

        if (searchForFeedback(team, board, itemTitle)) {
            Feedback feedbackToChange = wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks().get(itemTitle);
            feedbackToChange.changeRating(rating);
            addActivityToBoard(itemTitle, rating, team, board, FEEDBACK_CHANGED_RATING_ACTIVITY_MESSAGE);
            addActivityToMember(itemTitle, rating, wimRepository.getPeople().get(memberName),
                    FEEDBACK_CHANGED_RATING_ACTIVITY_MESSAGE);
            feedbackToChange.addActivity(String.format(FEEDBACK_CHANGED_RATING_FEEDBACK_ACTIVITY_MESSAGE,
                    formatter.format(date), itemTitle, rating));
        }

        return String.format(FEEDBACK_CHANGED_RATING_SUCCESS_MESSAGE, itemTitle);
    }

    private boolean searchForFeedback(String team, String board, String itemToChangeRating) {
        final Map<String, Feedback> feedbacks = wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks();
        return feedbacks.keySet().stream()
                .anyMatch(feedback -> feedbacks.containsKey(itemToChangeRating));
    }


    private void addActivityToBoard(String name, Integer item, String team, String board, String feedbackChangeRatingActivityMessage) {
        wimRepository.getTeams().get(team).getBoards().get(board)
                .addActivity(String.format(feedbackChangeRatingActivityMessage,
                        formatter.format(date), name, item));
    }

    private void addActivityToMember(String name, Integer item, Member member, String itemChangedPriorityActivityMessage) {
        wimRepository.getPeople().get(member.getName())
                .addActivity(String.format(itemChangedPriorityActivityMessage,
                        formatter.format(date), name, item));
    }

}


