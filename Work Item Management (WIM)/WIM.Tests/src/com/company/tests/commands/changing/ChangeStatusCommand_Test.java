package com.company.tests.commands.changing;

import com.company.commands.changing.ChangeStatusCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Bug;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.Story;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.FeedbackImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.workitems.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeStatusCommand_Test {
    private Command testCommand;
    private WorkItem testItemBug;
    private WorkItem testItemFeedback;
    private WorkItem testItemStory;
    private WimRepository wimRepository;
    private MemberImpl author;

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        testCommand = new ChangeStatusCommand(wimRepository);
        author = new MemberImpl("Maurice");
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        TeamImpl team = new TeamImpl("Team1");
        wimRepository.addTeam("Team1", team);
        BoardImpl board = new BoardImpl("Board1", "Team1");
        wimRepository.getTeams().get(team.getName()).addBoard(board);
        team.addMember(author);
        wimRepository.addPerson("Maurice", author);
        testItemBug = new BugImpl(2,"Bugtest1","Testdescription", Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        wimRepository.getTeams().get(team.getName()).getBoards().get(board.getName()).addBug("Bugtest1", (Bug) testItemBug);
        testItemBug.setBoard("Board1");
        testItemFeedback = new FeedbackImpl(1,"Feedback1", "Testdescription", 5, FeedbackStatus.SCHEDULED, author);
        testItemFeedback.setBoard("Board1");
        wimRepository.getTeams().get(team.getName()).getBoards().get(board.getName()).addFeedback("Feedback1", (Feedback) testItemFeedback);
        testItemStory = new StoryImpl(3,"Storytest1", "Testdescription", Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        wimRepository.getTeams().get(team.getName()).getBoards().get(board.getName()).addStory("Storytest1", (Story) testItemStory);
        testItemStory.setBoard("Board1");
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
    public void execute_should_changeStatusBug_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Bugtest1");
        testList.add("fixed");
        testList.add("Maurice");
        testList.add("Team1");
        testList.add("Board1");

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(BugStatus.FIXED, ((Bug) testItemBug).getStatus());
    }


    @Test
    public void execute_should_changeStatusFeedback_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Feedback1");
        testList.add("done");
        testList.add("Maurice");
        testList.add("Team1");
        testList.add("Board1");

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(FeedbackStatus.DONE, ((Feedback) testItemFeedback).getStatus());
    }

    @Test
    public void execute_should_changeStatusStory_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Storytest1");
        testList.add("done");
        testList.add("Maurice");
        testList.add("Team1");
        testList.add("Board1");

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(StoryStatus.DONE, ((Story) testItemStory).getStatus());
    }
}
