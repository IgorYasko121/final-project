package com.igoryasko.justmusic.language;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class {@code LanguageManager} changes language between Russian English and Belarusian.
 * @author Igor Yasko on 2019-07-19.
 */
public class LanguageManager {

    private static final String CONTENT = "pagecontent";
    private static final String DEFAULT_LOCALE = "ru_RU";

    private LanguageManager() { }

    /**
     * @param key is a marker to find attribute.
     * @param locale is a current language.
     * @return String locale.
     */
    public static String getMessage(String key, String locale) {
        locale = locale != null ? locale : DEFAULT_LOCALE;
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT, formatLocale(locale));
        return resourceBundle.getString(key);
    }

    private static Locale formatLocale(String locale) {
        switch (locale) {
            case "en_US":return new Locale("en", "US");
            case "ru_BY":return new Locale("ru", "BY");
            default:return new Locale("ru", "RU");
        }
    }

}
