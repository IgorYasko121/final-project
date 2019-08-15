package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The {@code DeleteTrackCommand} class deletes user from database.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class DeleteUserCommand implements Command {

    private UserService service;

    public DeleteUserCommand(UserService receiver) {
        this.service = receiver;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult router = new CommandResult();
        long userId = Long.parseLong(request.getParameter(ParameterConstant.USER_ID));
        try {
            service.deleteUser(userId);
        } catch (ServiceException e) {
            log.error("ServiceException :" + e);
            throw new CommandException("Command execute fail" + e);
        }
        router.setRoute(CommandResult.RouteType.REDIRECT);
        router.setPagePath(PageConstant.PATH_ADMIN);
        return Optional.of(router);
    }

}
