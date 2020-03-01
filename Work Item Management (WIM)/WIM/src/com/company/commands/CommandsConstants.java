package com.company.commands;

public class CommandsConstants {
    //Error messages
    public static final String ALREADY_ASSIGNED_EXCEPTION_MESSAGE = "%s is already assigned to %s.";
    public static final String NOT_ASSIGNED_EXCEPTION_MESSAGE = "%s is not assigned to %s.";
    public static final String ITEM_NOT_ASSIGNABLE_ERROR_MESSAGE = "Item '%s' is not assignable.";
    public static final String ITEM_DOES_NOT_HAVE_PRIORITY_ERROR_MESSAGE = "Item '%s' does not have priority.";
    public static final String ITEM_DOES_NOT_HAVE_SEVERITY_ERROR_MESSAGE = "Item '%s' does not have severity.";
    public static final String ITEM_DOES_NOT_HAVE_SIZE_ERROR_MESSAGE = "Item '%s' does not have size.";
    public static final String NO_ACTIVITY_ERROR_MESSAGE = "There are is no activity in %s.";

    // Success messages
    public static final String MEMBER_CREATED_SUCCESS_MESSAGE =
            "Member with name '%s' was successfully created.";
    public static final String BOARD_CREATED_SUCCESS_MESSAGE =
            "Board with name '%s' was successfully created in team %s.";
    public static final String TEAM_CREATED_SUCCESS_MESSAGE =
            "Team with name '%s' was successfully created.";
    public static final String BUG_CREATED_SUCCESS_MESSAGE =
            "Bug with ID %04d and title '%s' was successfully created in board %s.";
    public static final String FEEDBACK_CREATED_SUCCESS_MESSAGE =
            "Feedback with ID %04d and title '%s' was successfully created in board %s.";
    public static final String STORY_CREATED_SUCCESS_MESSAGE =
            "Story with ID %04d and title '%s' was successfully created in board %s.";

    public static final String ITEM_CHANGED_PRIORITY_SUCCESS_MESSAGE = "Item %s priority is changed";
    public static final String BUG_CHANGED_STATUS_SUCCESS_MESSAGE = "Bug %s status is changed";
    public static final String STORY_CHANGED_STATUS_SUCCESS_MESSAGE = "Story %s status is changed";
    public static final String FEEDBACK_CHANGED_STATUS_SUCCESS_MESSAGE = "Feedback %s status is changed";
    public static final String FEEDBACK_CHANGED_RATING_SUCCESS_MESSAGE = "Feedback %s rating is changed";
    public static final String BUG_CHANGED_SEVERITY_SUCCESS_MESSAGE = "Bug %s severity is changed";
    public static final String STORY_CHANGED_SIZE_SUCCESS_MESSAGE = "Story %s size is changed";
    public static final String PERSON_ADDED_TO_TEAM_SUCCESS_MESSAGE = "%s was successfully added to %s.";
    public static final String SUCCESSFULLY_ASSIGNED_MESSAGE = "%s was successfully assigned to %s.";
    public static final String SUCCESSFULLY_UNASSIGNED_MESSAGE = "%s was successfully unassigned from %s.";
    public static final String COMMENT_SUCCESSFULLY_ADDED_MESSAGE = "Comment was successfully added to %s.";

