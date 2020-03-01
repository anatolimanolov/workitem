package com.company.tests.commands.changing;

import com.company.commands.changing.ChangeSeverityCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Board;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Team;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.Priority;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeSeverityCommand_Test {
    private Command testCommand;
    private Bug testItem;
    private WimRepository wimRepository;
    private MemberImpl member = new MemberImpl("Maurice");

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        testCommand = new ChangeSeverityCommand(wimRepository);
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        testItem = new BugImpl(1, "Bugtest1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        TeamImpl team = new TeamImpl("Team1");
        wimRepository.addTeam("Team1", team);
        BoardImpl board = new BoardImpl("Board1", "Team1");
        wimRepository.getTeams().get("Team1").addBoard(board);
        testItem.setBoard("Board1");
        wimRepository.addPerson("Maurice", member);
        wimRepository.getTeams().get(team.getName()).getBoards().get(board.getName()).addBug("Bugtest1", (Bug) testItem);
        board.addBug("Bugtest1", testItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throwException_when_passedLessArguments() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Test2");
//        testList.add("low");


        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_changeSeverity_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Bugtest1");
        testList.add("minor");
        testList.add("Maurice");
        testList.add("Team1");
        testList.add("Board1");

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(BugSeverity.valueOf("MINOR"), ((Bug) testItem).getSeverity());
    }
}
