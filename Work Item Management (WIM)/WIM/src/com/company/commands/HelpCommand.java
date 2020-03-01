package com.company.commands;

import com.company.commands.contracts.Command;

import java.util.List;

import static com.company.commands.CommandsConstants.JOIN_DELIMITER;

public class HelpCommand implements Command {

    private static final String HELP = JOIN_DELIMITER + System.lineSeparator() +
            "Commands list:" + System.lineSeparator() +
            JOIN_DELIMITER + System.lineSeparator() +
            "--[commandName]--  --[arguments]--" + System.lineSeparator() + System.lineSeparator() +
            "createperson         <personName>" + System.lineSeparator() +
            "createteam           <teamName>" + System.lineSeparator() +
            "createboard          <boardName> <teamName>" + System.lineSeparator() +
            "createbug            <bugTitle> <description> <stepsToReproduce> <priority> <severity> <status> <teamName> <boardName>" + System.lineSeparator() +
            "createfeedback       <feedbackTitle> <description> <rating> <status> <boardName> <teamName> <author>" + System.lineSeparator() +
            "createstory          <storyTitle> <description> <priority> <size> <status> <teamName> <boardName>" + System.lineSeparator() +
            "listpeople           <n/a>" + System.lineSeparator() +
            "listteams            <n/a>" + System.lineSeparator() +
            "listmembers          <teamName>" + System.lineSeparator() +
            "listboards           <teamName>" + System.lineSeparator() +
            "listitems            <all/bug/story/feedback/memberName>" + System.lineSeparator() +
            "listitems            <all/bug/story/feedback/memberName> <status>" + System.lineSeparator() +
            "listitems            <all/bug/story/feedback/memberName> <title/priority/severity/size/rating>" + System.lineSeparator() +
            "listitems            <all/bug/story/feedback/memberName> <status> <title/priority/severity/size/rating>" + System.lineSeparator() +
            "assignitem           <memberName> <teamName> <boardName> <bugName/storyName> " + System.lineSeparator() +
            "unassignitem         <memberName> <teamName> <boardName> <bugName/storyName>" + System.lineSeparator() +
            "showpersonactivity   <memberName>" + System.lineSeparator() +
            "showteamactivity     <teamName>" + System.lineSeparator() +
            "showboardactivity    <teamName> <boardName>" + System.lineSeparator() +
            "changepriority       <memberName> <itemTitle> <priority> <teamName> <boardName>" + System.lineSeparator() +
            "changeseverity       <itemTitle> <severity> <teamName> <boardName>" + System.lineSeparator() +
            "changestatus         <itemTitle> <status> <teamName> <boardName>" + System.lineSeparator() +
            "changesize           <itemTitle> <size> <teamName> <boardName>" + System.lineSeparator() +
            "changerating         <itemTitle> <rating> <memberName> <teamName> <boardName>" + System.lineSeparator() +
            "addpersontoteam,     <memberName> <teamName>" + System.lineSeparator() +
            "addcomment,          <author> <teamName> <boardName> <itemTitle> <comment>" + System.lineSeparator() +
            JOIN_DELIMITER;

    public HelpCommand() {
    }

    @Override
    public String execute(List<String> parameters) {
        return HELP;
    }
}
