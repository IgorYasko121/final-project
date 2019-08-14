package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The {@code Command} provides one method for executing a command.
 * @author Igor Yasko on 2019-07-19.
 */
public interface Command {
    Optional<CommandResult> execute(HttpServletRequest request) throws CommandException;
}
