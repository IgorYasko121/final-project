package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * The {@code Guest Command} class poisons the user to the guest page.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class GuestCommand implements Command {

    private TrackService service;

    GuestCommand(TrackService service) {
        this.service = service;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        HttpSession session = request.getSession();
        List<Track> tracks;
        try {
            tracks = service.findTopSixTracks();
        } catch (ServiceException e) {
            log.info(e);
            throw new CommandException(e);
        }
        commandResult.setPagePath(PageConstant.PAGE_GUEST);
        session.setAttribute(AttributeConstant.ROLE, User.Role.GUEST);
        session.setAttribute(AttributeConstant.TRACKS, tracks);
        return Optional.of(commandResult);
    }

}
