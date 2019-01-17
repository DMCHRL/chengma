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

    public static final String MT4_DEFAULT_KEY = "_mt4_default_key";


    public static final int PER_LOT_MONEY = 10000; //套利宝每一手10000美金

    //出入金 申请状态
    public static final String FUND_APPLY_STATUS_REJECT = "REJECT";
    public static final String FUND_APPLY_STATUS_APPLYING = "APPLYING";
    public static final String FUND_APPLY_STATUS_PASSED = "PASSED";

    //资金方向
    public static final String FUND_APPLY_IN = "IN";
    public static final String FUND_APPLY_OUT = "OUT";
    public static final String FUND_APPLY_INNER = "INNER";

    //排序类型
    public static final String SORT_ASC = "ASC";
    public static final String SORT_DESC = "DESC";


    /**
     * hpp
     */

    //报名线下培训 申请状态
    public static final String TRAIN_APPLY_STATUS_REJECT = "REJECT";
    public static final String TRAIN_APPLY_STATUS_APPLYING = "APPLYING";
    public static final String TRAIN_APPLY_STATUS_PASSED = "PASSED";

    //初始化
    public static final Double INTEGRAL = 100.0;     //初始j积分
    public static final String DEFAULT_NAME = "默认昵称"; //默认昵称
    public static final String DEFAULT_HEAD_IMG = "http://tlb.txasfx.com/hui/img/2_01.png"; //默认头像地址

    //策略跟单类型
    public static final String STRATEGY_IN = "IN";   //跟单
    public static final String STRATEGY_OUT = "OUT"; //解绑

    //跟单申请状态
    public static final String STRATEGY_ORDER_APPLY_STATUS_REJECT = "REJECT";
    public static final String STRATEGY_ORDER_APPLY_STATUS_APPLYING = "APPLYING";
    public static final String STRATEGY_ORDER_APPLY_STATUS_PASSED = "PASSED";

    //跟单云端状态
    public static final String STRATEGY_ORDER_MT4_STATUS_ONLINE = "ONLINE";    //已上线
    public static final String STRATEGY_ORDER_MT4_STATUS_DOWNLINE = "DOWNLINE";//已下线
    public static final String STRATEGY_ORDER_MT4_STATUS_NOLINE = "NOLINE";    //未上线


    //客服任务申请状态
    public static final String HPP_USER_STATUS_APPLYING = "APPLYING";
    public static final String HPP_USER_STATUS_PASSED = "PASSED";

    //跟单信息是否有效
    public static final String STRATEGY_EFFECTIVE = "EFFECTIVE";  //有效
    public static final String STRATEGY_INVALID = "INVALID";  //无效

    //策略申请状态
    public static final String STRATEGY_APPLY_STATUS_REJECT = "REJECT";
    public static final String STRATEGY_APPLY_STATUS_APPLYING = "APPLYING";
    public static final String STRATEGY_APPLY_STATUS_PASSED = "PASSED";

    //手机用户是否有新消息
    public static final String MOBILE_FLAG_YES = "YES";
    public static final String MOBILE_FLAG_NO = "NO";

    //手机用户消息状态
    public static final String MSG_STATUS_UNREAD = "UNREAD";
    public static final String MSG_STATUS_READ = "READ";

    //消息类型
    public static final String  MSG_TYPE_FOLLOW_ORDER = "策略跟单";
    public static final String  MSG_TYPE_RELIEVE_ORDER = "策略解除";
    public static final String  MSG_TYPE_FOLLOW_ACCOUNT = "账号绑定";

    //消息体
    public static final String  MSG_FOLLOW_ORDER_SUCCESS = "已跟单交易组合策略";
    public static final String  MSG_FOLLOW_ORDER_FAIL = "跟单交易组合策略失败";
    public static final String  MSG_RELIEVE_ORDER_SUCCESS = "已解除交易组合策略";
    public static final String  MSG_RELIEVE_ORDER_FAIL = "解除交易组合策略失败";
    public static final String  MSG_FOLLOW_ACCOUNT_SUCCESS = "交易账号绑定成功";
    public static final String  MSG_FOLLOW_ACCOUNT_FAIL = "交易账号绑定失败";

    //信号中断
    public static final String  MSG_TYPE_STRATEGY_OUT = "信号中断标题测试";
    public static final String  MSG_STRATEGY_OUT = "信号中断内容测试";

    //视频类别是否每日推荐
    public static final String VIDEO_TYPE_DAY_PUSH_YES = "YES";
    public static final String VIDEO_TYPE_DAY_PUSH_NO = "NO";

    //广告类型
    public static final String ADVERTISEMENT_TYPE_HOME = "HOME";
    public static final String ADVERTISEMENT_TYPE_VIDEO = "VIDEO";
    public static final String ADVERTISEMENT_TYPE_SINKS = "SINKS";
    public static final String ADVERTISEMENT_TYPE_STRATEGY = "STRATEGY";
    public static final String ADVERTISEMENT_TYPE_LIVE = "LIVE";

    public static final int AMOUNT_KEEP_PERICE = 2;

    //设置新闻是否显示app
    public static final String HPP_NEWS_STATUS_Y = "Y";  //显示
    public static final String HPP_NEWS_STATUS_N = "N";  //不显示

    /**
     * 增长率查询条件
     */
    public static final String DAYS = "days"; //按天分
    public static final String MONTHS = "months";

    //消息通知对象
    public static final String MULTIPLE_NOTICE = "MULTIPLE"; //单个或多个
    public static final String WHOLE_NOTICE = "WHOLE";        //全体
    public static final String FOLLOW_NOTICE = "FOLLOW";        //跟单用户
    public static final String BUY_NOTICE = "BUY";        //消费用户
    public static final String DEFAULT_NOTICE = "DEFAULT";        //自动通知

    //积分明细类别
    public static final String INTEGRAL_DETAIL_TYPE_FRIEND = "FRIEND";
    public static final String INTEGRAL_DETAIL_TYPE_COMMUNITY = "COMMUNITY";
    public static final String INTEGRAL_DETAIL_TYPE_VIDEO = "VIDEO";
    public static final String INTEGRAL_DETAIL_TYPE_COURSE = "COURSE";
    public static final String INTEGRAL_DETAIL_TYPE_EXAM = "EXAM";
    public static final String INTEGRAL_DETAIL_TYPE_STRATEGY = "STRATEGY";
    public static final String INTEGRAL_DETAIL_TYPE_SYSTEM = "SYSTEM";
    public static final String INTEGRAL_DETAIL_TYPE_LOGIN = "LOGIN";

    //积分管理
    public static final Double INTEGRAL_COMMUNITY = 30.0;
    public static final Double INTEGRAL_FRIEND = 10.0;
    public static final Double INTEGRAL_VIDEO = 20.0;
    public static final Double WECHAT_VIDEO = 50.0;
    public static final Double WECHAT_COURSE = 2000.0;
    public static final Double INTEGRAL_COURSE = 2000.0;
    public static final Double INTEGRAL_LOGIN = 25.0;

    //表示套餐的价格表
    public static final Double FLAG_COURSE_A = 36000.0;
    public static final Double FLAG_COURSE_B = 32000.0;
    public static final Double FLAG_COURSE_C = 28000.0;

    //最高抵扣
    public static final Double MIX_COURSE_A = 5000.0;
    public static final Double MIX_COURSE_B = 4000.0;
    public static final Double MIX_COURSE_C = 3000.0;
    public static final Double MIX_EXAM = 1000.0;

    public static final Integer SHARE_COMMUNITY_NUM = 5;
    public static final Integer SHARE_LOGIN_NUM = 1;
    public static final Integer SHARE_FRIEND_NUM = 10;

    //报名人数初始值
    public static final Integer APPLY_COURSE = 18000;
    public static final Integer APPLY_EXAM = 2100;


    //积分去向
    public static final String INTEGRAL_IN = "IN";
    public static final String INTEGRAL_OUT = "OUT";

    //支付类型
    public static final String PAY_TYPE_INTEGRAL= "integral";
    public static final String PAY_TYPE_WXCHAT = "weChat";
    public static final String PAY_TYPE_MIX = "mix";

    //支付状态
    public static final String PAY_STATUS_N= "N";
    public static final String PAY_STATUS_Y = "Y";
    public static final String PAY_STATUS_DISABLE = "DISABLE";

    //商品body类型
    public static final String BODY_TYPE_COURSE= "course";
    public static final String BODY_TYPE_VIDEO = "video";
    public static final String BODY_TYPE_EXAM = "exam";


}

