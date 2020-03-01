package com.company.tests.commands.creation;

import com.company.commands.contracts.Command;
import com.company.commands.creation.CreatePersonCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreatePersonCommand_Tests {

    private Command testCommand;
    private WimRepository wimRepository;
    private WimFactory wimFactory;

    @Before
    public void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testCommand = new CreatePersonCommand(wimFactory, wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_more_arguments_are_passed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("mmm");
        testList.add("mmm");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_trow_when_invalidInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("mmm");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_should_CratePerson_when_validInput() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("monica");

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, wimRepository.getPeople().size());
    }

    @Test(expected = RuntimeException.class)
    public void execute_should_trow_when_member_with_name_already_exist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("Maurice");
        testList.add("Maurice");

        //Act & Assert
        testCommand.execute(testList);
    }
}