package com.company.tests.commands.changing;

import com.company.commands.changing.ChangeSizeCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimRepository;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.*;
import com.company.models.workitems.StoryImpl;
import com.company.models.workitems.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeSizeCoomand_Test {
    private Command testCommand;
    private StoryImpl testItem;
    private WimRepository wimRepository;
    private MemberImpl member = new MemberImpl("Maurice");

    @Before
    public void before() {
        wimRepository = new WimRepositoryImpl();
        testCommand = new ChangeSizeCommand(wimRepository);
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
    public void execute_should_changeSize_when_inputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Storytest1");
        testList.add("small");
        testList.add("Maurice");
        testList.add("Team1");
        testList.add("Board1");

        testItem = new StoryImpl(1, "Storytest1", "TestDescriptionforTest",
                Priority.HIGH, Size.MEDIUM, StoryStatus.NOTDONE);
        TeamImpl team = new TeamImpl("Team1");
        wimRepository.addTeam("Team1", team);
        BoardImpl board = new BoardImpl("Board1", "Team1");
        wimRepository.getTeams().get(team.getName()).addBoard(board);
        wimRepository.getTeams().get(team.getName()).getBoards().get(board.getName()).addStory("Storytest1", testItem);
        testItem.setBoard("Board1");
        wimRepository.addPerson("Maurice", member);
        board.addStory(testItem.getTitle(), testItem);


        // Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(Size.valueOf("SMALL"), ((Story) testItem).getSize());
    }
}
