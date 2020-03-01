package com.company.models;

import com.company.models.contracts.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamImpl implements Team {
    private static final int MIN_NAME_LENGTH = 5;
    private static final int MAX_NAME_LENGTH = 10;
    private static final String NAME_ERROR_MESSAGE =
            "Team name must be between 5 and 10 characters";

    private String name;
    private HashMap<String, MemberImpl> members;
    private HashMap<String, BoardImpl> boards;
    private List<String> activityHistory;

    public TeamImpl(String name) {
        setName(name);
        this.members = new HashMap<>();
        this.boards = new HashMap<>();
        this.activityHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, MemberImpl> getMembers() {
        return members;
    }

    public HashMap<String, BoardImpl> getBoards() {
        return boards;
    }

    public void addMember(MemberImpl member) {
        members.put(member.getName(), member);
    }

    public void addBoard(BoardImpl board) {
        boards.put(board.getName(), board);
    }

    public List<String> getActivityHistory() {
        return activityHistory;
    }


    private void setName(String name) {
        ValidationHelper.checkNameValidation(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH, NAME_ERROR_MESSAGE);
        this.name = name;
    }

    public void addActivity(String activity) {
        activityHistory.add(activity);
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
