package com.company.tests.commands.assignation;

import com.company.commands.assignation.UnassignItemCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.FeedbackImpl;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.FeedbackStatus;
import com.company.models.workitems.enums.Priority;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.company.commands.CommandsConstants.ITEM_NOT_ASSIGNABLE_ERROR_MESSAGE;

public class UnassignItemCommand_Tests {

    private WimFactory wimFactory;
    private WimRepository wimRepository;
    private Command testCommand;
    private TeamImpl testTeam;
    private BoardImpl testBoard;
    private MemberImpl testMember;
    private BugImpl testBug;
    private FeedbackImpl testFeedback;
    private List<String> testSteps;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("testTeam");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testBoard = new BoardImpl("testBoard", testTeam.getName());
        wimRepository.getTeams().get(testTeam.getName()).addBoard(testBoard);
        testMember = new MemberImpl("Maurice");
        wimRepository.addPerson(testMember.getName(), testMember);
        testTeam.addMember(testMember);
        testSteps = new ArrayList<>();
        testSteps.add("step 1");
        testBug = new BugImpl(1, "testBug", "description", Priority.LOW,
                BugSeverity.MAJOR, BugStatus.ACTIVE, testSteps);
        wimRepository.getTeams().get(testTeam.getName()).getBoards().get(testBoard.getName())
                .addBug(testBug.getTitle(), testBug);
        testBug.assignMember(testMember);
        testFeedback = new FeedbackImpl(1, "testFeedbkack", "description", 6,
                FeedbackStatus.NEW, testMember);
        wimRepository.getTeams().get(testTeam.getName()).getBoards().get(testBoard.getName())
                .addFeedback(testFeedback.getTitle(), testFeedback);
        testCommand = new UnassignItemCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testBug.getTitle());
        testList.add(testMember.getName());
        testList.add("blabla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_less_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testBug.getTitle());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_InvalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testBug.getTitle());
        testList.add("blabla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_ItemAlreadyUnassigned() {
        //Arrange
        testBug.getAssignees().remove(testMember);
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add(testBug.getTitle());

        //Act & Assert
        testCommand.execute(testList);
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_trow_when_ItemNotAssignale() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add(testBug.getTitle());

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(String.format(ITEM_NOT_ASSIGNABLE_ERROR_MESSAGE, testFeedback.getTitle()), 1, 1);
    }

    @Test
    public void execute_should_UnassignItem_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add(testBug.getTitle());

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(0, testBug.getAssignees().size());
    }
}
