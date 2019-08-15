package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.language.LanguageManager;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.validator.UserValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.*;

/**
 * The class {@code UpdateUserRoleCommand} updates user.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class UpdateUserCommand implements Command {

    private UserService userService;
    private TrackService trackService;

    public UpdateUserCommand(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        UserValidator validator = new UserValidator();
        if (validator.validate(firstName, lastName, email, login, password)) {
            try {
                userService.updateUser(firstName, lastName, login, password, email);
                commandResult.setRoute(CommandResult.RouteType.REDIRECT);
                commandResult.setPagePath(PageConstant.PATH_HOME);
            } catch (ServiceException e) {
                log.error("ServiceException :" + e);
                throw new CommandException("Command execute fail" + e);
            }
        } else {
            request.setAttribute("errorLoginPassMessage",
                    LanguageManager.getMessage("registration.failed", (String) request.getSession().getAttribute(LOCALE)));
            commandResult.setPagePath(PageConstant.PAGE_UPDATE_USER);
        }
        return Optional.of(commandResult);
    }
}
