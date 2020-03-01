package com.company.core.providers;

import com.company.core.contracts.Writer;

public class ConsoleWriter implements Writer {

    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeLine(String message) {
        System.out.println(message);
    }
}
