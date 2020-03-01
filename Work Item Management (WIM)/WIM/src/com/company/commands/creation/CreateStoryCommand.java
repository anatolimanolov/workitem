package com.company.commands.creation;

import com.company.commands.CommandsValidationHelper;
import com.company.commands.LineInterface;
import com.company.commands.contracts.Command;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.workitems.WorkItemsBaseImpl;
import com.company.models.contracts.Story;
import com.company.models.workitems.enums.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.company.commands.CommandsConstants.*;

public class CreateStoryCommand implements Command {

    private static final String[] CREATE_STORY_LINE_INTERFACE = {"Enter story title: ",
            "Enter description: ",
            "Enter priority " + Arrays.toString(Priority.values()) + ": ",
            "Enter size " + Arrays.toString(Size.values()) + ": ",
            "Enter status " + Arrays.toString(StoryStatus.values()) + ": ",
            "Enter team name: ",
            "Enter board name: "};
    private static final int EXPECTED_NUMBER_OF_ARGUMENTS = 7;

    private final WimFactory wimFactory;
    private final WimRepository wimRepository;
    private Date date = new Date();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);


    public CreateStoryCommand(WimFactory wimFactory, WimRepository wimRepository) {
        this.wimFactory = wimFactory;
        this.wimRepository = wimRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        if (parameters.size() == 0) {
            LineInterface.getParametersFromLineInterface(CREATE_STORY_LINE_INTERFACE, parameters);
        }
        CommandsValidationHelper.checkNumberOfArguments(EXPECTED_NUMBER_OF_ARGUMENTS, parameters.size());

        long itemID = wimRepository.getNextItemId();
        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = Priority.customValueOf(parameters.get(2));
        Size size = Size.customValueOf(parameters.get(3));
        StoryStatus status = StoryStatus.customValueOf(parameters.get(4));
        String teamToAdd = parameters.get(5);
        String boardToAdd = parameters.get(6);

        CommandsValidationHelper.checkIfTeamExist(teamToAdd, wimRepository);
        CommandsValidationHelper.checkIfBoardExist(teamToAdd, boardToAdd, wimRepository);
        CommandsValidationHelper.checkIfItemAlreadyExistInBoard(title, teamToAdd, boardToAdd, wimRepository);

        return createStory(itemID, title, description, priority, size, status, teamToAdd, boardToAdd);
    }

    private String createStory(long itemID, String title, String description, Priority priority,
                               Size size, StoryStatus status, String teamToAdd, String boardToAdd) {

        Story story = wimFactory.createStory(itemID, title, description, priority, size, status);
        wimRepository.getTeams().get(teamToAdd).getBoards().get(boardToAdd).addStory(title, story);
        BoardImpl board = wimRepository.getTeams().get(teamToAdd).getBoards().get(boardToAdd);
        board.addStory(title, story);
        story.setBoard(boardToAdd);
        board.addActivity(String.format(STORY_ADD_TO_BOARD_ACTIVITY_MESSAGE, formatter.format(date), title));
        story.addActivity(String.format(STORY_ADD_TO_BOARD_STORY_ACTIVITY_MESSAGE, formatter.format(date), title, boardToAdd));

        return String.format(STORY_CREATED_SUCCESS_MESSAGE, itemID, title, boardToAdd);
    }
}
