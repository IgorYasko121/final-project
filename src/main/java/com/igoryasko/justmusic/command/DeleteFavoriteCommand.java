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

import static com.igoryasko.justmusic.util.AttributeConstant.USER;
import static com.igoryasko.justmusic.util.ParameterConstant.TRACK_ID;

/**
 * The {@code DeleteFavoriteCommand} class deletes track from user's favorites tracks.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class DeleteFavoriteCommand implements Command {

    private UserService userService;
    private TrackService trackService;

    public DeleteFavoriteCommand(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        long trackId = Long.parseLong(request.getParameter(TRACK_ID));
        String userName = (String) request.getSession().getAttribute(USER);
        List<Track> tracks;
        try {
            User user = userService.findUserByName(userName);
            trackService.deleteFromFavorite(trackId, user.getUserId());
            tracks = trackService.findFavoriteTracks(user.getUserId());
        } catch (ServiceException e) {
            log.error("ServiceException :" + e);
            throw new CommandException("Command execute fail" + e);
        }
        request.getSession().setAttribute(AttributeConstant.TRACKS, tracks);
        commandResult.setPagePath(PageConstant.PATH_HOME);
        commandResult.setRoute(CommandResult.RouteType.REDIRECT);
        return Optional.of(commandResult);
    }
}
