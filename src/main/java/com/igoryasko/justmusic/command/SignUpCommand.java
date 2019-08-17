package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.exception.ServiceException;
import com.igoryasko.justmusic.language.LanguageManager;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.service.UserService;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.validator.UserValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.*;

/**
 * The class {@code SignUpCommand} .
 *
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class SignUpCommand implements Command {

    private UserService userService;
    private TrackService trackService;

    public SignUpCommand(UserService userService, TrackService trackService) {
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
                userService.registerUser(firstName, lastName, login, password, email);
                HttpSession session = request.getSession();
                session.setAttribute(AttributeConstant.NUMBER_OF_PAGES, null);
                session.setAttribute(AttributeConstant.ROLE, User.Role.USER);
                session.setAttribute(AttributeConstant.USER, login);
                commandResult.setPagePath(PageConstant.PATH_HOME);
                commandResult.setRoute(CommandResult.RouteType.REDIRECT);
            } catch (ServiceException e) {
                if (e.getMessage().contains("duplicate key value violates unique constraint \"users_login_key\"")) {
                    request.setAttribute(AttributeConstant.ERROR_LOGIN_PASSWORD,
                            LanguageManager.getMessage("login.exist", (String) request.getSession().getAttribute(LOCALE)));
                    commandResult.setPagePath(PageConstant.PAGE_MAIN);
                } else {
                    log.error("ServiceException :" + e);
                    throw new CommandException("Command execute fail " + e);
                }
            }
        } else {
            request.setAttribute(AttributeConstant.ERROR_LOGIN_PASSWORD,
                    LanguageManager.getMessage("registration.failed", (String) request.getSession().getAttribute(LOCALE)));
            commandResult.setPagePath(PageConstant.PAGE_MAIN);
        }
        return Optional.of(commandResult);
    }

}
