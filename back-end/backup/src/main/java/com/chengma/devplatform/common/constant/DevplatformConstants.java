package com.chengma.devplatform.common.constant;

/**
 * Created by ddgui on 2017/10/18.
 */
public class DevplatformConstants {

    /*
     * 超级管理员ID
     */
    public static final String ADMIN_ROLE_ID = "3";

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
    public static final String USER_INVALID_MOBILE = "devplatformApp.user.error.invalidMoblie";
    public static final String USER_DUPLICATE_MOBILE = "devplatformApp.user.error.duplicateMobile";

    //移动端手机登录默认密码
    public static final String USER_DEFAULT_PASSWORD = "123456";
    public static final String TLB_ACCOUNT_SEE_PASSWORD = "123456";

    public static final String MT4_DEFAULT_KEY = "_mt4_default_key";

    //排序类型
    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC = "DESC";

    public static final String DEFAULT_NAME = "默认昵称"; //默认昵称
    public static final String DEFAULT_HEAD_IMG = "http://tlb.txasfx.com/hui/img/2_01.png"; //默认头像地址

    //app用户
    public static final String MOBILE_USER_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String MOBILE_USER_APPLYING = "APPLYING";
    public static final String MOBILE_USER_AUTHORIZED = "AUTHORIZED";
    public static final String USER_ROLE_PARTNER = "partner";        //合伙人
    public static final String USER_ROLE_ORIGINATOR = "originator"; //发起人
    public static final String USER_ROLE_MANAGER = "manager";        //资金管理人

    //用户初始资产
    public static final Double USER_ASSET_INIT = 0.0;

    //交易账号初始数据
    public static final Double ACCOUNT_INIT = 0.0;


    //基金信号  募集中(APPLYING)，操作中(DOING)，已清盘(FINISH)
    public static final String FUND_STATUS_APPLYING = "APPLYING";        // 募集中
    public static final String FUND_STATUS_OPERATION = "OPERATION";        // 操作中
    public static final String FUND_STATUS_FINISH = "FINISH";        // 已清盘
}

