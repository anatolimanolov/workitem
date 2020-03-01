package com.company.core.contracts;

import com.company.commands.contracts.Command;

public interface CommandFactory {
    Command createCommand(String commandTypeAsString, WimFactory wimFactory, WimRepository wimRepository);
}