package com.igoryasko.justmusic.filter;

import com.igoryasko.justmusic.command.CommandType;
import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.util.ParameterConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.igoryasko.justmusic.command.CommandType.GUEST;
import static com.igoryasko.justmusic.command.CommandType.LANGUAGE;

@WebFilter(urlPatterns = {"/controller"})
public class CommandFilter implements Filter {

    private static final Set<CommandType> COMMAND_FOR_GUEST = Set.of(GUEST, LANGUAGE);
    private static final Set<CommandType> COMMAND_FOR_ADMIN = Set.of(GUEST, LANGUAGE);
    private static final Set<CommandType> COMMAND_FOR_USER = Set.of(GUEST, LANGUAGE);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        User.Role role = (User.Role) request.getSession().getAttribute(ParameterConstant.ROLE);
        String commandString = request.getParameter(ParameterConstant.COMMAND);
        CommandType command = null;
        if (commandString == null) {
            response.sendRedirect(request.getContextPath());
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
//            command = CommandType.valueOf(commandString);

//        if (role == null || role == User.Role.GUEST && COMMAND_FOR_GUEST.contains(command)) {
//            filterChain.doFilter(request, response);
//        } else {
//            response.sendRedirect(request.getContextPath());
//        }

//        if(role.equals(admin) || COMMAND_FOR_GUEST.contains(command)){
//            chain.doFilter(req, resp);
//        } else {
//            httpResponse.sendRedirect(httpRequest.getContextPath() + CommandPathConstant.PATH_PAGE_ERROR);
//        }
    }

}
