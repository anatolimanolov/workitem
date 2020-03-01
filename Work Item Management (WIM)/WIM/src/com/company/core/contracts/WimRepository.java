package com.company.core.contracts;

import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;

import java.util.Map;

public interface WimRepository {
    Map<String, MemberImpl> getPeople();

    Map<String, TeamImpl> getTeams();

    long getNextItemId();

    void addPerson (String name, MemberImpl person);

    void addTeam (String name, TeamImpl team);

}
