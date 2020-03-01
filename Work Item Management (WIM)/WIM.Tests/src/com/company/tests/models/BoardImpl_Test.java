package com.company.tests.models;

import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Board;
import com.company.models.contracts.Team;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.FeedbackImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.workitems.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardImpl_Test {
    private Team testTeam;
    private Board testBoard;
    private WimRepository wimRepository;


    @Before
    public void before() {
        testTeam = new TeamImpl("Team1");
        testBoard = new BoardImpl("Board1", "Team1");
        wimRepository = new WimRepositoryImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_NameIsNull() {
        //Arrange, Act, Assert
        Board newBoard = new BoardImpl("null", "Team1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_TeamNameIsInvalid() {
        //Arrange,Act,Assert
        Board newBoard = new BoardImpl("Board1", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_nameLengthIsInvalid() {
        //Arrange, Act, Assert
        Board newBoard = new BoardImpl("Bor", "Team1");
    }

    @Test
    public void constructor_should_return_board_when_argumentsAreValid() {
        Board newBoard = new BoardImpl("Board1", "Team1");
    }

    @Test
    public void getBoards_should_not_returnShallowCopy() {
        //Arrange
        BoardImpl newBoard = new BoardImpl("Board1", "Team1");
        testTeam.addBoard(newBoard);

        //Assert and Act
        Assert.assertEquals(1, testTeam.getBoards().size());
    }

    @Test
    public void getBugs_should_not_returnShallowCopy() {
        //Arrange
        BoardImpl newBoard = new BoardImpl("Board1", "Team1");
        testTeam.addBoard(newBoard);
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl newBug = new BugImpl(1, "Bugtest1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        testBoard.addBug(newBug.getTitle(), newBug);

        //Assert and Act
        Assert.assertEquals(1, testBoard.getBugs().size());
    }

    @Test
    public void getStories_should_not_returnShallowCopy() {
        //Arrange
        BoardImpl newBoard = new BoardImpl("Board1", "Team1");
        testTeam.addBoard(newBoard);
        StoryImpl newStroy = new StoryImpl(1, "Storytest1", "TestDescriptionforTest",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        testBoard.addStory(newStroy.getTitle(), newStroy);

        //Assert and Act
        Assert.assertEquals(1, testBoard.getStories().size());
    }

    @Test
    public void getFeedbacks_should_not_returnShallowCopy() {
        //Arrange
        BoardImpl newBoard = new BoardImpl("Board1", "Team1");
        testTeam.addBoard(newBoard);
        MemberImpl author = new MemberImpl("Maurice");
        FeedbackImpl newFeedback = new FeedbackImpl(1,"Feedback1", "Testdescription",
                5, FeedbackStatus.SCHEDULED, author);
        testBoard.addFeedback(newFeedback.getTitle(), newFeedback);

        //Assert and Act
        Assert.assertEquals(1, testBoard.getFeedbacks().size());
    }

    @Test
    public void getActivityHistory_should_not_returnShallowCopy() {
        //Arrange
        BoardImpl newBoard = new BoardImpl("Board1", "Team1");
        testTeam.addBoard(newBoard);
        MemberImpl author = new MemberImpl("Maurice");
        FeedbackImpl newFeedback = new FeedbackImpl(1,"Feedback1", "Testdescription",
                5, FeedbackStatus.SCHEDULED, author);
        testBoard.addFeedback(newFeedback.getTitle(), newFeedback);
        testBoard.addActivity("Items was added to board");

        //Assert and Act
        Assert.assertEquals(1, testBoard.getActivityHistory().size());
    }

}

