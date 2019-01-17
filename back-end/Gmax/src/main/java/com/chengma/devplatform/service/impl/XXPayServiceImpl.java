package com.chengma.devplatform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.TranInfoDTO;
import com.chengma.devplatform.xxpay.XXPayApi;
import com.chengma.devplatform.xxpay.XXPayConstant;
import liquibase.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/21.
 */
@Service
@Transactional
public class XXPayServiceImpl {

    private final Logger log = LoggerFactory.getLogger(XXPayServiceImpl.class);

    @Autowired
    private TranInfoService tranInfoService;

    @Autowired
    private BindBankService bindBankService;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbFundApplyService fundApplyService;

    @Autowired
    private TlbAccountService tlbAccountService;


    public Map createOrder(HashMap<String, Object> params){

        Map ret =  new HashMap();

        String account = params.get("account") == null ? "" : params.get("account").toString();
        long usd = 0L;
        try {
            usd = Long.valueOf(params.get("usd").toString());
        }catch (Exception e){
            log.info(e.getMessage());
            ret.put("retCode", "FAIL");
            ret.put("msg", "请输入正确的金额");
            return ret;
        }
        long amount = Long.valueOf(params.get("amount").toString());

        if(usd > DevplatformConstants.MAX_IN_MONEY){
            ret.put("retCode", "FAIL");
            ret.put("msg", "单笔最大入金" + DevplatformConstants.MAX_IN_MONEY + "美金");
            return ret;
        }

        if(StringUtils.isEmpty(account) || null == tlbAccountService.findByAccount(account)){
            ret.put("retCode", "FAIL");
            ret.put("msg", "请输入有效的套利宝帐号");
            return ret;
        }


        String bankId = params.get("bankId") != null ? params.get("bankId").toString() : "";
        String banksn = params.get("banksn") != null ? params.get("banksn").toString() : "";
        String payType = params.get("payType") != null ? params.get("payType").toString() : "";
        String currency = params.get("currency") != null ? params.get("currency").toString() : "cny";
        String body = params.get("body") != null ? params.get("body").toString() : "";

        JSONObject paramMap = new JSONObject();
        paramMap.put("mchOrderNo", System.currentTimeMillis());     // 商户订单号
        if("fastPay".equals(payType)){
            paramMap.put("channelId", "zjzyfo_b2c");
        }else if("gatewayPay".equals(payType)){
            paramMap.put("channelId", "gomepay_b2c");
        }else{
            paramMap.put("channelId", "gomepay_b2c2");
        }
        paramMap.put("amount", amount);                                  // 支付金额,单位分
        paramMap.put("currency", currency);                            // 币种, cny-人民币
        paramMap.put("device", "WEB");                              // 设备
        paramMap.put("subject", "充值");
        paramMap.put("body", body);
        paramMap.put("param1", "");                                 // 扩展参数1
        paramMap.put("param2", "");                                 // 扩展参数2
        paramMap.put("extra", "{\n" +
                "  \"bankId\": \"" + bankId + "\",\n" +
                ("fastPay".equals(payType) ? "  \"banksn\": \"" + banksn + "\",\n" : "") +
                "  \"biztype\": \"01\",\n" +
                "  \"buyername\": \"测试快捷支付\",\n" +
                "  \"buyerlinkinfo\": \"\"\n" +
                "}");  // 附加参数

        ret = XXPayApi.payOrder(paramMap);
        if("SUCCESS".equals(ret.get("retCode"))) {
            TranInfoDTO tranInfoDTO = new TranInfoDTO();
            tranInfoDTO.setBody(paramMap.get("body").toString());
            tranInfoDTO.setAppid(XXPayApi.xxPayApi.xxpayProperties.getAppId());
            tranInfoDTO.setOutTradeNo(paramMap.get("mchOrderNo").toString());
            tranInfoDTO.setTransactionId(ret.get("payOrderId").toString());
            tranInfoDTO.setMchId(XXPayApi.xxPayApi.xxpayProperties.getMchId());
            tranInfoDTO.setTotalFee(params.get("amount").toString());
            tranInfoDTO.setTotalFeeUsd(params.get("usd").toString());
            tranInfoDTO.setObj(account);
            tranInfoDTO.setUserId(userService.getUserWithAuthorities() != null ? userService.getUserWithAuthorities().getId() : "3");
            tranInfoDTO.setPaySuccess(XXPayConstant.ORDER_STATUS_NOT_PAID);
            createTranInfo(tranInfoDTO);
        }
        ret.put("mchOrderNo", paramMap.get("mchOrderNo"));

        return ret;
    }

