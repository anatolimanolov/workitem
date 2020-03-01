package com.company.tests.models;

import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamImpl_Test {
    private Team testTeam;
    private WimRepository wimRepository;
    private MemberImpl member = new MemberImpl("Maurice");

    @Before
    public void before() {
        testTeam = new TeamImpl("Team1");
        wimRepository = new WimRepositoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_NameIsNull() {
        //Arrange, Act, Assert
        Team newTeam = new TeamImpl("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_nameLengthIsInvalid() {
        //Arrange, Act, Assert
        Team newTeam = new TeamImpl("Tea");
    }

    @Test
    public void constructor_should_return_team_when_argumentsAreValid() {
        Team newTeam = new TeamImpl("Team1");
    }

    @Test
    public void getTeams_should_not_returnShallowCopy() {
        //Arrange
        TeamImpl newTeam = new TeamImpl("Team1");
        wimRepository.addTeam(newTeam.getName(), newTeam);

        //Assert and Act
        Assert.assertEquals(1, wimRepository.getTeams().size());
    }

    @Test
    public void getBoards_should_not_returnShallowCopy() {
        //Arrange
        TeamImpl newTeam = new TeamImpl("Team1");
        BoardImpl newBoard = new BoardImpl("Board1", "Team1");
        newTeam.addBoard(newBoard);

        //Assert and Act
        Assert.assertEquals(1, newTeam.getBoards().size());
    }

    @Test
    public void getMembers_should_not_returnShallowCopy() {
        //Arrange
        TeamImpl newTeam = new TeamImpl("Team1");
        newTeam.addMember(member);

        //Assert and Act
        Assert.assertEquals(1, newTeam.getMembers().size());
    }

    @Test
    public void getActivityHistory_should_not_returnShallowCopy() {
        //Arrange
        TeamImpl newTeam = new TeamImpl("Team1");
        MemberImpl author = new MemberImpl("Maurice");
        newTeam.addMember(author);
        newTeam.addActivity("Member was added to Team1");

        //Assert and Act
        Assert.assertEquals(1, newTeam.getActivityHistory().size());
    }

}
