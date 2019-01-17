package com.chengma.devplatform.xxpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengma.devplatform.config.XXpayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xxpay.core.common.util.PayDigestUtil;
import org.xxpay.core.common.util.XXPayUtil;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class XXPayApi {

    private static final Logger log = LoggerFactory.getLogger(XXPayApi.class);

    @Autowired
    public XXpayProperties xxpayProperties;

    public static XXPayApi xxPayApi;

    @PostConstruct
    public void init() {
        xxPayApi = this;
        xxPayApi.xxpayProperties = this.xxpayProperties;
    }


    // 统一下单
    public static Map payOrder(JSONObject paramMap) {
        paramMap = setMch(paramMap, true);
        paramMap.put("mchOrderNo", System.currentTimeMillis());     // 商户订单号
        //paramMap.put("notifyUrl", XXPayConstant.BASE_NOTIFY_URL + XXPayConstant.ORDER_NOTIFY_URL);
        paramMap.put("notifyUrl", xxPayApi.xxpayProperties.getBaseNotifyUrl() + xxPayApi.xxpayProperties.getOrderNotifyUrl());
        //String reqSign = PayDigestUtil.getSign(paramMap, XXPayConstant.REQ_KEY);
        String reqSign = PayDigestUtil.getSign(paramMap, xxPayApi.xxpayProperties.getReqKey());
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
        //System.out.println("请求支付中心下单接口,请求数据:" + reqData);
        //String url = XXPayConstant.BASE_URL + "/pay/create_order?";
        String url = xxPayApi.xxpayProperties.getBaseUrl() + "/pay/create_order?";
        String result = XXPayUtil.call4Post(url + reqData);
        //System.out.println("请求支付中心下单接口,响应数据:" + result);
        log.info("请求支付中心下单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode"))) {
            // 验签
            //String checkSign = PayDigestUtil.getSign(retMap, XXPayConstant.REP_KEY, "sign", "payParams");
            String checkSign = PayDigestUtil.getSign(retMap, xxPayApi.xxpayProperties.getRepKey(), "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                log.info("=========支付中心下单验签成功=========");
            }else {
//                System.err.println("=========支付中心下单验签失败=========");
                log.info("=========支付中心下单验签失败=========");
                return null;
            }
        }
        Map ret = new HashMap<>();

      /*  ret.put("mchOrderNo", paramMap.get("mchOrderNo"));
        ret.put("payOrderId", retMap.get("payOrderId"));
        if(null != retMap.get("payUrl")){
            ret.put("payUrl", retMap.get("payUrl"));
        }*/

        return retMap;
    }

    /**
     * 查询订单
     * @param mchOrderNo
     * @param payOrderId
     * @return
     */
    public static Map queryPayOrder(String mchOrderNo, String payOrderId) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchOrderNo", mchOrderNo);                     // 商户订单号
        paramMap.put("payOrderId", payOrderId);                     // 支付订单号
        paramMap.put("executeNotify", "true");                      // 是否执行回调,true或false,如果为true当订单状态为支付成功(2)时,支付中心会再次回调一次业务系统
        paramMap = setMch(paramMap,true);

        //String reqSign = PayDigestUtil.getSign(paramMap, XXPayConstant.REQ_KEY);
        String reqSign = PayDigestUtil.getSign(paramMap, xxPayApi.xxpayProperties.getReqKey());
        paramMap.put("sign", reqSign);                              // 签名
        String reqData = "params=" + paramMap.toJSONString();
