package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.language.LanguageManager;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.AttributeConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.AttributeConstant.USER;
import static com.igoryasko.justmusic.util.PageConstant.PATH_HOME;
import static com.igoryasko.justmusic.util.ParameterConstant.LOCALE;
import static com.igoryasko.justmusic.util.ParameterConstant.TRACK_ID;

/**
 * The {@code AddToFavoriteCommand} add track to the favorite list.
 *
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class AddToFavoriteCommand implements Command {

    private UserService userService;
    private TrackService trackService;

    public AddToFavoriteCommand(UserService userService, TrackService trackService) {
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
            if (trackService.addToFavorite(user.getUserId(), trackId)) {
                commandResult.setPagePath(PATH_HOME);
            } else {
                request.setAttribute(AttributeConstant.ERROR_MESSAGE,
                        LanguageManager.getMessage("label.added", (String) request.getSession().getAttribute(LOCALE)));
                commandResult.setPagePath(PATH_HOME);
            }
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
        return Optional.of(commandResult);
    }
}
