package com.company.tests.commands;

import com.company.commands.AddPersonToTeamCommand;
import com.company.commands.contracts.Command;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddPersonToTeamCommand_Tests {

    private static WimFactory wimFactory;
    private static WimRepository wimRepository;
    private static TeamImpl testTeam;
    private static MemberImpl testMember;
    private static Command testCommand;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("testTeam");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testMember = new MemberImpl("Maurice");
        wimRepository.addPerson(testMember.getName(), testMember);
        testCommand = new AddPersonToTeamCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_LessArgumentsPassed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_MoreArgumentsPassed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_InvalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_AddPersonToTeam_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());
        testList.add(testTeam.getName());

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1,testTeam.getMembers().size());
    }
}
