package com.company.tests.commands.listing;

import com.company.commands.contracts.Command;
import com.company.commands.listing.ListMembersCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.TeamImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListMembersCommand_Tests {

    private static WimFactory wimFactory;
    private static WimRepository wimRepository;
    private static TeamImpl testTeam;
    private static Command testCommand;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testTeam = new TeamImpl("testTeam");
        wimRepository.addTeam(testTeam.getName(), testTeam);
        testCommand = new ListMembersCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_more_arguments_are_passed(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());
        testList.add(testTeam.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_TeamHasNoMembers(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_InvalidInput(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }
}
