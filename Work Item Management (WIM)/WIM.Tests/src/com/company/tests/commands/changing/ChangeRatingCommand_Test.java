package com.company.tests.commands.changing;

import com.company.commands.changing.ChangeRatingCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Feedback;
import com.company.models.contracts.WorkItem;
import com.company.models.workitems.FeedbackImpl;
import com.company.models.workitems.enums.FeedbackStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeRatingCommand_Test {
    private Command testCommand;
    private FeedbackImpl testItem;
    private WimRepository wimRepository;
    private MemberImpl author;

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        testCommand = new ChangeRatingCommand(wimRepository);
        author = new MemberImpl("Maurice");
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
    public void execute_should_changeRating_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Feedback1");
        testList.add("1");
        testList.add("Maurice");
        testList.add("Team1");
        testList.add("Board1");

        testItem = new FeedbackImpl(1,"Feedback1", "Testdescription", 5,
                FeedbackStatus.SCHEDULED, author);
        TeamImpl team = new TeamImpl("Team1");
        wimRepository.addTeam("Team1", team);
        BoardImpl board = new BoardImpl("Board1", "Team1");
        wimRepository.getTeams().get(team.getName()).addBoard(board);
        testItem.setBoard("Board1");
        team.addMember(author);
        wimRepository.getTeams().get(team.getName()).getBoards().get(board.getName()).addFeedback(testItem.getTitle(), testItem);
        wimRepository.addPerson("Maurice", author);

        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, ((Feedback) testItem).getRating());
    }
}
