package com.chengma.devplatform.config.usdt;

/**
 * Created by Administrator on 2019/1/4.
 */
public interface USDTConfig {

    String P_UserId = "10375";

    String channelKey = "447c139783be56ff9fc48aa90f225436";

    String payUrl = "https://pay.ctwo.io/Payapi_Index_Pay.html";

    String P_Result_url = "https://www.cat58.com/crm/api/usdtOrder/notifyUrl";//异步通知

    //String P_Result_url = "https://pay.ctwo.io/PayApi/Result_url.php";//异步通知

    String P_Notify_url= "http://www.txasfx.com"; //同步通知

    int channelId = 7;

}
