package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.util.PageConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.TRACK_ID;

public class toUpdateTrackCommand implements Command {

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        String id = request.getParameter(TRACK_ID);
        request.setAttribute(TRACK_ID, id);
        commandResult.setPagePath(PageConstant.PAGE_UPDATE_TRACK);
        return Optional.of(commandResult);
    }
}