//        System.out.println("请求支付中心查单接口,请求数据:" + reqData);
        //String url = XXPayConstant.BASE_URL + "/pay/query_order?";
        String url = xxPayApi.xxpayProperties.getBaseUrl() + "/pay/query_order?";
        log.info("请求支付中心查单接口,请求数据:" + reqData);
        String result = XXPayUtil.call4Post(url + reqData);
        //System.out.println("请求支付中心查单接口,响应数据:" + result);
        log.info("请求支付中心查单接口,响应数据:" + result);
        Map retMap = JSON.parseObject(result);
        if("SUCCESS".equals(retMap.get("retCode"))) {
            // 验签
            //String checkSign = PayDigestUtil.getSign(retMap, XXPayConstant.REP_KEY, "sign", "payParams");
            String checkSign = PayDigestUtil.getSign(retMap, xxPayApi.xxpayProperties.getRepKey(), "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if(checkSign.equals(retSign)) {
                //System.out.println("=========支付中心查单验签成功=========");
                log.info("=========支付中心查单验签成功=========");
            }else {
//                System.err.println("=========支付中心查单验签失败=========");
                log.info("=========支付中心查单验签失败=========");
                return null;
            }
        }
        return retMap;
    }
    /**
     * 支付确认
     * @param paramMap
     * @return
     */

    public static Map payConfirm(JSONObject paramMap) {
        paramMap = setMch(paramMap);
        String reqData = reqData(paramMap);
        //System.out.println("请求支付确认接口,请求数据:" + reqData);
        //String url = XXPayConstant.BASE_URL + "/pay/zjzyfo_pay_confirm?";
        String url = xxPayApi.xxpayProperties.getBaseUrl() + "/pay/zjzyfo_pay_confirm?";
        log.info("请求支付确认接口,请求数据:" + url + reqData);
        String result = XXPayUtil.call4Post(url + reqData);
        //System.out.println("请求支付确认接口,响应数据:" + result);
        log.info("请求支付确认接口,响应数据:" + reqData);

        if (!verifySign(result)) {
            return null;
        } else {
             Map retMap = JSON.parseObject(result);
            return retMap;
        }
    }

    /**
     * 绑卡
     * @param paramMap
     * @return
     */
    public static Map bind(JSONObject paramMap) {
        paramMap = setMch(paramMap);
        //paramMap.put("notifyurl", XXPayConstant.BASE_NOTIFY_URL + XXPayConstant.BIND_NOTIFY_URL);
        paramMap.put("notifyurl", xxPayApi.xxpayProperties.getBaseNotifyUrl() + xxPayApi.xxpayProperties.getBindNotifyUrl());
        String reqData = reqData(paramMap);
        //System.out.println("请求绑卡接口,请求数据:" + reqData);
        //String url = XXPayConstant.BASE_URL + "/pay/zjzyfo_bind?";
        String url = xxPayApi.xxpayProperties.getBaseUrl() + "/pay/zjzyfo_bind?";
        log.info("请求绑卡接口,请求数据:" + url + reqData);
        String result = XXPayUtil.call4Post(url + reqData);
        //System.out.println("请求绑卡接口,响应数据:" + result);
        log.info("请求绑卡接口,响应数据:" + result);

        if (!verifySign(result)) {
            return null;
        } else {
            Map ret = JSON.parseObject(result);
            return ret;
        }
    }

    /**
     * 绑卡确认
     * @param paramMap
     * @return
     */
    public static Map bindConfirm(JSONObject paramMap) {
        paramMap = setMch(paramMap);
        String reqData = reqData(paramMap);
        //System.out.println("请求绑卡确认接口,请求数据:" + reqData);
        //String url = XXPayConstant.BASE_URL + "/pay/zjzyfo_bind_confirm?";
        String url = xxPayApi.xxpayProperties.getBaseUrl() + "/pay/zjzyfo_bind_confirm?";
        log.info("请求绑卡确认接口,请求数据:" + url + reqData);
        String result = XXPayUtil.call4Post(url + reqData);
        //System.out.println("请求绑卡确认接口,响应数据:" + result);
        log.info("请求绑卡确认接口,响应数据:" + result);

        if (!verifySign(result)) {
            return null;
        } else {
            Map ret = JSON.parseObject(result);
            return  ret;
        }
    }


    private static boolean verifySign(String result) {
        Map retMap = JSON.parseObject(result);
        if ("SUCCESS".equals(retMap.get("retCode"))) {
            // 验签
            //String checkSign = PayDigestUtil.getSign(retMap, XXPayConstant.REP_KEY, "sign");
            String checkSign = PayDigestUtil.getSign(retMap, xxPayApi.xxpayProperties.getRepKey(), "sign");
            String retSign = (String) retMap.get("sign");
            if (checkSign.equals(retSign)) {
                //System.out.println("=========验签成功=========");
                log.info("=========验签成功=========");
                return true;
            } else {
                log.info("=========验签失败=========");
            }
        }

        return false;
    }

    private static String reqData(JSONObject params) {
        //String reqSign = PayDigestUtil.getSign(params, XXPayConstant.REQ_KEY);
        String reqSign = PayDigestUtil.getSign(params, xxPayApi.xxpayProperties.getReqKey());
        params.put("sign", reqSign);                              // 签名
        return "params=" + params.toJSONString();
    }


    private static JSONObject setMch(JSONObject paramMap){
        return setMch(paramMap, false);
    }
    private static JSONObject setMch(JSONObject paramMap, boolean order){
//        paramMap.put("mchId", XXPayConstant.MCH_ID);                               // 商户ID
//        paramMap.put("appId", XXPayConstant.APP_ID);
        paramMap.put("mchId", xxPayApi.xxpayProperties.getMchId());                               // 商户ID
        paramMap.put("appId", xxPayApi.xxpayProperties.getAppId());
        if(order) {
            paramMap.put("passageId", XXPayConstant.PASSAGE_ID);
        }
        return paramMap;
    }


}
