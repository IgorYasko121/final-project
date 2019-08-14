package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.Genre;
import com.igoryasko.justmusic.entity.Singer;
import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.language.LanguageManager;
import com.igoryasko.justmusic.service.AdminService;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.validator.TrackValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.*;

/**
 * The {@code UpdateTrackCommand} class updates track.
 * @author Igor Yasko on 2019-11-08.
 */
@Log4j2
public class UpdateTrackCommand implements Command {

    private AdminService adminService;

    public UpdateTrackCommand(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        long trackId = Long.parseLong(request.getParameter(TRACK_ID));
        String trackName = request.getParameter(TRACK_NAME);
        String genreName = request.getParameter(GENRE).toUpperCase();
        String singerName = request.getParameter(SINGER);
        TrackValidator validator = new TrackValidator();
        if (validator.validate(trackName, genreName, singerName)) {
            Track track = new Track();
            track.setTrackId(trackId);
            track.setName(trackName);
            track.setGenre(new Genre(genreName));
            track.setSinger(new Singer(singerName));
            try {
                adminService.updateTrack(track);
                commandResult.setRoute(CommandResult.RouteType.REDIRECT);
                commandResult.setPagePath(PageConstant.PATH_ADMIN);
            } catch (ServiceException e) {
                log.error(e.getMessage());
                throw new CommandException(e);
            }
        } else {
            request.setAttribute("errorLoginPassMessage",
                    LanguageManager.getMessage("registration.failed", (String) request.getSession().getAttribute(LOCALE)));
            commandResult.setPagePath(PageConstant.PAGE_UPDATE_TRACK);
        }
        return Optional.of(commandResult);
    }

}
