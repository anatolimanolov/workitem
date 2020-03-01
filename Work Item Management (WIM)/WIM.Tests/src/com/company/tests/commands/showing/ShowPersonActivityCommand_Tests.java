package com.company.tests.commands.showing;

import com.company.commands.contracts.Command;
import com.company.commands.showing.ShowPersonActivityCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.MemberImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowPersonActivityCommand_Tests {

    private static WimFactory wimFactory;
    private static WimRepository wimRepository;
    private static MemberImpl testMember;
    private static Command testCommand;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testMember = new MemberImpl("Maurice");
        testCommand = new ShowPersonActivityCommand(wimRepository);
    }

    @Test (expected = IllegalArgumentException.class)
    public void execute_should_throw_when_InvalidInput(){
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
        testList.add(testMember.getName());
        testList.add("bla");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test (expected = IllegalArgumentException.class)
    public void execute_should_throw_when_MemberHasNoActivity(){
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add(testMember.getName());

        //Act & Assert
        testCommand.execute(testList);
    }

}
