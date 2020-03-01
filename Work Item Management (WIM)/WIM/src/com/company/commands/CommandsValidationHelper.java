package com.company.commands;

import com.company.core.contracts.WimRepository;

import static com.company.commands.CommandsConstants.*;

public class CommandsValidationHelper {

    public static void checkNumberOfArguments(int expectedNumberOfArguments, int receivedNumberOfArguments) {
        if (expectedNumberOfArguments != receivedNumberOfArguments) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_OF_ARGUMENTS_EXCEPTION,
                    expectedNumberOfArguments, receivedNumberOfArguments));
        }
    }

    public static void checkIfTeamExist(String teamName, WimRepository wimRepository) {
        if (!wimRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_DOES_NOT_EXIST_EXCEPTION, teamName));
        }
    }

    public static void checkIfTeamAlreadyExist(String teamName, WimRepository wimRepository) {
        if (wimRepository.getTeams().containsKey(teamName)) {
            throw new IllegalArgumentException(String.format(TEAM_ALREADY_EXIST_EXCEPTION, teamName));
        }
    }

    public static void checkIfMemberExist(String memberName, WimRepository wimRepository) {
        if (!wimRepository.getPeople().containsKey(memberName)) {
            throw new IllegalArgumentException(String.format(MEMBER_DOES_NOT_EXIST_EXCEPTION, memberName));
        }
    }

    public static void checkIfMemberAlreadyExist(String memberName, WimRepository wimRepository) {
        if (wimRepository.getPeople().containsKey(memberName)) {
            throw new IllegalArgumentException(String.format(MEMBER_ALREADY_EXIST_EXCEPTION, memberName));
        }
    }

    public static void checkIfBoardExist(String teamName, String boardName, WimRepository wimRepository) {
        if (!wimRepository.getTeams().get(teamName).getBoards().containsKey(boardName)) {
            throw new IllegalArgumentException(String.format(BOARD_DOES_NOT_EXIST_EXCEPTION, boardName));
        }
    }

    public static void checkIfBoardExistInTeam(String boardName, String teamName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(teamName).getBoards().containsKey(boardName)) {
            throw new IllegalArgumentException(String.format(BOARD_EXIST_IN_TEAM_EXCEPTION,
                    boardName, teamName));
        }
    }

    public static void checkIfItemExist(String team, String board, String itemName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemName) == null &&
                wimRepository.getTeams().get(team).getBoards().get(board).getFeedbacks().get(itemName) == null &&
                wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemName) == null) {
            throw new IllegalArgumentException(String.format(ITEM_DOES_NOT_EXIST_EXCEPTION, itemName));
        }
    }

    public static void checkIfItemAlreadyExistInBoard(String itemName,String teamName, String boardName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getBugs().containsKey(itemName) ||
                wimRepository.getTeams().get(teamName).getBoards().get(boardName).getFeedbacks().containsKey(itemName) ||
                wimRepository.getTeams().get(teamName).getBoards().get(boardName).getStories().containsKey(itemName)) {
            throw new IllegalArgumentException(String.format(ITEM_ALREADY_EXIST_EXCEPTION, itemName, boardName));
        }
    }

    public static void checkIfAlreadyAssigned(String memberName, String teamName, String boardName
            , String itemName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getBugs().containsKey(itemName)) {
            if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getBugs().get(itemName).getAssignees()
                    .contains(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(String.format(ALREADY_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }
        if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getStories().containsKey(itemName)) {
            if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getStories().get(itemName).getAssignees()
                    .contains(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(String.format(ALREADY_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }
    }

    public static void checkIfItemAssignedToMember(String memberName, String teamName, String boardName
            , String itemName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getBugs().containsKey(itemName)) {
            if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getBugs().get(itemName).getAssignees()
                    .contains(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(String.format(NOT_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }
        if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getStories().containsKey(itemName)) {
            if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getStories().get(itemName).getAssignees()
                    .contains(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(String.format(NOT_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }
        if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getFeedbacks().containsKey(itemName)) {
            if (wimRepository.getTeams().get(teamName).getBoards().get(boardName).getFeedbacks().get(itemName).getAuthor()
                    .equals(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(String.format(NOT_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }

    }

    public static void checkIfNotAssigned(String memberName, String team, String board,
                                          String itemName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(team).getBoards().get(board).getBugs().containsKey(itemName)) {
            if (!wimRepository.getTeams().get(team).getBoards().get(board).getBugs().get(itemName).getAssignees().
                    contains(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(
                        String.format(NOT_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }
        if (wimRepository.getTeams().get(team).getBoards().get(board).getStories().containsKey(itemName)) {
            if (!wimRepository.getTeams().get(team).getBoards().get(board).getStories().get(itemName).getAssignees().
                    contains(wimRepository.getPeople().get(memberName))) {
                throw new IllegalArgumentException(
                        String.format(NOT_ASSIGNED_EXCEPTION_MESSAGE, itemName, memberName));
            }
        }
    }

    public static void checkIfTeamHasBoards(String teamName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(teamName).getBoards().isEmpty()) {
            throw new IllegalArgumentException(String.format(NO_BOARDS_IN_TEAM_EXCEPTION_MESSAGE, teamName));
        }
    }

    public static void checkIfTeamHasNoMembers(String teamName, WimRepository wimRepository) {
        if (wimRepository.getTeams().get(teamName).getMembers().isEmpty()) {
            throw new IllegalArgumentException(String.format(NO_MEMBERS_IN_TEAM_EXCEPTION_MESSAGE, teamName));
        }
    }

    public static void checkIfMembersExist(WimRepository wimRepository) {
        if (wimRepository.getPeople().isEmpty()) {
            throw new IllegalArgumentException(NO_REGISTERED_MEMBERS_EXCEPTION_MESSAGE);
        }
    }

    public static void checkIfMemberIsInTeam(String teamName, String memberName, WimRepository wimRepository) {
        if (!wimRepository.getTeams().get(teamName).getMembers().containsKey(memberName)) {
            throw new IllegalArgumentException(NO_REGISTERED_MEMBERS_EXCEPTION_MESSAGE);
        }
    }

    public static void checkIfTeamsExist(WimRepository wimRepository) {
        if (wimRepository.getTeams().isEmpty()) {
            throw new IllegalArgumentException(NO_REGISTERED_TEAMS_EXCEPTION_MESSAGE);
        }
    }

    public static void isInputNumeric(String input) {
        try {
            int i = Integer.parseInt(input);
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new IllegalArgumentException(INVALID_INPUT_EXCEPTION);
        }
    }
}
