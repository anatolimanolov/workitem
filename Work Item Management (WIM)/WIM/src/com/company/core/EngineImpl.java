package com.company.core;

import com.company.commands.contracts.Command;
import com.company.core.contracts.*;
import com.company.core.factories.CommandFactoryImpl;
import com.company.core.factories.WimFactoryImpl;
import com.company.core.providers.CommandParserImpl;
import com.company.core.providers.ConsoleReader;
import com.company.core.providers.ConsoleWriter;

import java.util.List;

public class EngineImpl implements Engine {

    private static final String TERMINATION_COMMAND = "Exit";
    private static final String WELCOME_MESSAGE =
            "-----------------------------------------------" + System.lineSeparator() +
                    " Welcome to Work Item Management v1.0 Console! " + System.lineSeparator() +
                    "-----------------------------------------------" + System.lineSeparator() +
                    System.lineSeparator() +
                    "Enter 'help' to display all available commands" + System.lineSeparator() +
                    "Enter 'exit' to close the application" + System.lineSeparator() +
                    System.lineSeparator();
    private static final String GOODBYE_MESSAGE = "Goodbye!";
    private static final String ENTER_COMMAND_MESSAGE = "Enter command: ";

    private Reader reader;
    private Writer writer;
    private WimFactory wimFactory;
    private WimRepository wimRepository;
    private CommandFactory commandFactory;
    private CommandParser commandParser;

    public EngineImpl() {
        this.reader = new ConsoleReader();
        this.writer = new ConsoleWriter();
        this.wimFactory = new WimFactoryImpl();
        this.wimRepository = new WimRepositoryImpl();
        this.commandFactory = new CommandFactoryImpl();
        this.commandParser = new CommandParserImpl();
    }

    private boolean flag = true;

    public void start() {
        while (true) {
            try {
                if (flag) {
//                    writer.write(WELCOME_MESSAGE);
                    flag = false;
                }
//                writer.write(ENTER_COMMAND_MESSAGE);
                String commandAsString = reader.readLine();
                if (commandAsString.equalsIgnoreCase(TERMINATION_COMMAND)) {
//                    writer.writeLine(GOODBYE_MESSAGE);
                    break;
                }
                processCommand(commandAsString);

            } catch (Exception ex) {
                writer.writeLine(ex.getMessage() != null && !ex.getMessage().isEmpty() ? ex.getMessage() : ex.toString());
            }
        }
    }

    private void processCommand(String commandAsString) {
        if (commandAsString == null || commandAsString.trim().equals("")) {
            throw new IllegalArgumentException("Command cannot be null or empty.");
        }

        String commandName = commandParser.parseCommand(commandAsString);
        Command command = commandFactory.createCommand(commandName, wimFactory, wimRepository);
        List<String> parameters = commandParser.parseParameters(commandAsString);
        String executionResult = command.execute(parameters);
        writer.writeLine(executionResult);
    }
}
