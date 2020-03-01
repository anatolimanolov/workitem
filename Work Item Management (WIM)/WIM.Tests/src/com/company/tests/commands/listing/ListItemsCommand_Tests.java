package com.company.tests.commands.listing;

import com.company.commands.contracts.Command;
import com.company.commands.listing.ListItemsCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListItemsCommand_Tests {

    private static WimFactory wimFactory;
    private static WimRepository wimRepository;
    private static Command testCommand;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testCommand = new ListItemsCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoRegisteredItems() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("all");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoRegisteredBugs() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bug");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoRegisteredFeedbacks() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("feedback");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoRegisteredStories() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("story");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoRegisteredMember() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_MoreArgumentsArePassed() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("all");
        testList.add("all");
        testList.add("all");
        testList.add("all");

        //Act & Assert
        testCommand.execute(testList);
    }

}
