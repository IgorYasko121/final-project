package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.language.LanguageManager;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.validator.UserValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.*;

/**
 * The class {@code UpdateUserRoleCommand} updates user role.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class UpdateUserRoleCommand implements Command {

    private UserService service;

    public UpdateUserRoleCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        String role = request.getParameter(ROLE).toUpperCase();
        long userId = Long.parseLong(request.getParameter(USER_ID));
        UserValidator validator = new UserValidator();
        if (validator.validateRole(role) && userId > 0) {
            try {
                if (service.updateRoleUser(role, userId)) {
                    commandResult.setPagePath(PageConstant.PATH_ADMIN);
                    commandResult.setRoute(CommandResult.RouteType.REDIRECT);
                } else {
                    request.setAttribute("errorLoginPassMessage",
                            LanguageManager.getMessage("update.role", (String) request.getSession().getAttribute(LOCALE)));
                    commandResult.setPagePath(PageConstant.PATH_ADMIN);
                }
            } catch (ServiceException e) {
                log.error("ServiceException :" + e);
                throw new CommandException("Command execute fail" + e);
            }
        } else {
            request.setAttribute("errorLoginPassMessage",
                    LanguageManager.getMessage("registration.failed", (String) request.getSession().getAttribute(LOCALE)));
            commandResult.setPagePath(PageConstant.PAGE_ADMIN);
        }
        return Optional.of(commandResult);
    }

}
