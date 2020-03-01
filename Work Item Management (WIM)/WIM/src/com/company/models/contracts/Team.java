package com.company.models.contracts;

import com.company.models.BoardImpl;
import com.company.models.MemberImpl;

import java.util.HashMap;
import java.util.List;

public interface Team {
    String getName();

    HashMap<String, MemberImpl> getMembers();

    HashMap<String, BoardImpl> getBoards();

    void addMember(MemberImpl member);

    void addBoard(BoardImpl board);

    List<String> getActivityHistory();

    String toString();
}
