package com.igoryasko.justmusic.filter;

import com.igoryasko.justmusic.command.CommandType;
import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.util.ParameterConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.igoryasko.justmusic.command.CommandType.*;

/**
 * The class {@code RoleCommandFilter} defends from incorrect input user's role command.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
@WebFilter(urlPatterns = {"/controller", "/update"})
public class RoleCommandFilter implements Filter {

    private static final Set<CommandType> COMMAND_FOR_GUEST = Set.of(GUEST, LANGUAGE, SIGN_UP, LOGIN);
    private static final Set<CommandType> COMMAND_FOR_ADMIN = Set.of(GUEST, LANGUAGE, ALL_USERS, ALL_TRACKS, TO_UPDATE_ROLE, TO_UPDATE_TRACK,
            UPDATE_USER_ROLE, UPDATE_TRACK, DELETE_TRACK, DELETE_USER, LOGOUT);
    private static final Set<CommandType> COMMAND_FOR_USER = Set.of(GUEST, LANGUAGE, ALL_TRACKS, TO_UPDATE_USER, UPDATE_USER, ADD_TO_FAVORITES,
            DELETE_FAVORITE, FAVORITE, LOGOUT);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User.Role role = (User.Role) request.getSession().getAttribute(ParameterConstant.ROLE);
        String commandString = request.getParameter(ParameterConstant.COMMAND);
        CommandType command;

        if (commandString == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        try {
            command = CommandType.valueOf(commandString.toUpperCase());
        } catch (IllegalArgumentException e){
            log.warn("Command does't exist");
            response.sendRedirect(request.getContextPath());
            return;
        }

        if (role == null && COMMAND_FOR_GUEST.contains(command) || role == User.Role.GUEST && COMMAND_FOR_GUEST.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (role == User.Role.ADMIN && COMMAND_FOR_ADMIN.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (role == User.Role.USER && COMMAND_FOR_USER.contains(command)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect(request.getContextPath());
        }

    }


}
