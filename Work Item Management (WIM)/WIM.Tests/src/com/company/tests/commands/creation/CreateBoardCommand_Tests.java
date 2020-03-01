package com.company.tests.commands.creation;

import com.company.commands.contracts.Command;
import com.company.commands.creation.CreateBoardCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.TeamImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateBoardCommand_Tests {

    private WimRepository wimRepository;
    private WimFactory wimFactory;
    private Command testCommand;
    private TeamImpl testTeam;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("team1");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testCommand = new CreateBoardCommand(wimFactory, wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bbb");
        testList.add("nnn");
        testList.add("mmm");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_less_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bbb");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_CrateBoard_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("board1");
        testList.add(testTeam.getName());

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testTeam.getBoards().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_TeamToAddBoardTo_DoesNotExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("board1");
        testList.add("ewrytty");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_BoardNameExistInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("board1");
        testList.add(testTeam.getName());

        //Act & Assert
        testCommand.execute(testList);
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_invalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("asd");
        testList.add(testTeam.getName());

        //Act & Assert
        testCommand.execute(testList);
    }
}
