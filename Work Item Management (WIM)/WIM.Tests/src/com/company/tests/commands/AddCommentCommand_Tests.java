package com.company.tests.commands;

import com.company.commands.AddCommentCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.BoardImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import com.company.models.workitems.StoryImpl;
import com.company.models.workitems.enums.Priority;
import com.company.models.workitems.enums.Size;
import com.company.models.workitems.enums.StoryStatus;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddCommentCommand_Tests {
   private static WimFactory wimFactory;
   private static WimRepository wimRepository;
   private static Command testCommand;
   private static TeamImpl testTeam;
   private static BoardImpl testBoard;
   private static MemberImpl testMember;
   private static StoryImpl testStory;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("testTeam");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testBoard = new BoardImpl("testBoard", testTeam.getName());
        testTeam.addBoard(testBoard);
        testMember = new MemberImpl("Maurice");
        wimRepository.addPerson(testMember.getName(), testMember);
        testTeam.addMember(testMember);
        testStory = new StoryImpl(1, "testStory", "this is description", Priority.LOW, Size.MEDIUM, StoryStatus.NOTDONE);
        wimRepository.getTeams().get(testTeam.getName()).getBoards().get(testBoard.getName())
                .addStory(testStory.getTitle(), testStory);
        testCommand = new AddCommentCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_InvalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add("bla");
        testList.add("This is comment.");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_AddComment_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());
        testList.add(testBoard.getName());
        testList.add(testStory.getTitle());
        testList.add("This is comment.");

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testStory.getComments().size());
    }
}
