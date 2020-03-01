package com.company.core;

import com.company.core.contracts.WimRepository;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;
import com.company.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WimRepositoryImpl implements WimRepository {

    private Map<String, MemberImpl> members;
    private Map<String, TeamImpl> teams;

    public WimRepositoryImpl() {
        this.members = new HashMap<>();
        this.teams = new HashMap<>();
    }

    @Override
    public Map<String, MemberImpl> getPeople() {
        return new HashMap<>(members);
    }

    @Override
    public Map<String, TeamImpl> getTeams() {
        return new HashMap<>(teams);
    }

    public long getNextItemId() {

        List<WorkItem> items = new ArrayList<>();

        getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getBugs().forEach((c, d) -> items.add(d))));
        getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getFeedbacks().forEach((c, d) -> items.add(d))));
        getTeams().forEach((k, v) -> v.getBoards().
                forEach((a, b) -> b.getStories().forEach((c, d) -> items.add(d))));
        return items.size() + 1; //we add 1 to avoid creating item with id 0;
    }

    @Override
    public void addPerson(String name, MemberImpl person) {
        this.members.put(name, person);
    }

    @Override
    public void addTeam(String name, TeamImpl team) {
        this.teams.put(name, team);
    }

}
