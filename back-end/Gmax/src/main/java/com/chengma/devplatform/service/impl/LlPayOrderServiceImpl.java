package com.chengma.devplatform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.config.llpay.config.PartnerConfig;
import com.chengma.devplatform.config.llpay.config.ServerURLConfig;
import com.chengma.devplatform.config.llpay.conn.HttpRequestSimple;
import com.chengma.devplatform.config.llpay.utils.LLPayUtil;
import com.chengma.devplatform.config.llpay.vo.PaymentInfo;
import com.chengma.devplatform.domain.BankInfo;
import com.chengma.devplatform.domain.LlPayOrder;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.BankInfoRepository;
import com.chengma.devplatform.repository.LlPayOrderRepository;
import com.chengma.devplatform.service.BankInfoService;
import com.chengma.devplatform.service.LlPayOrderService;
import com.chengma.devplatform.service.TlbFundApplyService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.BankInfoDTO;
import com.chengma.devplatform.service.dto.LlPayOrderDTO;
import com.chengma.devplatform.service.dto.TranInfoDTO;
import com.chengma.devplatform.service.mapper.BankInfoMapper;
import com.chengma.devplatform.service.mapper.LlPayOrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Service
@Transactional
public class LlPayOrderServiceImpl implements LlPayOrderService {

    private final LlPayOrderRepository llPayOrderRepository;

    private final LlPayOrderMapper llPayOrderMapper;

    @Autowired
    private PageCommon pageCommon;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbFundApplyService fundApplyService;

    public LlPayOrderServiceImpl(LlPayOrderRepository llPayOrderRepository, LlPayOrderMapper llPayOrderMapper){
        this.llPayOrderRepository=llPayOrderRepository;
        this.llPayOrderMapper=llPayOrderMapper;
    }

