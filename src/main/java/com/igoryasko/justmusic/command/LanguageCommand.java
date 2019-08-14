package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.util.PageConstant;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.igoryasko.justmusic.util.ParameterConstant.*;

/**
 * The class {@code LanguageCommand} changes language between Russian English and Belarusian.
 * @author Igor Yasko on 2019-07-19.
 */
public class LanguageCommand implements Command {

    @Override
    public Optional<CommandResult> execute(HttpServletRequest request) {
        CommandResult commandResult = new CommandResult();
        String language = request.getParameter(LOCALE);
        String page = request.getParameter(PAGE);
        request.getSession().setAttribute(LOCALE, language);
        commandResult.setPagePath(selectPage(page));
        return Optional.of(commandResult);
    }

    private String selectPage(String page) {
        String result;
        if (page.equalsIgnoreCase(PAGE_MAIN)) {
            result = PageConstant.PAGE_MAIN;
        } else if (page.equalsIgnoreCase(PAGE_ADMIN)) {
            result = PageConstant.PAGE_ADMIN;
        } else if (page.equalsIgnoreCase(PAGE_GUEST)) {
            result = PageConstant.PAGE_GUEST;
        } else {
            result = PageConstant.PAGE_HOME;
        }
        return result;
    }

}




