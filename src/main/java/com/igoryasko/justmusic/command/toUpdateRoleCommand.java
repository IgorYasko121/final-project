package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The {@code toUpdateRoleCommand} class redirects to the update role page.
 * @author Igor Yasko on 2019-07-19.
 */
public class toUpdateRoleCommand implements Command {

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        String id = request.getParameter(ParameterConstant.USER_ID);
        request.setAttribute(ParameterConstant.USER_ID, id);
        commandResult.setPagePath(PageConstant.PAGE_UPDATE_USER_ROLE);
        return Optional.of(commandResult);
    }
}
