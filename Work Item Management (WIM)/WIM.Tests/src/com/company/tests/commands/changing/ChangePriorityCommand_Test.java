package com.company.tests.commands.changing;

import com.company.commands.changing.ChangePriorityCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.*;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.workitems.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangePriorityCommand_Test {
    private Command testCommand;
    private BugImpl testBug;
    private StoryImpl testStory;
    private WimRepository wimRepository;
    private MemberImpl member = new MemberImpl("Maurice");

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        testCommand = new ChangePriorityCommand(wimRepository);
        wimRepository.addPerson(member.getName(), member);
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        testBug = new BugImpl(1, "Bugtest1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        TeamImpl team = new TeamImpl("Team1");
        testStory = new StoryImpl(1, "Storytest1", "TestDescriptionforTest",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        wimRepository.addTeam("Team1", team);
        team.addMember(member);
        BoardImpl board = new BoardImpl("Board1", "Team1");
        testBug.setBoard("Board1");
        testStory.setBoard("Board1");
        wimRepository.getTeams().get(team.getName()).addBoard(board);
        wimRepository.getTeams().get("Team1").addBoard(board);
        board.addBug(testBug.getTitle(), testBug);
        board.addStory(testStory.getTitle(), testStory);
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
    public void execute_should_changePriorityBug_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Maurice");
        testList.add("Bugtest1");
        testList.add("low");
        testList.add("Team1");
        testList.add("Board1");

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(Priority.valueOf("LOW"), ((Bug) testBug).getPriority());
    }

    @Test
    public void execute_should_changePriorityStory_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Maurice");
        testList.add("Storytest1");
        testList.add("low");
        testList.add("Team1");
        testList.add("Board1");

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(Priority.valueOf("LOW"), ((Story) testStory).getPriority());
    }
}
