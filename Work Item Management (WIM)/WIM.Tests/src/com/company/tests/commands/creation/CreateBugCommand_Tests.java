package com.company.tests.commands.creation;

import com.company.commands.contracts.Command;
import com.company.commands.creation.CreateBugCommand;
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

public class CreateBugCommand_Tests {

    private WimRepository wimRepository;
    private WimFactory wimFactory;
    private Command testCommand;
    private TeamImpl testTeam;
    private BoardImpl testBoard;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("testTeam");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testBoard = new BoardImpl("testBoard", testTeam.getName());
        wimRepository.getTeams().get(testTeam.getName()).addBoard(testBoard);
        testCommand = new CreateBugCommand(wimFactory, wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("low");
        testList.add("MINOR");
        testList.add("fixed");
        testList.add(testBoard.getName());
        testList.add("stepsToReproduce");
        testList.add("sdfghj");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_less_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("low");
        testList.add("MINOR");
        testList.add("fixed");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_InvalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("aaa");
        testList.add("description");
        testList.add("low");
        testList.add("MINOR");
        testList.add("fixed");
        testList.add(testBoard.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_CrateBug_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("aaaaaaaaaaa");
        testList.add("description");
        testList.add("low");
        testList.add("MINOR");
        testList.add("fixed");
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
//        testList.add("steps");

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, wimRepository.getTeams().get(testTeam.getName()).getBoards().get(testBoard.getName())
                .getBugs().size());
    }
}
