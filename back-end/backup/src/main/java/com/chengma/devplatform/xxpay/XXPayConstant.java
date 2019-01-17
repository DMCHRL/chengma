package com.chengma.devplatform.xxpay;

/**
 * Created by Administrator on 2018/6/21.
 */
public class XXPayConstant {

    //public static final String MCH_ID = "20000004";//20001223,20001245
    public static final String MCH_ID = "20000013";//20001223,20001245
    //public static final String APP_ID = "6844060c13294a0eba192eeb38d85a0b";
    public static final String APP_ID = "c6f4cbd99afd4f8db1f136edff129804";
    public static final String PASSAGE_ID = "1";
    // 加签key
    //static final String REQ_KEY = "y4jRRWXFLGcVCBKrCr6qknXfiY9GtzQSOneAUxwNbZmkvXX2";
    static final String REQ_KEY = "XDLhWnONcECAMoRR9gwaXk2KbtiTsPmRzRzMQ5XBfFL83JkT";
    // 验签key
    //static final String REP_KEY = "bnzHqeyIGdEh8LU6MjjlnFk6753WQNtKT4Xxu36DNkSWySRI";
    static final String REP_KEY = "wtmmVs2zg65zLlt72vPIWZIwBhdR2yEmJNKPiwBno5jjWwkk";

    static final String BASE_URL = "http://47.75.36.94:3020/api";

    static final String NOTIFY_URL = "http://shop.xxpay.org/goods/payNotify";

    //static final String BASE_NOTIFY_URL = "http://8ssvsg.natappfree.cc";

    //static final String BASE_NOTIFY_URL = "http://8ssvsg.natappfree.cc";
    static final String BASE_NOTIFY_URL = "http://47.52.199.109:8081/crm";

    static final String BIND_NOTIFY_URL = "/api/xxpay/bind_callback";

    static final String ORDER_NOTIFY_URL = "/api/xxpay/pay_callback";

    public static final String ORDER_STATUS_PAID = "Y";

    public static final String ORDER_STATUS_NOT_PAID = "N";

}
