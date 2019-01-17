package com.suitong.devplatform.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    //店铺业主角色编号
    public static final String ROLE_NO_STORE_ADMIN = "2001";

    private AuthoritiesConstants() {
    }
}
