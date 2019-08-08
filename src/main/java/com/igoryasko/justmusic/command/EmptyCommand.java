package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.util.PageConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EmptyCommand implements Command {

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        commandResult.setPagePath(PageConstant.PAGE_MAIN);
        return Optional.of(commandResult);
    }

}
