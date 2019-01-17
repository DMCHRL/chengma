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
    public static final String USER_DEFAULT_PASSWORD = "abc123";

    public static final String MT4_DEFAULT_KEY = "_mt4_default_key";


    public static final int  PER_LOT_MONEY = 10000; //套利宝每一手10000美金

    public static final double  MAX_IN_MONEY = 50000; //套利宝每一手10000美金

    //出入金 申请状态
    public static final String FUND_APPLY_STATUS_REJECT = "REJECT";
    public static final String FUND_APPLY_STATUS_APPLYING = "APPLYING";
    public static final String FUND_APPLY_STATUS_PASSED = "PASSED";

    //资金方向
    public static final String FUND_APPLY_IN = "IN";
    public static final String FUND_APPLY_OUT = "OUT";
    public static final String FUND_APPLY_INNER = "INNER";

    //客服任务申请状态
    public static final String TLB_USER_STATUS_APPLYING = "APPLYING";
    public static final String TLB_USER_STATUS_PASSED = "PASSED";

    //客服任务申请类型
    public static final String TLB_USER_TYPE_APPLYACCOUNT = "APPLYACCOUNT"; //开户申请

    //开户申请证件类型
    public static final String TLB_USER_ID_CODE="IDCODE";
    public static final String TLB_USER_DRIVE_CODE="DRIVECODE";

    public static final int ALLOW_ZROA_MONEY_ACCOUNT = 2;

    public static final int AMOUNT_KEEP_PERICE = 2;

    public static final String EA_GROUP_DEFAULT = "B";


    //排序类型
    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC= "DESC";


    //賬號類型
    public static final String ERROR_USER_OR_PASSWORD="賬號或密碼錯誤";

    //下单手数
    public static final double  MIN_LOTS = 0.1;

    public static final int LOTS_KEEP_SCALE = 1;

    public static final double  MAX_PER_LOTS = 10;

    public static final double  MAX_SUM_LOTS = 10;

    public static final double  MIN_OUT_AMOUNT = 100;

    //黄金下单限制时间
    public static final double  XAUUSD_LIMIT_TRADE_END_TIME = 8;
    public static final double  XAUUSD_LIMIT_TRADE_START_TIME = 1;


    //關聯賬號類型
    public static final String ACCOUNT_CONTROL_TRADE_YES = "YES"; //可交易
    public static final String ACCOUNT_CONTROL_TRADE_NO = "NO";   //只观摩

    //賬號默認觀摩密碼
    public static final String TLB_ACCOUNT_SEE_PASSWORD = "123abc";

    //银联支付状态
    public static final String UNION_PAY_STATUS_Y = "Y";   //支付成功
    public static final String UNION_PAY_STATUS_N = "N";   //未支付

    //支付结果状态码
    public static final String UNION_PAY_SUCCESS = "1000";  //交易成功
    public static final String UNION_PAY_IN = "2001";  //支付中
    public static final String UNION_PAY_FAIL = "1002"; //交易失敗
    public static final String UNION_PAY_NOT_EXISTS = "4001"; //交易不存在

    //消息通知
    public static final String WHOLE_NOTICE = "WHOLE";        //全体


    public static final String MT4_CONFIGURE = "402881076835cc14016835d013ec0000";        //全体
}

