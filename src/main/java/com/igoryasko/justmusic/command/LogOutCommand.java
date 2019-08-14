package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.util.PageConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * The class {@code LogOutCommand} provides an exit from session and poisons to the main page.
 * @author Igor Yasko on 2019-07-19.
 */
public class LogOutCommand implements Command {

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        commandResult.setPagePath(PageConstant.PAGE_MAIN);
        return Optional.of(commandResult);
    }

}