    @Override
    public Page<LlPayOrderDTO> pageList(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public LlPayOrderDTO save(LlPayOrderDTO llPayOrderDTO) {
        return llPayOrderMapper.toDto(llPayOrderRepository.save(llPayOrderMapper.toEntity(llPayOrderDTO)));
    }

    @Override
    public HashMap<String,Object> createLlPayOrderDTO(LlPayOrderDTO llPayOrderDTO) {
        HashMap<String,Object> result = new HashMap<>();

        User user = userService.getUserWithAuthorities();
        llPayOrderDTO.setNoOrder(LLPayUtil.getCurrentDateTimeStr());
        llPayOrderDTO.setDtOrder(LLPayUtil.getCurrentDateTimeStr());
        llPayOrderDTO.setNameGoods("充值");
        llPayOrderDTO.setFlagPayProduct("2"); //网银
        llPayOrderDTO.setUserId(user.getId());


        // 构造支付请求对象
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setApi_version(PartnerConfig.VERSION);
        paymentInfo.setOid_partner(PartnerConfig.OID_PARTNER);  //商户号
        paymentInfo.setSign_type(PartnerConfig.SIGN_TYPE);
        paymentInfo.setBusi_partner(PartnerConfig.BUSI_PARTNER);
        paymentInfo.setNo_order(llPayOrderDTO.getNoOrder());
        paymentInfo.setDt_order(llPayOrderDTO.getDtOrder());
        paymentInfo.setName_goods(llPayOrderDTO.getNameGoods());
           /* paymentInfo.setInfo_order(order.getInfo_order());*/
        paymentInfo.setMoney_order(llPayOrderDTO.getMoneyOrder());
        paymentInfo.setNotify_url(PartnerConfig.NOTIFY_URL);   //异步通知
        paymentInfo.setUrl_return(PartnerConfig.URL_RETURN);   //同步通知
        paymentInfo.setValid_order("30");// 单位分钟，可以为空，默认30分钟
        paymentInfo.setTime_stamp(LLPayUtil.getCurrentDateTimeStr());
        paymentInfo.setRisk_item(createRiskItem());
        paymentInfo.setFlag_chnl("3");//2 web 、3 wap
        paymentInfo.setFlag_pay_product(llPayOrderDTO.getFlagPayProduct());//0 快捷、1 认证、2 网银、5新认证支付

        paymentInfo.setUser_id(llPayOrderDTO.getUserId());
      /*  paymentInfo.setCard_no(llPayOrderDTO.getCardNo());   //银行卡号
        paymentInfo.setAcct_name(llPayOrderDTO.getAcctName());  //银行卡持有人姓名
        paymentInfo.setId_type("0");   //可选 身份证
        paymentInfo.setId_no(llPayOrderDTO.getIdNo()); //可选*/
        //paymentInfo.setBind_mob(req.getParameter("bind_mob"));
        //paymentInfo.setBank_code(req.getParameter("bank_code"));  //bank_code card_type二选一
        paymentInfo.setCard_type(llPayOrderDTO.getCardType());


        // 加签名
        String sign = LLPayUtil.addSign(JSON.parseObject(JSON
                        .toJSONString(paymentInfo)), PartnerConfig.TRADER_PRI_KEY,
                "");
        paymentInfo.setSign(sign);

        String resJSON = HttpRequestSimple.getInstance().postSendHttp(ServerURLConfig.BILL_CREATE_URL, JSON.toJSONString(paymentInfo));
        System.out.println("创单请求响应报文[" + resJSON + "]");

        JSONObject payDataBean = JSON.parseObject(resJSON);
        if(!"0000".equals(payDataBean.getString("ret_code"))){
            result.put("statusCode", ResponseResult.FAIL_CODE);
            result.put("msg", "创单失败!");
            return result;
        }

        if (!LLPayUtil.checkSign(resJSON, PartnerConfig.YT_PUB_KEY, "")) {
            result.put("statusCode", ResponseResult.FAIL_CODE);
            result.put("msg", "签名验证失败!");
            return result;
        }

        LlPayOrderDTO llPayOrderDTO1 = llPayOrderMapper.toDto(llPayOrderRepository.save(llPayOrderMapper.toEntity(llPayOrderDTO)));
        llPayOrderDTO1.setGatewayUrl(payDataBean.getString("gateway_url"));
        result.put("statusCode", ResponseResult.SUCCESS_CODE);
        result.put("data", llPayOrderDTO1);
        return result;


    }

    /**
     * 根据连连支付风控部门要求的参数进行构造风控参数
     * @return
     */
    private String createRiskItem()
    {
        JSONObject riskItemObj = new JSONObject();
        riskItemObj.put("user_info_full_name", "你好");
        riskItemObj.put("frms_ware_category", "1999");
        return riskItemObj.toString();
    }


    @Override
    public HashMap<String, Object> checkCreateLlPayOrderDTO(LlPayOrderDTO llPayOrderDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public LlPayOrderDTO findOne(String id) {
        return llPayOrderMapper.toDto(llPayOrderRepository.findOne(id));
    }

    @Override
    public List<LlPayOrderDTO> findAll() {
        return llPayOrderMapper.toDto(llPayOrderRepository.findAll());
    }

    @Override
    public void delete(String id) {
        llPayOrderRepository.delete(id);
    }

    @Override
    public void updateTranInfo(String noOrder) {
        LlPayOrder llPayOrder = llPayOrderRepository.findByNoOrderEquals(noOrder);

        if( null != llPayOrder){
            //更新资金申请表
          /*  HashMap<String, Object> paramsFundApply = new HashMap<>();
            paramsFundApply.put("account", tranInfoDTO.getObj());
            paramsFundApply.put("usd", Long.valueOf(tranInfoDTO.getTotalFeeUsd().toString()));
            paramsFundApply.put("amount", Long.valueOf(tranInfoDTO.getTotalFee().toString()) / 100);
            paramsFundApply.put("transactionId", params.get("payOrderId"));
            fundApplyService.recharge(paramsFundApply);*/

            llPayOrder.setPayStatus("1");
            llPayOrderRepository.save(llPayOrder);
        }
    }
}
