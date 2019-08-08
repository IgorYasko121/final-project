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

@Log4j2
public class AllTracksCommand implements Command {

    public static final int RECORDS_PER_PAGE = 5;

    private TrackService service;

    public AllTracksCommand(TrackService service) {
        this.service = service;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        int currentPage = 1;
        if (request.getParameter(ParameterConstant.CURRENT_PAGE) != null){
            currentPage = Integer.parseInt(request.getParameter(ParameterConstant.CURRENT_PAGE));
        }
        String page = request.getParameter(ParameterConstant.PAGE);
        List<Track> tracks;
        int rows;
        try {
            tracks = service.findLimitTracks(RECORDS_PER_PAGE, currentPage);
            rows = service.getNumberOfRows();
        } catch (ServiceException e) {
            log.info(e);
            throw new CommandException(e);
        }
        int nOfPages = rows / RECORDS_PER_PAGE;

        if (nOfPages % RECORDS_PER_PAGE > 0) {
            nOfPages++;
        }

        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("RECORDS_PER_PAGE", RECORDS_PER_PAGE);

        if (page != null && page.equals(ParameterConstant.PAGE_HOME)) {
            commandResult.setPagePath(PageConstant.PATH_HOME);
            request.getSession().setAttribute(AttributeConstant.TRACKS, tracks);
        } else {
            commandResult.setPagePath(PageConstant.PAGE_ADMIN);
            request.setAttribute(AttributeConstant.TRACKS, tracks);
        }
        return Optional.of(commandResult);
    }

}