    //Activity
    public static final String BOARD_CREATED_IN_TEAM_ACTIVITY = "%s Board %s created in %s team";
    public static final String BUG_CHANGED_PRIORITY_ACTIVITY_MESSAGE = "%s%s priority was changed to %s";
    public static final String BUG_CHANGED_PRIORITY_BUG_ACTIVITY_MESSAGE = "%s%s priority was changed to %s";
    public static final String BUG_CHANGED_SEVERITY_ACTIVITY_MESSAGE = "%s%s severity was changed to %s";
    public static final String BUG_CHANGED_SEVERITY_BUG_ACTIVITY_MESSAGE = "%s%s severity was changed to %s";
    public static final String BUG_CHANGED_STATUS_ACTIVITY_MESSAGE = "%s%s status was changed to %s";
    public static final String BUG_CHANGED_STATUS_BUG_ACTIVITY_MESSAGE = "%s%s status was changed to %s";
    public static final String STORY_CHANGED_PRIORITY_ACTIVITY_MESSAGE = "%s%s priority was changed to %s";
    public static final String STORY_CHANGED_PRIORITY_STORY_ACTIVITY_MESSAGE = "%s%s priority was changed to %s";
    public static final String STORY_SIZE_ACTIVITY_MESSAGE = "%s%s size was changed to %s";
    public static final String STORY_SIZE_STORY_ACTIVITY_MESSAGE = "%s%s size was changed to %s";
    public static final String STORY_CHANGED_STATUS_ACTIVITY_MESSAGE = "%s%s status was changed to %s";
    public static final String STORY_CHANGED_STATUS_STORY_ACTIVITY_MESSAGE = "%s%s status was changed to %s";
    public static final String FEEDBACK_CHANGED_RATING_ACTIVITY_MESSAGE = "%s%s rating was changed to %s";
    public static final String FEEDBACK_CHANGED_RATING_FEEDBACK_ACTIVITY_MESSAGE = "%s%s rating was changed to %s";
    public static final String FEEDBACK_CHANGED_STATUS_ACTIVITY_MESSAGE = "%s%s status was changed to %s";
    public static final String FEEDBACK_CHANGED_STATUS_FEEDBACK_ACTIVITY_MESSAGE = "%s%s status was changed to %s";
    public static final String BUG_ADD_TO_BOARD_ACTIVITY_MESSAGE = "%s%s was added";
    public static final String BUG_ADD_TO_BOARD_BUG_ACTIVITY_MESSAGE = "%s%s was added to %s";
    public static final String STORY_ADD_TO_BOARD_ACTIVITY_MESSAGE = "%s%s was added";
    public static final String STORY_ADD_TO_BOARD_STORY_ACTIVITY_MESSAGE = "%s%s was added to %s";
    public static final String FEEDBACK_ADD_TO_BOARD_ACTIVITY_MESSAGE = "%s%s was added";
    public static final String FEEDBACK_ADD_TO_BOARD_FEEDBACK_ACTIVITY_MESSAGE = "%s%s was added to %s";
    public static final String ASSIGNED_MEMBER_ACTIVITY = "%s was assigned to %s";
    public static final String UNASSIGNED_MEMBER_ACTIVITY = "%s was unassigned from %s";

    // Exception
    public static final String TEAM_DOES_NOT_EXIST_EXCEPTION = "There is no registered team with name '%s'.";
    public static final String TEAM_ALREADY_EXIST_EXCEPTION = "There is already a registered team with name '%s'.";
    public static final String MEMBER_DOES_NOT_EXIST_EXCEPTION = "There is no registered member with name '%s'.";
    public static final String MEMBER_DOES_NOT_EXIST_IN_TEAM_EXCEPTION = "Member must be part of '%s'.";
    public static final String MEMBER_ALREADY_EXIST_EXCEPTION = "There is already a registered member with name '%s'.";
    public static final String ITEM_DOES_NOT_EXIST_EXCEPTION = "There is no registered item with title '%s'.";
    public static final String ITEM_ALREADY_EXIST_EXCEPTION = "There is already a registered item with title '%s' in board %s.";
    public static final String BOARD_DOES_NOT_EXIST_EXCEPTION = "There is no registered board with name '%s'.";
    public static final String BOARD_EXIST_IN_TEAM_EXCEPTION = "There is already a registered board with name '%s' in team %s.";
    public static final String INVALID_NUMBER_OF_ARGUMENTS_EXCEPTION = "Invalid number of arguments. Expected: %d, Received: %d";
    public static final String INVALID_INPUT_EXCEPTION = "Rating must be a valid number.";
    public static final String NO_BOARDS_IN_TEAM_EXCEPTION_MESSAGE = "There are no registered boards in %s.";
    public static final String NO_MEMBERS_IN_TEAM_EXCEPTION_MESSAGE = "There are no registered members in %s.";
    public static final String NO_REGISTERED_MEMBERS_EXCEPTION_MESSAGE = "There are no registered members.";
    public static final String NO_REGISTERED_TEAMS_EXCEPTION_MESSAGE = "There are no registered teams.";

    // Delimiters
    public static final String JOIN_DELIMITER = "------------------------------";

    // Date format
    public static final String DATE_FORMAT = " dd-MM-yyyy HH:mm: ";
}
