package com.suitong.devplatform.common.util;

import com.alibaba.fastjson.JSONObject;
import com.ucpaas.restDemo.client.JsonReqClient;

import java.util.Random;

public class SysRestUtil {
    private static volatile SysRestUtil conf;

    public static SysRestUtil getInstance() {
        if (conf == null) {
            synchronized (SysRestUtil.class) {
                if (conf == null) {
                    conf = new SysRestUtil();
                }
            }
        }
        return conf;
    }

    public static String sendCode(String phone, String param){
        return send(phone,param,"sendCode");
    }

    public static String noticeForSignUp(String phone, String param){
        return send(phone,param,"noticeForSignUp");
    }

    public static String noticeForRegister(String phone, String param){
        return send(phone,param,"noticeForRegister");
    }

    /**
     *
     * @param phone 手机号
     * @param param 自定义内容，对应短信模板参数，参数用英文逗号隔开
     * @param type 非官方参数，根据不同的type调用不同的短信模板
     * @return 返回000000则表示发送成功, 105112表示参数数量不匹配， 其他代码请查阅云之讯官网文档
     */
    private static String send(String phone, String param, String type){
        String accountSid = SysRestConfig.getInstance().getProperty("accountSid");
        String token = SysRestConfig.getInstance().getProperty("token");
        String appId = SysRestConfig.getInstance().getProperty("appId");
        String templateId = null;
        int paramsCount = 0;

        if("sendCode".equals(type)){
            templateId = SysRestConfig.getInstance().getProperty("sendCodeTemplate");
            String sendCodeParamsCount = SysRestConfig.getInstance().getProperty("sendCodeParamsCount");
            paramsCount = sendCodeParamsCount == null ? 0 :  Integer.valueOf(sendCodeParamsCount);
            if(!checkParams(paramsCount,param)){
                return "105112";
            }
        }
        if("noticeForSignUp".equals(type)){
            templateId = SysRestConfig.getInstance().getProperty("signUpTemplate");
            String signUpParamsCount = SysRestConfig.getInstance().getProperty("signUpParamsCount");
            paramsCount = signUpParamsCount == null ? 0 :  Integer.valueOf(signUpParamsCount);
            if(!checkParams(paramsCount,param)){
                return "105112";
            }
        }
        if("noticeForRegister".equals(type)){
            templateId = SysRestConfig.getInstance().getProperty("registerTemplate");
            String registerParamsCount = SysRestConfig.getInstance().getProperty("registerParamsCount");
            paramsCount = registerParamsCount == null ? 0 :  Integer.valueOf(registerParamsCount);
            if(!checkParams(paramsCount,param)){
                return "105112";
            }
        }

        String result = new JsonReqClient().templateSMS(accountSid, token, appId, templateId, phone, param);
        JSONObject json = JSONObject.parseObject(result);
        JSONObject resp = json.getJSONObject("resp");
        String respCode = resp.getString("respCode");
        System.out.println("Response content is: " + result);
        return respCode;
    }

    /**
     * 生成随机码
     */
    public static String getCode() {
        char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        int codeCount = 4;//验证码个数
        //定义随机数类
        Random r = new Random();
        //定义存储验证码的类
        StringBuilder builderCode = new StringBuilder();
        for (int i = 0; i < codeCount; i++) {
            char c = codeSequence[r.nextInt(codeSequence.length)];
            builderCode.append(c);
        }
        return builderCode.toString();
    }

    /**
     * 检查参数
     * @param paramsCount
     * @param param
     * @return true表示检查通过，false表示参数有误
     */
    private static boolean checkParams(int paramsCount, String param){
        if(null != param && paramsCount != param.split(",").length){
            return false;
        }
        if(null == param && paramsCount != 0){
            return false;
        }
        return true;
    }


}
