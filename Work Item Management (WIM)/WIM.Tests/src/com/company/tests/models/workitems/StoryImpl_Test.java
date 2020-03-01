package com.company.tests.models.workitems;

import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.workitems.enums.Priority;
import com.company.models.workitems.enums.Size;
import com.company.models.workitems.enums.StoryStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StoryImpl_Test {
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
        StoryImpl newStory = new StoryImpl(1, "", "TestDescriptionforTest",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_descriptionIsNull() {
        //Arrange,Act,Assert
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_error_when_nameLengthIsInvalid() {
        //Arrange, Act, Assert
        StoryImpl newStory = new StoryImpl(1, "Stor", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
    }

    @Test
    public void constructor_should_return_bug_when_argumentsAreValid() {
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
    }

    @Test
    public void getActivityHistory_should_not_returnShallowCopy() {
        //Arrange
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        newStory.addActivity("Item's activity was changed'");

        //Assert and Act
        Assert.assertEquals(1, newStory.getHistory().size());
    }

    @Test
    public void addComment_should_not_returnShallowCopy() {
        //Arrange
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        newStory.addComment("Test comment'");

        //Assert and Act
        Assert.assertEquals(1, newStory.getComments().size());
    }

    @Test
    public void addAssignee_should_not_returnShallowCopy() {
        //Arrange
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        MemberImpl member = new MemberImpl("Maurice");
        newStory.assignMember(member);

        //Assert and Act
        Assert.assertEquals(1, newStory.getAssignees().size());
    }

    @Test
    public void unAssignee_should_returnShallowCopy() {
        //Arrange
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        MemberImpl member = new MemberImpl("Maurice");
        newStory.assignMember(member);
        newStory.unassignMember(member);

        //Assert and Act
        Assert.assertEquals(0, newStory.getAssignees().size());
    }

    @Test
    public void getBoard_should_returnSetBoard() {
        //Arrange
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistestdescription",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        newStory.setBoard("Board1");

        //Assert and Act
        Assert.assertEquals("Board1", newStory.getBoard());
    }

    @Test
    public void testToString() {
        //Arrange
        String expected = "Title: Storytest1" + System.lineSeparator() +
                "Item ID: 1" + System.lineSeparator() +
                "Description: Thisistest" + System.lineSeparator() +
                "Activity history: " + System.lineSeparator() +
                System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                System.lineSeparator() +
                "Priority: HIGH" + System.lineSeparator() +
                "Size: MEDIUM" + System.lineSeparator() +
                "Status: NOTDONE" + System.lineSeparator();
        StoryImpl newStory = new StoryImpl(1, "Storytest1", "Thisistest",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);

        //Assert and Act
        Assert.assertEquals(expected, newStory.toString());
    }

}
