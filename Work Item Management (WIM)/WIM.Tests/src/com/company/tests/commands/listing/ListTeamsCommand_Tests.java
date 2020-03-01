package com.company.tests.commands.listing;

import com.company.commands.contracts.Command;
import com.company.commands.listing.ListMembersCommand;
import com.company.commands.listing.ListTeamsCommand;
import com.company.core.WimRepositoryImpl;
import com.company.core.contracts.WimFactory;
import com.company.core.contracts.WimRepository;
import com.company.core.factories.WimFactoryImpl;
import com.company.models.TeamImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTeamsCommand_Tests {

    private static WimFactory wimFactory;
    private static WimRepository wimRepository;
    private static Command testCommand;

    @BeforeClass
    public static void before() {
        wimFactory = new WimFactoryImpl();
        wimRepository = new WimRepositoryImpl();
        testCommand = new ListTeamsCommand(wimRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_should_throw_when_NoRegisteredTeams() {
        //Arrange
        List<String> testList = new ArrayList<>();

        //Act & Assert
        testCommand.execute(testList);
    }

}
