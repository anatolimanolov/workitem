package com.company.commands;

import com.company.core.contracts.Reader;
import com.company.core.contracts.Writer;
import com.company.core.providers.ConsoleReader;
import com.company.core.providers.ConsoleWriter;

import java.util.List;

public class LineInterface {

    private static final String STEPS_TO_REPRODUCE_TERMINATION_COMMAND = "end";
    private static Reader reader = new ConsoleReader();
    private static Writer writer = new ConsoleWriter();

    public static void getParametersFromLineInterface(String[] lineInterface, List<String> parameters) {
        for (String s : lineInterface) {
            writer.write(s);
            parameters.add(reader.readLine());
        }
    }

    public static void getListItemParametersLineInterface(String[] lineInterface, List<String> parameters) {
        String input;
        for (String s : lineInterface) {
            writer.write(s);
            input = reader.readLine();
            if (input.equalsIgnoreCase("none")) {
                continue;
            }
            parameters.add(input);
        }
    }

    public static void getBoardParametersFromLineInterface(String[] lineInterface,
                                                           List<String> parameters,
                                                           List<String> stepsToReproduce) {

        for (int i = 0; i < lineInterface.length; i++) {
            boolean flag = false;
            writer.write(lineInterface[i]);
            if (i == 2) { // 2 -> Steps to reproduce
                int stepsCounter = 1;
                while (true) {
                    writer.write(String.format("Enter step %d: ", stepsCounter));
                    String inputSteps;
                    inputSteps = reader.readLine();
                    String input =(String.valueOf(stepsCounter)).concat(".").concat(inputSteps);
                    if (inputSteps.equalsIgnoreCase(STEPS_TO_REPRODUCE_TERMINATION_COMMAND)) {
                        flag = true;
                        break;
                    }
                    stepsCounter++;
                    stepsToReproduce.add(input);
                }
            }
            if (flag) {
                continue;
            }
            parameters.add(reader.readLine());
        }
    }

}
