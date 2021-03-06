package com.suitong.devplatform.common.constant;

/**
 * Created by ddgui on 2017/10/18.
 */
public class DevplatformConstants {

    /**
     * 未知错误
     */
    public static final String ERROR_ERRORMSG = "error.errorMsg";

    /**
     * 用户登录状态常量
     */
    //账号不存在！
    public static final String INVALID_LOGIN = "login.messages.error.invalidLogin";
    //账号不可用！
    public static final String LOGIN_DISABLE = "login.messages.error.loginDisable";
    //账号过期！
    public static final String EXPIRE_LOGIN = "login.messages.error.expireLogin";
    //密码过期！
    public static final String EXPIRE_PASSWORD = "login.messages.error.expirePassword";
    //密码错误
    public static final String ERROR_PASSWORD = "errorPassword";
    //验证码错误
    public static final String ERROR_VERIFYCODE = "login.messages.error.errorVerifyCode";

    /**
     * 角色管理状态常量
     */
    //角色编号重复
    public static final String REPEAT_ROLENO = "devplatformApp.sysRole.messages.error.repeatRoleNo";

    /**
     * 表单管理状态常量
     */
    //表单英文名重复
    public static final String REPEAT_FORMENGLISHNAME = "devplatformApp.sysForm.messages.error.repeatFormEnglishName";

    /**
     * sysDict 数据字典 错误提示
     */
    public static final String SYSDICT_DUPLICATE_GROUP = "devplatformApp.sysDict.error.duplicateGroup";
    public static final String SYSDICT_DUPLICATE_KEY = "devplatformApp.sysDict.error.duplicateKey";
    public static final String SYSDICT_INVALID_GROUP = "devplatformApp.sysDict.error.invalidGroup";
    public static final String SYSDICT_INVALID_KEY = "devplatformApp.sysDict.error.invalidKey";
    public static final String SYSDICT_INVALID_DESC = "devplatformApp.sysDict.error.invalidDesc";

    /**
     * user 用户 错误提示
     */
    public static final String USER_DUPLICATE_LOGIN = "devplatformApp.user.error.duplicateLogin";
    public static final String USER_DUPLICATE_EMAIL = "devplatformApp.user.error.duplicateEmail";
    public static final String USER_INVALID_LOGIN = "devplatformApp.user.error.invalidLogin";
    public static final String USER_INVALID_FIRSTNAME = "devplatformApp.user.error.invalidFirstName";
    public static final String USER_INVALID_EXPIRE_DATE = "devplatformApp.user.error.invalidExpireDate";
    public static final String USER_INVALID_PASSWORD_EXPIRE = "devplatformApp.user.error.invalidPasswordExpire";
    public static final String USER_ERROR_OLD_PASSWORD = "devplatformApp.user.error.errorOldPassword";
}
