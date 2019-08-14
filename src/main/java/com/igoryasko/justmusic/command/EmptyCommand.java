package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.util.PageConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The {@code EmptyCommand} class defends application from incorrect user's input.
 * @author Igor Yasko on 2019-07-19.
 */
public class EmptyCommand implements Command {

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        commandResult.setPagePath(PageConstant.PAGE_MAIN);
        return Optional.of(commandResult);
    }

}
