package com.igoryasko.justmusic.filter;

import com.igoryasko.justmusic.entity.User;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
@WebFilter(urlPatterns = {"/jsp/*", "/admin", "/home"})
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User.Role role = (User.Role) session.getAttribute(ParameterConstant.ROLE);
        String uri = request.getRequestURI();
        if (role == User.Role.USER && uri.contains(PageConstant.PATH_HOME)){
            request.getRequestDispatcher(PageConstant.PATH_HOME).forward(request, response);
            return;
        }
        if (role == User.Role.ADMIN && uri.contains(PageConstant.PATH_ADMIN)){
            request.getRequestDispatcher(PageConstant.PATH_ADMIN).forward(request, response);
            return;
        }

        if (!checkAccess(request, role)) {
            log.warn("non-authorized access blocked");
            response.sendRedirect(request.getContextPath());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAccess(HttpServletRequest request, User.Role role) {
        boolean result = true;
        String uri = request.getRequestURI();

        if (role == null && uri.contains(PageConstant.PATH_ADMIN) || uri.contains(PageConstant.PATH_HOME)) {
            result = false;
        }
        if(role != null && role != User.Role.ADMIN  && uri.contains(PageConstant.PATH_ADMIN)) {
            result = false;
        }
        return result;
    }

}