    public Map queryOrder(HashMap<String, Object> params){

        String mchOrderNo = params.get("mchOrderNo").toString();
        String payOrderId = params.get("payOrderId").toString();

        Map ret = XXPayApi.queryPayOrder(mchOrderNo, payOrderId);
        return ret;
    }

    public Map bind(HashMap<String, Object> params){
        String banksn = params.get("banksn").toString();
        String khname = params.get("khname").toString();
        String idtype = params.get("idtype").toString();
        String idcardsn = params.get("idcardsn").toString();
        String mobile = params.get("mobile").toString();

        Object exptime = params.get("exptime");
        Object cvn2 = params.get("cvn2");

        JSONObject paramMap = new JSONObject();

        paramMap.put("banksn", banksn);
        paramMap.put("khname", khname);
        paramMap.put("idtype", idtype);
        paramMap.put("idcardsn", idcardsn);
        paramMap.put("mobile", mobile);
        if (exptime != null) {
            paramMap.put("exptime", exptime);
        }
        if (cvn2 != null) {
            paramMap.put("cvn2", cvn2);
        }

        Map ret = XXPayApi.bind(paramMap);

        /*
        * */
        if("SUCCESS".equals(ret.get("retCode"))) {
            BindBankDTO bindBankDTO = new BindBankDTO();
            bindBankDTO.setAppid(XXPayApi.xxPayApi.xxpayProperties.getAppId());
            bindBankDTO.setMchId(XXPayApi.xxPayApi.xxpayProperties.getMchId());
            bindBankDTO.setBanksn(banksn);
            bindBankDTO.setKhname(khname);
            bindBankDTO.setIdtype(idtype);
            bindBankDTO.setIdcardsn(idcardsn);
            bindBankDTO.setMobile(mobile);
            bindBankDTO.setBindSuccess("N");
            bindBankDTO.setCreateDt(new Date());
            bindBankDTO = bindBankService.save(bindBankDTO);
            ret.put("bindBank", bindBankDTO);
        }

        return ret;
    }

    public Map bindConfirm(HashMap<String, Object> params){
        String banksn = params.get("banksn").toString();
        String smscode = params.get("smscode").toString();

        JSONObject paramMap = new JSONObject();

        paramMap.put("banksn", banksn);
        paramMap.put("smscode", smscode);

        Map ret  = XXPayApi.bindConfirm(paramMap);
        return ret;
    }

    public Map payConfirm(HashMap<String, Object> params){
        String orderid = params.get("payOrderId").toString();
        String smscode = params.get("smscode").toString();

        JSONObject paramMap = new JSONObject();

        paramMap.put("orderid", orderid);
        paramMap.put("smscode", smscode);

        Map ret = XXPayApi.payConfirm(paramMap);

        return ret;
    }

    public void updateTranInfo(HashMap<String, Object> params){
        HashMap paramsMap = new HashMap();
        paramsMap.put("payOrderId", params.get("payOrderId"));
        TranInfoDTO tranInfoDTO = tranInfoService.findByParams(paramsMap);

        if( null != tranInfoDTO){
            //更新资金申请表
            HashMap<String, Object> paramsFundApply = new HashMap<>();
            paramsFundApply.put("account", tranInfoDTO.getObj());
            paramsFundApply.put("usd", Long.valueOf(tranInfoDTO.getTotalFeeUsd().toString()));
            paramsFundApply.put("amount", Long.valueOf(tranInfoDTO.getTotalFee().toString()) / 100);
            paramsFundApply.put("transactionId", params.get("payOrderId"));
            fundApplyService.recharge(paramsFundApply);

            tranInfoDTO.setPaySuccess("Y");
            tranInfoService.save(tranInfoDTO);
        }
    }

    public void updateBindBank(HashMap<String, Object> params){
        HashMap paramsMap = new HashMap();
        paramsMap.put("banksn", params.get("banksn"));
        BindBankDTO bindBankDTO = bindBankService.findOneByParam(paramsMap);

        if( null != bindBankDTO){
            bindBankDTO.setBindSuccess("Y");
            bindBankDTO.setBindResult(params.toString());
            bindBankService.save(bindBankDTO);
        }
    }


    private void createTranInfo(TranInfoDTO tranInfoDTO){
        tranInfoService.save(tranInfoDTO);
    }


}
