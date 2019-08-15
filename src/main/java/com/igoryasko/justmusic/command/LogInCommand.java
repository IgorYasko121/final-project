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
 * The {@code LogIn Command} class checks the user's role and poisons to the necessary page.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
public class LogInCommand implements Command {

    private UserService userService;
    private TrackService trackService;

    public LogInCommand(UserService userService, TrackService trackService) {
        this.userService = userService;
        this.trackService = trackService;
    }

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) throws CommandException {
        CommandResult commandResult = new CommandResult();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        UserValidator validator = new UserValidator();
        if (validator.validate(login, password)) {
            User.Role role;
            try {
                role = userService.checkUser(login, password);
            } catch (ServiceException e) {
                log.error("ServiceException :" + e);
                throw new CommandException("Command execute fail" + e);
            }
            if (role.equals(User.Role.USER)) {
                session.setAttribute(AttributeConstant.NUMBER_OF_PAGES, null);
                session.setAttribute(AttributeConstant.ROLE, role);
                session.setAttribute(AttributeConstant.USER, login);
                commandResult.setPagePath(PageConstant.PAGE_HOME);
            } else if (role.equals(User.Role.ADMIN)) {
                session.setAttribute(AttributeConstant.ROLE, role);
                session.setAttribute(AttributeConstant.USER, login);
                commandResult.setPagePath(PageConstant.PAGE_ADMIN);
            } else {
                session.setAttribute(AttributeConstant.ROLE, role);
                commandResult.setPagePath(PageConstant.PAGE_MAIN);
                request.setAttribute(AttributeConstant.ERROR_AUTHORIZE,
                        LanguageManager.getMessage("login.failed", (String) request.getSession().getAttribute(LOCALE)));
            }
        } else {
            request.setAttribute(AttributeConstant.ERROR_AUTHORIZE,
                    LanguageManager.getMessage("login.failed", (String) request.getSession().getAttribute(LOCALE)));
            commandResult.setPagePath(PageConstant.PAGE_MAIN);
        }
        return Optional.of(commandResult);
    }

}
