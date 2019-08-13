package com.igoryasko.justmusic.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

import static com.igoryasko.justmusic.util.PageConstant.PAGE_MAIN;

/**
 * The class {@code DefendXssAttackFilter} defends from xss javascript attack.
 *
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
@WebFilter(urlPatterns = {"/*"})
public class DefendXssAttackFilter implements Filter {
    public static final String XSS_SCRIPT_TAG = "<script";
    public static final String XSS_SCRIPT_END_TAG = "</script>";
    public static final String JAVASCRIPT = "javascript:";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String text = servletRequest.getParameter(parameterNames.nextElement()).toLowerCase();
            log.warn("Hacking attempt xss attack.");
            if (text.contains(XSS_SCRIPT_TAG) || text.contains(XSS_SCRIPT_END_TAG) ||
                    text.contains(JAVASCRIPT)) {
                servletRequest.getServletContext().getRequestDispatcher(PAGE_MAIN)
                        .forward(servletRequest, servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

