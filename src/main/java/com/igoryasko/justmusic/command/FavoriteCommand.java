package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The {@code FavoriteCommand} class provides favorite music for users.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class FavoriteCommand implements Command {

    private TrackService service;
    private UserService userService;

    public FavoriteCommand(TrackService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        String userName = (String) request.getSession().getAttribute(AttributeConstant.USER);
        List<Track> tracks;
        try {
            User user = userService.findUserByName(userName);
            tracks = service.findFavoriteTracks(user.getUserId());
        } catch (ServiceException e) {
            log.error("ServiceException :" + e);
            throw new CommandException("Command execute fail" + e);
        }
        commandResult.setPagePath(PageConstant.PATH_HOME);
        request.getSession().setAttribute(AttributeConstant.TRACKS, tracks);
        request.getSession().setAttribute(AttributeConstant.NUMBER_OF_PAGES, null);
        return Optional.of(commandResult);
    }
}
