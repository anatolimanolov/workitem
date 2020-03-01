package com.company.tests.commands.creation;

import com.company.commands.contracts.Command;
import com.company.commands.creation.CreateTeamCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamCommand_Tests {

    private Command testCommand;
    private WimFactory wimFactory;
    private WimRepository wimRepository;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testCommand = new CreateTeamCommand(wimFactory, wimRepository);
    }

    @Test(expected = RuntimeException.class)
    public void execute_should_trow_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("someText");
        testList.add("someText");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_CrateTeam_when_ValidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("team1");

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, wimRepository.getTeams().size());
    }

    @Test(expected = RuntimeException.class)
    public void execute_should_trow_when_team_with_name_already_exist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("team2");
        testList.add("team2");

        //Act & Assert
        testCommand.execute(testList);
    }
}
