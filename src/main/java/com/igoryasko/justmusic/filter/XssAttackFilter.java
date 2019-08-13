package com.igoryasko.justmusic.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Enumeration;

import static com.igoryasko.justmusic.util.PageConstant.PAGE_MAIN;

/**
 * The class {@code XssAttackFilter} defends from xss javascript attack.
 * @author Igor Yasko on 2019-07-19.
 */
@Log4j2
@WebFilter(urlPatterns = {"/*"})
public class XssAttackFilter implements Filter {
    private static final String XSS_SCRIPT_TAG = "<script";
    private static final String XSS_SCRIPT_END_TAG = "</script>";
    private static final String JAVASCRIPT_PATTERN = "javascript:";
    private static final String VBSCRIPT_PATTERN = "vbscript:";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String text = servletRequest.getParameter(parameterNames.nextElement()).toLowerCase();
            if (text.contains(XSS_SCRIPT_TAG) || text.contains(XSS_SCRIPT_END_TAG) ||
                    text.contains(JAVASCRIPT_PATTERN) || text.contains(VBSCRIPT_PATTERN)) {
                log.warn("Hacking attempt xss attack.");
                servletRequest.getServletContext().getRequestDispatcher(PAGE_MAIN)
                        .forward(servletRequest, servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}

