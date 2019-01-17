package com.chengma.devplatform.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String DEFAULT_PASSWORD = "111111";
    public static final String STATUS_ENABLE = "启用";
    public static final String STATUS_DISABLE = "禁用";
    public static final String CHAR_SET = "UTF-8";

    private Constants() {
    }
}
