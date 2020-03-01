package com.company.tests.commands.creation;

import com.company.commands.contracts.Command;
import com.company.commands.creation.CreateStoryCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.BoardImpl;
import com.company.models.TeamImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateStoryCommand_Tests {
    private WimRepository wimRepository;
    private WimFactory wimFactory;
    private Command testCommand;
    private TeamImpl testTeam;
    private BoardImpl testBoard;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("team1");
        testBoard = new BoardImpl("testBoard", testTeam.getName());
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testTeam.addBoard(testBoard);
        testCommand = new CreateStoryCommand(wimFactory, wimRepository);
    }

    @Test(expected = RuntimeException.class)
    public void execute_should_throw_when_more_arguments_are_passed() {

        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("low");
        testList.add("large");
        testList.add("NOTDONE");
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add("asdf");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = RuntimeException.class)
    public void execute_should_throw_when_less_arguments_are_passed() {

        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("LOW");
        testList.add("large");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_CreateStory_when_ValidInput() {

        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("low");
        testList.add("large");
        testList.add("NOTDONE");
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, wimRepository.getTeams().get(testTeam.getName()).getBoards()
                .get(testBoard.getName()).getStories().size());
    }


}
