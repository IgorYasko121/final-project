package com.igoryasko.justmusic.command;

import com.igoryasko.justmusic.service.AdminService;
import com.igoryasko.justmusic.service.TrackService;
import com.igoryasko.justmusic.service.UserService;

/**
 * The {@code CommandResult} provides command for users.
 * @author Igor Yasko on 2019-07-19.
 */
public enum CommandType {

    SIGN_UP(new SignUpCommand(new UserService(), new TrackService())),
    LOGIN(new LogInCommand(new UserService(), new TrackService())),
    LOGOUT(new LogOutCommand()),
    ALL_USERS(new AllUsersCommand(new UserService())),
    ALL_TRACKS(new AllTracksCommand(new TrackService())),
    LANGUAGE(new LanguageCommand()),
    GUEST(new GuestCommand(new TrackService())),
    TO_UPDATE_ROLE(new toUpdateRoleCommand()),
    UPDATE_USER_ROLE(new UpdateUserRoleCommand(new UserService())),
    TO_UPDATE_USER(new toUpdateUserCommand()),
    TO_UPDATE_TRACK(new toUpdateTrackCommand()),
    UPDATE_USER(new UpdateUserCommand(new UserService(), new TrackService())),
    DELETE_USER(new DeleteUserCommand(new UserService())),
    UPDATE_TRACK(new UpdateTrackCommand(new AdminService())),
    DELETE_TRACK(new DeleteTrackCommand(new TrackService())),
    ADD_TO_FAVORITES(new AddToFavoriteCommand(new UserService(), new TrackService())),
    DELETE_FAVORITE(new DeleteFavoriteCommand(new UserService(), new TrackService())),
    FAVORITE(new FavoriteCommand(new TrackService(), new UserService()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
