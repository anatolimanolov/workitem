package com.company.tests.models.workitems;

import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.workitems.FeedbackImpl;
import com.company.models.workitems.enums.FeedbackStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FeedbackImpl_Test {
    WimRepository wimRepository;

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        BoardImpl testBoard = new BoardImpl("Board1", "Team1");
        TeamImpl testTeam = new TeamImpl("Team1");
        testTeam.addBoard(testBoard);
    }

    @Test
    public void addComment_should_not_returnShallowCopy() {
        //Arrange
        MemberImpl author = new MemberImpl("Maurice");
        FeedbackImpl testFeedback = new FeedbackImpl(1,"Feedback1", "Testdescription",
                5, FeedbackStatus.SCHEDULED, author);
        testFeedback.addComment("Item's activity was changed");

        //Assert and Act
        Assert.assertEquals(1, testFeedback.getComments().size());
    }

    @Test
    public void getActivityHistory_should_not_returnShallowCopy() {
        //Arrange
        MemberImpl author = new MemberImpl("Maurice");
        FeedbackImpl testFeedback = new FeedbackImpl(1,"Feedback1", "Testdescription",
                5, FeedbackStatus.SCHEDULED, author);
        testFeedback.addActivity("Item's activity was changed'");

        //Assert and Act
        Assert.assertEquals(1, testFeedback.getHistory().size());
    }

    @Test
    public void getBoard_should_returnSetBoard() {
        //Arrange
        MemberImpl author = new MemberImpl("Maurice");
        FeedbackImpl testFeedback = new FeedbackImpl(1,"Feedback1", "Testdescription",
                5, FeedbackStatus.SCHEDULED, author);
        testFeedback.setBoard("Board1");

        //Assert and Act
        Assert.assertEquals("Board1", testFeedback.getBoard());
    }

    @Test
    public void testToString() {
        //Arrange
        String expected = "Title: Feedback1" + System.lineSeparator() +
                "Item ID: 1" + System.lineSeparator() +
                "Description: Thisistest" + System.lineSeparator() +
                "Activity history: " + System.lineSeparator() +
                System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                System.lineSeparator() +
                "Rating: 5" + System.lineSeparator() +
                "Status: NEW" + System.lineSeparator() +
                "Author: Maurice" + System.lineSeparator();
        MemberImpl author = new MemberImpl("Maurice");
        FeedbackImpl testFeedback = new FeedbackImpl(1,"Feedback1", "Thisistest",
                5, FeedbackStatus.NEW, author);

        //Assert and Act
        Assert.assertEquals(expected, testFeedback.toString());
    }


}
