package com.igoryasko.justmusic.controller;

import com.igoryasko.justmusic.command.ActionFactory;
import com.igoryasko.justmusic.command.Command;
import com.igoryasko.justmusic.command.CommandResult;
import com.igoryasko.justmusic.command.EmptyCommand;
import com.igoryasko.justmusic.connectionpool.ConnectionPool;
import com.igoryasko.justmusic.exception.CommandException;
import com.igoryasko.justmusic.util.AttributeConstant;
import com.igoryasko.justmusic.util.PageConstant;
import com.igoryasko.justmusic.util.ParameterConstant;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@Log4j2
@WebServlet(urlPatterns = {"/controller", "/login", "/update", "/delete"}, loadOnStartup = 1)
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Command> commandOptional =
                ActionFactory.defineCommand(request.getParameter(ParameterConstant.COMMAND));
        Command command = commandOptional.orElse(new EmptyCommand());

        Optional<CommandResult> resultOptional;
        try {
            resultOptional = command.execute(request);
        } catch (CommandException e) {
            log.error(e);
            e.printStackTrace();
            request.setAttribute(AttributeConstant.ERROR, e);
            request.getRequestDispatcher(PageConstant.PAGE_SERVER_ERROR).forward(request, response);
            return;
        }

        if (resultOptional.isPresent()) {
            CommandResult commandResult = resultOptional.get();
            if (commandResult.getRoute().equals(CommandResult.RouteType.REDIRECT)) {
                response.sendRedirect(request.getContextPath() + commandResult.getPagePath());
            } else {
                request.getRequestDispatcher(commandResult.getPagePath()).forward(request, response);
            }
        } else {
            request.getRequestDispatcher(PageConstant.PAGE_ERROR).forward(request, response);
        }
    }

    @Override
    public void init() {
        ConnectionPool.getInstance();
        log.info("Initialize connection pool");
    }

    @Override
    public void destroy() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connectionPool.destroyConnectionPool();
            connectionPool.deregisterDriver();
        } catch (SQLException | InterruptedException e) {
            log.error("ConnectionPool couldn't destroy", e);
        }
    }

}
