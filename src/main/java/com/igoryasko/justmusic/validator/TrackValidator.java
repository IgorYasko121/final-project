package com.igoryasko.justmusic.validator;

/**
 * The class {@code UserValidator} validates track's parameter input.
 * @author Igor Yasko on 2019-07-19.
 */
public class TrackValidator {

    private static final String NAME_REGEX_PATTERN = "^[a-zA-Z0-9 -]{3,30}$";
    private static final String GENRE_REGEX_PATTERN = "^[A-Z]{3,20}";

    public boolean validate(String name, String genre, String singer) {
        return !name.isBlank()
                && !genre.isBlank()
                && !singer.isBlank()
                && name.matches(NAME_REGEX_PATTERN)
                && genre.matches(GENRE_REGEX_PATTERN)
                && singer.matches(NAME_REGEX_PATTERN);
    }

}