package com.company.core.factories;

import com.company.commands.AddCommentCommand;
import com.company.commands.AddPersonToTeamCommand;
import com.company.commands.HelpCommand;
import com.company.commands.changing.*;
import com.company.commands.assignation.AssignItemCommand;
import com.company.commands.assignation.UnassignItemCommand;
import com.company.commands.contracts.Command;
import com.company.commands.creation.*;
import com.company.commands.enums.CommandType;
import com.company.commands.listing.*;
import com.company.commands.showing.ShowBoardActivityCommand;
import com.company.commands.showing.ShowPersonActivityCommand;
import com.company.commands.showing.ShowTeamActivityCommand;
import com.company.core.contracts.CommandFactory;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;

public class CommandFactoryImpl implements CommandFactory {
    private static final String INVALID_COMMAND = "Invalid command name: %s!";

    public Command createCommand(String commandName, WimFactory wimFactory, WimRepository wimRepository) {
        CommandType commandType = CommandType.customValueOf(commandName);
        switch (commandType) {

            //creation
            case CREATEPERSON:
                return new CreatePersonCommand(wimFactory, wimRepository);
            case CREATETEAM:
                return new CreateTeamCommand(wimFactory, wimRepository);
            case CREATEBOARD:
                return new CreateBoardCommand(wimFactory, wimRepository);
            case CREATEBUG:
                return new CreateBugCommand(wimFactory, wimRepository);
            case CREATEFEEDBACK:
                return new CreateFeedbackCommand(wimFactory, wimRepository);
            case CREATESTORY:
                return new CreateStoryCommand(wimFactory, wimRepository);

            //listing
            case LISTPEOPLE:
                return new ListPeopleCommand(wimRepository);
            case LISTTEAMS:
                return new ListTeamsCommand(wimRepository);
            case LISTBOARDS:
                return new ListBoardsCommand(wimRepository);
            case LISTITEMS:
                return new ListItemsCommand(wimRepository);
            case LISTMEMBERS:
                return new ListMembersCommand(wimRepository);

            //assignation
            case ASSIGNITEM:
                return new AssignItemCommand(wimRepository);
            case UNASSIGNITEM:
                return new UnassignItemCommand(wimRepository);

            //showing
            case SHOWPERSONACTIVITY:
                return new ShowPersonActivityCommand(wimRepository);
            case SHOWTEAMACTIVITY:
                return new ShowTeamActivityCommand(wimRepository);
            case SHOWBOARDACTIVITY:
                return new ShowBoardActivityCommand(wimRepository);

            //changing
            case CHANGEPRIORITY:
                return new ChangePriorityCommand(wimRepository);
            case CHANGESEVERITY:
                return new ChangeSeverityCommand(wimRepository);
            case CHANGESTATUS:
                return new ChangeStatusCommand(wimRepository);
            case CHANGESIZE:
                return new ChangeSizeCommand(wimRepository);
            case CHANGERATING:
                return new ChangeRatingCommand(wimRepository);

            case ADDPERSONTOTEAM:
                return new AddPersonToTeamCommand(wimRepository);
            case ADDCOMMENT:
                return new AddCommentCommand(wimRepository);

            case HELP:
                return new HelpCommand();

        }
        throw new IllegalArgumentException(String.format(INVALID_COMMAND, commandName));
    }
}

