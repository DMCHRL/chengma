package com.chengma.devplatform.service;


import com.chengma.devplatform.common.util.JSONUtils;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class AppPushService {
    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "uyUnIh7D6M64n6HXaCyew8";
    private static String appKey = "xT2fjPIE718hjTAWrOFLY6";
    private static String masterSecret = "kdYciGRXQW7xvVUJWyAwr9";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 发送到全体用户,带url
     * @param title
     * @param text
     * @param url1
     */
    public void sendAllWithUrl(String title,String text,String url1) {
         IGtPush push = new IGtPush(url, appKey, masterSecret);

         // 定义"点击链接打开通知模板"，并设置标题、内容、链接
         LinkTemplate template = linkTemplateDemo(title,text,url1);
         //template.setUrl("http://getui.com");

         List<String> appIds = new ArrayList<String>();
         appIds.add(appId);

         // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
         AppMessage message = new AppMessage();
         message.setData(template);
         message.setAppIdList(appIds);
         message.setOffline(true);
         message.setOfflineExpireTime(24 * 3600 * 1000);
         message.setSpeed(100); //每秒一百条
         IPushResult ret = push.pushMessageToApp(message);
         System.out.println(ret.getResponse().toString());
    }

    /**
     * 发送到全体用户
     * @param title
     * @param text
     */
    public  void sendAllNoUrl(String title,String text) {
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        NotificationTemplate template = notificationTemplateDemo(title,text);
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setSpeed(100); //每秒一百条
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());

    }

    /**
     * 发送到单个用户,带url
     * @param title
     * @param text
     * @param CID
     * @param url1
     */
    public  void sentToOneWithUrl(String title,String text,String CID,String url1){
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        LinkTemplate template = linkTemplateDemo(title,text,url1);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }

    /**
     * 发送到单个用户
     * @param title
     * @param text
     * @param CID
     */
    public  void sentToOneNoUrl(String title,String text,String CID){
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        NotificationTemplate template = notificationTemplateDemo(title,text);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(CID);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }

    public static LinkTemplate linkTemplateDemo(String title,String text,String url1) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);


        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(text);
        // 配置通知栏图标
        //style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 设置打开的网址地址(不能为空)
        template.setUrl(url1);
        return template;
    }

    public static void main(String[] args) throws IOException {

        /*IGtPush push = new IGtPush(url, appKey, masterSecret);

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        NotificationTemplate template = notificationTemplateDemo("测试1","测试1");
       *//* template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("测试1");
        template.setText("测试1");
        template.setUrl("http://getui.com");*//*

        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());*/
        //sentToOne("测试1","测试1","10f49bdf81240f8a450d011b0d1764b9");
        //sendNews();
        //sendAllNoUrl("测试","测试");
    }

    public static NotificationTemplate notificationTemplateDemo(String title, String text) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        //template.setTransmissionContent("请输入您要透传的内容");
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(text);
        // 配置通知栏图标
        //style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        return template;
    }

    /**
     *新闻消息模板
     * @return
     */
    public static NotificationTemplate newsTemplate(String title,String text,String id) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(1);
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        try {
            template.setTransmissionContent(JSONUtils.obj2json(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(text);
        // 配置通知栏图标
        //style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        return template;
    }

    public  void sendNews(String title,String text,String id) {
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        NotificationTemplate template = newsTemplate(title,text,id);
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setSpeed(100); //每秒一百条
        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());

    }

}
