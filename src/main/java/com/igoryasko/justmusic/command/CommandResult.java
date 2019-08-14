package com.igoryasko.justmusic.command;

import lombok.Data;

import java.util.HashMap;

/**
 * The {@code CommandResult} provides command for users.
 * @author Igor Yasko on 2019-07-19.
 */
@Data
public class CommandResult {

    public enum RouteType {
        FORWARD, REDIRECT
    }

    private String pagePath;
    private RouteType route = RouteType.FORWARD;

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public RouteType getRoute() {
        return route;
    }

    public void setRoute(RouteType route) {
        if (route != null) {
            this.route = route;
        }
    }

}
