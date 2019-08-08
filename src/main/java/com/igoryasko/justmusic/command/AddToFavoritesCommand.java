package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.AttributeConstant.USER;
import static com.igoryasko.justmusic.util.PageConstant.PATH_HOME;
import static com.igoryasko.justmusic.util.ParameterConstant.TRACK_ID;

public class AddToFavoritesCommand implements Command{

    private UserService userService;
    private TrackService trackService;

    public AddToFavoritesCommand(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        long trackId = Long.parseLong(request.getParameter(TRACK_ID));
        String userName = (String) request.getSession().getAttribute(USER);
        try {
            User user = userService.findUserByName(userName);
            if (trackService.createTrackUser(user.getUserId(), trackId)){
                commandResult.setPagePath(PATH_HOME);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new CommandException(e);
        }
//        commandResult.setPagePath(PATH_HOME);
        return Optional.of(commandResult);
    }
}
