package com.company.tests.commands.showing;

import com.company.commands.contracts.Command;
import com.company.commands.showing.ShowBoardActivityCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.BoardImpl;
import com.company.models.TeamImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowBoardActivityCommand_Tests {

    private static WimFactory wimFactory;
    private static WimRepository wimRepository;
    private static BoardImpl testBoard;
    private static TeamImpl testTeam;
    private static Command testCommand;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("testTeam");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testBoard = new BoardImpl("testBoard", testTeam.getName());
        testTeam.addBoard(testBoard);
        testCommand = new ShowBoardActivityCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_less_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_InvalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_MemberHasNoActivity() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

}
