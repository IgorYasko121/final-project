package com.igoryasko.justmusic.command;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class ActionFactory {

    public static Optional<Command> defineCommand(String commandName) {
        Optional<Command> current = Optional.empty();
        if (commandName == null) {
            return current;
        }
        CommandType type;
        try {
            type = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            log.warn("Unknown command");
            return current;
        }
        return Optional.of(type.getCommand());
    }

}
