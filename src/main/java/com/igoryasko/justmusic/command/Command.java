package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface Command {
    Optional<CommandResult> execute(HttpServletRequest request) throws CommandException;
}
