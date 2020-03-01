package com.company.tests.commands.showing;

import com.company.commands.contracts.Command;
import com.company.commands.showing.ShowPersonActivityCommand;
import com.company.commands.showing.ShowTeamActivityCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.MemberImpl;
import com.company.models.TeamImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowTeamActivityCommand_Tests {

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
        testCommand = new ShowTeamActivityCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_InvalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoActivityInTeam(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testTeam.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

}
