package com.company.tests.commands.creation;

import com.company.commands.contracts.Command;
import com.company.commands.creation.CreateFeedbackCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.contracts.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateFeedbackCommand_Tests {
    private WimRepository wimRepository;
    private WimFactory wimFactory;
    private Command testCommand;
    private TeamImpl testTeam;
    private MemberImpl testMember;
    private BoardImpl testBoard;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("team1");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testMember = new MemberImpl("Maurice");
        wimRepository.addPerson(testMember.getName(), testMember);
        testTeam.addMember(testMember);
        testBoard = new BoardImpl("testBoard", testTeam.getName());
        wimRepository.getTeams().get("team1").addBoard(testBoard);
        testCommand = new CreateFeedbackCommand(wimFactory, wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_more_arguments_are_passed() {

        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("5");
        testList.add("new");
        testList.add("board1");
        testList.add(testMember.getName());
        testList.add("sdfghk");
        testList.add("sdfghk");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_less_arguments_are_passed() {

        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("title");
        testList.add("description");
        testList.add("5");
        testList.add("new");
        testList.add("board1");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = RuntimeException.class)
    public void execute_should_throw_when_InvalidTitle() {

        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("aaaaa");
        testList.add("description");
        testList.add("5");
        testList.add("new");
        testList.add("board1");
        testList.add(testMember.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_CreateFeedback_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("aaaaaaaaaaa");
        testList.add("description");
        testList.add("5");
        testList.add("new");
        testList.add(testBoard.getName());
        testList.add(testTeam.getName());
        testList.add(testMember.getName());

        //Act
        testCommand.execute(testList);

        //Arrange
        Assert.assertEquals(1, wimRepository.getTeams().get(testTeam.getName()).getBoards()
                .get(testBoard.getName()).getFeedbacks().size());
    }
}
