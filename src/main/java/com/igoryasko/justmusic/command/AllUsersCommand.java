package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class AllUsersCommand implements Command {

    private static final int RECORDS_PER_PAGE = 5;

    private UserService service;

    public AllUsersCommand(UserService receiver) {
        this.service = receiver;
    }

    /**
     *
     * @param request
     * @return
     * @throws CommandException
     */
    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult router = new CommandResult();
        int currentPage = 1;
        if (request.getParameter(ParameterConstant.CURRENT_PAGE) != null){
            currentPage = Integer.parseInt(request.getParameter(ParameterConstant.CURRENT_PAGE));
        }
        List<User> users;
        int rows;
        try {
            users = service.findLimitUsers(RECORDS_PER_PAGE, currentPage);
            rows = service.getNumberOfRows();
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
        int nOfPages = rows / RECORDS_PER_PAGE;

        if (nOfPages % RECORDS_PER_PAGE > 0) {
            nOfPages++;
        }

        request.setAttribute(AttributeConstant.NUMBER_OF_PAGES, nOfPages);
        request.setAttribute(AttributeConstant.CURRENT_PAGE, currentPage);
        request.setAttribute(AttributeConstant.RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        router.setPagePath(PageConstant.PAGE_ADMIN);
        request.setAttribute(AttributeConstant.USERS, users);
        return Optional.of(router);
    }

}
