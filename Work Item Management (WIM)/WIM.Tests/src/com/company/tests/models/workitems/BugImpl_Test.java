package com.company.tests.models.workitems;

import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.workitems.BugImpl;
import com.company.models.workitems.enums.BugSeverity;
import com.company.models.workitems.enums.BugStatus;
import com.company.models.workitems.enums.Priority;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BugImpl_Test {
    WimRepository wimRepository;

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        BoardImpl testBoard = new BoardImpl("Board1", "Team1");
        TeamImpl testTeam = new TeamImpl("Team1");
        testTeam.addBoard(testBoard);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throwError_when_NameIsNull() {
        //Arrange, Act, Assert
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);

    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_descriptionIsNull() {
        //Arrange,Act,Assert
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtest1", "",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_nameLengthIsInvalid() {
        //Arrange, Act, Assert
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bug", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
    }

    @Test
    public void constructor_should_return_bug_when_argumentsAreValid() {
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
    }

    @Test
    public void getActivityHistory_should_not_returnShallowCopy() {
        //Arrange
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        testBug.addActivity("Item's activity was changed'");

        //Assert and Act
        Assert.assertEquals(1, testBug.getHistory().size());
    }

    @Test
    public void addComment_should_not_returnShallowCopy() {
        //Arrange
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        testBug.addComment("Item's activity was changed");

        //Assert and Act
        Assert.assertEquals(1, testBug.getComments().size());
    }

    @Test
    public void addAssignee_should_not_returnShallowCopy() {
        //Arrange
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        MemberImpl member = new MemberImpl("Maurice");
        testBug.assignMember(member);

        //Assert and Act
        Assert.assertEquals(1, testBug.getAssignees().size());
    }

    @Test
    public void getBoard_should_returnSetBoard() {
        //Arrange
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);
        testBug.setBoard("Board1");

        //Assert and Act
        Assert.assertEquals("Board1", testBug.getBoard());
    }

    @Test
    public void testToString() {
        //Arrange
        String expected = "Title: Bugtest1" + System.lineSeparator() +
                "Item ID: 1" + System.lineSeparator() +
                "Description: Thisistest" + System.lineSeparator() +
                "Activity history: " + System.lineSeparator() +
                System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                System.lineSeparator() +
                "Severity: MINOR" + System.lineSeparator() +
                "Priority: LOW" + System.lineSeparator() +
                "Stats: ACTIVE" + System.lineSeparator() +
                "Steps to reproduce: " + System.lineSeparator() +
                "Open" + System.lineSeparator() +
                "Close" +
                System.lineSeparator();
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtest1", "Thisistest",
                Priority.LOW, BugSeverity.MINOR, BugStatus.ACTIVE, steps);

        //Assert and Act
        Assert.assertEquals(expected, testBug.toString());
    }

    @Test
    public void getSteps_should_not_returnShallowCopy() {
        //Arrange
        List<String> steps = new ArrayList<>();
        steps.add("Open");
        steps.add("Close");
        BugImpl testBug = new BugImpl(1, "Bugtst1", "TestDescriptionforTest",
                Priority.HIGH, BugSeverity.MAJOR, BugStatus.ACTIVE, steps);

        //Assert and Act
        Assert.assertEquals(2, testBug.getStepsToReproduce().size());
    }


}
