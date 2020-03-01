package com.company.commands.enums;

public enum CommandType {

    // creation
    CREATEPERSON,
    CREATETEAM,
    CREATEBOARD,
    CREATEBUG,
    CREATEFEEDBACK,
    CREATESTORY,

    // listing
    LISTPEOPLE,
    LISTITEMS,
    LISTBOARDS,
    LISTTEAMS,
    LISTMEMBERS,

    // assignation
    ASSIGNITEM,
    UNASSIGNITEM,

    // showing
    SHOWPERSONACTIVITY,
    SHOWTEAMACTIVITY,
    SHOWBOARDACTIVITY,

    // changing
    CHANGEPRIORITY,
    CHANGESEVERITY,
    CHANGESTATUS,
    CHANGESIZE,
    CHANGERATING,

    // add
    ADDPERSONTOTEAM,
    ADDCOMMENT,

    HELP;

    public static CommandType customValueOf(String commandType) {
        for (CommandType type : values()) {
            if (type.name().equals(commandType.toUpperCase())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid command: " + commandType
        + System.lineSeparator()
        +"Enter 'help' to display all available commands.");
    }
}
