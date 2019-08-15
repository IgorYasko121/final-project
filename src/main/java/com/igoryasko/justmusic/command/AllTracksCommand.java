package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.Track;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.*;

/**
 * The {@code AllUsersCommand} provides a function to gets all tracks and pagination.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class AllTracksCommand implements Command {

    private TrackService service;

    public AllTracksCommand(TrackService service) {
        this.service = service;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        int currentPage = 1;
        if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));
        }
        String page = request.getParameter(PAGE);
        List<Track> tracks;
        int rows;
        try {
            tracks = service.findLimitTracks(RECORDS_PER_PAGE, currentPage);
            rows = service.getNumberOfRows();
        } catch (ServiceException e) {
            log.error("ServiceException :" + e);
            throw new CommandException("Command execute fail" + e);
        }
        int nOfPages = rows / RECORDS_PER_PAGE;

        if (nOfPages % RECORDS_PER_PAGE > 0) {
            nOfPages++;
        }

        if (page != null && page.equals(ParameterConstant.PAGE_HOME)) {
            commandResult.setPagePath(PageConstant.PATH_HOME);
            request.getSession().setAttribute(AttributeConstant.TRACKS, tracks);
            request.getSession().setAttribute(AttributeConstant.NUMBER_OF_PAGES, nOfPages);
            request.getSession().setAttribute(AttributeConstant.CURRENT_PAGE, currentPage);
            request.getSession().setAttribute(AttributeConstant.RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        } else {
            commandResult.setPagePath(PageConstant.PAGE_ADMIN);
            request.setAttribute(AttributeConstant.TRACKS, tracks);
            request.setAttribute(AttributeConstant.NUMBER_OF_PAGES, nOfPages);
            request.setAttribute(AttributeConstant.CURRENT_PAGE, currentPage);
            request.setAttribute(AttributeConstant.RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        }
        return Optional.of(commandResult);
    }

}
