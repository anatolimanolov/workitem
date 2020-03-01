package com.company.commands;

import com.company.commands.contracts.Command;
import com.company.core.contracts.WimRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class AddCommentCommand implements Command {

    private static final String[] ADD_COMMENT_LINE_INTERFACE = {"Enter author name: ",
            "Enter team name: ",
            "Enter board name: ",
            "Enter item title to add comment to: ",
            "Enter comment: "};

    private WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public AddCommentCommand(WimRepository wimRepository) {
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(ADD_COMMENT_LINE_INTERFACE, parameters);
        }

        String memberName = parameters.get(0);
        String team = parameters.get(1);
        String board = parameters.get(2);
        String itemTitle = parameters.get(3);

        StringBuilder comment = new StringBuilder();
        for (int i = 4; i < parameters.size(); i++) {
            comment.append(parameters.get(i)).append(" ");
        }
        CommandsValidationHelper.checkIfMemberExist(memberName, wimRepository);
        CommandsValidationHelper.checkIfTeamExist(team, wimRepository);
        CommandsValidationHelper.checkIfItemExist(team, board, itemTitle, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(team, board, wimRepository);

        String commentFormat = String.format(" %s" + formatter.format(date) + System.lineSeparator() + " - %s",
                memberName, comment);

        if (wimRepository.getTeams().get(team).getBoards().get(board).getBugs().containsKey(itemTitle)) {
            wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemTitle)
                    .addComment(commentFormat);
        } else if (wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks().containsKey(itemTitle)) {
            wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks().get(itemTitle)
                    .addComment(commentFormat);
        } else if (wimRepository.getTeams().get(team).getBoards().get(board).getStories().containsKey(itemTitle)) {
            wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemTitle)
                    .addComment(commentFormat);
        }
        return String.format(COMMENT_SUCCESSFULLY_ADDED_MESSAGE, itemTitle);
    }
}
