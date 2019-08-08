package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.util.PageConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.USER_ID;

/**
 * The {@code toUpdateRoleCommand} class redirects to the update user page.
 * @author Igor Yasko on 2019-07-19.
 */
public class toUpdateUserCommand implements Command {
    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        String id = request.getParameter(USER_ID);
        request.setAttribute(USER_ID, id);
        commandResult.setPagePath(PageConstant.PAGE_UPDATE_USER);
        return Optional.of(commandResult);
    }

}
