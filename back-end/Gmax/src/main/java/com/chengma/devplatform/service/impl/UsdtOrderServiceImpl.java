package com.chengma.devplatform.service.impl;


import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.UsdtOrder;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.UsdtOrderRepository;
import com.chengma.devplatform.service.*;
import com.chengma.devplatform.service.dto.DigitalCurrencyDTO;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;
import com.chengma.devplatform.service.mapper.UsdtOrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Service Implementation for managing TranInfo.
 */
@Service
@Transactional
public class UsdtOrderServiceImpl implements UsdtOrderService {

    private final Logger log = LoggerFactory.getLogger(UsdtOrderService.class);

    private final UsdtOrderRepository usdtOrderRepository;

    private final UsdtOrderMapper usdtOrderMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TlbCommissionService tlbCommissionService;

    @Autowired
    private TlbAccountService tlbAccountService;

    @Autowired
    private DigitalCurrencyService digitalCurrencyService;

    public UsdtOrderServiceImpl(UsdtOrderRepository usdtOrderRepository, UsdtOrderMapper usdtOrderMapper) {
        this.usdtOrderRepository = usdtOrderRepository;
        this.usdtOrderMapper = usdtOrderMapper;
    }

    @Override
    public HashMap<String, Object> checkUsdtOrderDTO(UsdtOrderDTO usdtOrderDTO) {
        HashMap<String, Object>  retMap = new HashMap<>();
        User user = userService.getUserWithAuthorities();
        if(user == null){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请登录");
            return retMap;
        }

        if(StringUtils.isBlank(usdtOrderDTO.getAccount())){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入交易账号");
            return retMap;
        }

        if(usdtOrderDTO.getFaceValue() <= 0){
            retMap.put("statusCode", ResponseResult.FAIL_CODE);
            retMap.put("msg", "请输入有效金额");
            return retMap;
        }

        retMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return retMap;
    }

    @Override
    public HashMap<String, Object> createUsdtOrderDTO(UsdtOrderDTO usdtOrderDTO) {

        DigitalCurrencyDTO digitalCurrencyDTO = digitalCurrencyService.findNew();
        if(digitalCurrencyDTO == null) return null;

        HashMap<String,Object> result = new HashMap<>();
        usdtOrderDTO.setOrderId(getCurrentDateTimeStr());
        String P_Result_url = digitalCurrencyDTO.getResultUrl();//充值状态通知地址
        String P_Notify_url = digitalCurrencyDTO.getNotifyUrl();//充值后网页跳转地址
        String signParam = digitalCurrencyDTO.getUserId()+"|"+usdtOrderDTO.getOrderId()+"|"+usdtOrderDTO.getFaceValue()+"|"+digitalCurrencyDTO.getChannelId()+"|"+digitalCurrencyDTO.getChannelKey();
        String P_PostKey = encryption(signParam);
        usdtOrderDTO.setStatus("0");//未付款
        usdtOrderDTO.setNotic("usdt");  //可以放汇率
        usdtOrderDTO.setCreateAt(new Date());
        usdtOrderDTO.setUserId(userService.getUserWithAuthorities().getId());
        usdtOrderRepository.save(usdtOrderMapper.toEntity(usdtOrderDTO));

        String url = digitalCurrencyDTO.getPayUrl()+"?P_UserId="+digitalCurrencyDTO.getUserId()+"&P_OrderId="+usdtOrderDTO.getOrderId()+"&P_CardId="+
                "&P_CardPass="+"&P_FaceValue="+usdtOrderDTO.getFaceValue()+"&P_ChannelId="+digitalCurrencyDTO.getChannelId()+
                "&P_Notic="+usdtOrderDTO.getNotic()+"&P_Result_url="+P_Result_url+"&P_Notify_url="+P_Notify_url+"&P_PostKey="+P_PostKey;
        result.put("statusCode", ResponseResult.SUCCESS_CODE);
        result.put("data", url);
        return result;
    }

    @Override
    public UsdtOrder findByOrderId(String orderId) {
        return null;
    }

    @Override
    public void notifyUrl(HashMap<String, Object> params) {
        Integer UserId = (Integer)params.get("UserID");
        String OrderId = (String)params.get("OrderID");
        String OderUsername = (String)params.get("OderUsername");
        Integer Result = (Integer)params.get("Result");
        DecimalFormat df = new DecimalFormat("0.00000000"); //保留8位
        String OrderMoney = df.format((Float)params.get("OrderMoney"));
        String SuccTime = (String)params.get("SuccTime");
        String PostKey = (String)params.get("PostKey");
        String SalfStr = digitalCurrencyService.findNew().getChannelKey();
        if(StringUtils.isBlank(PostKey)){
            log.debug("=========================验证失败,缺少签名===================");
        }
        String signParam = UserId+""+OrderId+""+OderUsername+""+Result+""+OrderMoney+""+SuccTime+""+SalfStr;
        log.debug(signParam);
        String encodeStr = encryption(signParam);
        if(encodeStr.equals(PostKey)){
            if(Result == 0){ //支付成功
                log.debug("=========================支付成功===================");
                UsdtOrder usdtOrder = usdtOrderRepository.findByOrderIdEquals(OrderId);
                usdtOrder.setStatus("1"); //支付成功
                usdtOrder.setUpdateAt(new Date());
                Float usdt = usdtOrder.getFaceValue();
                usdtOrderRepository.save(usdtOrder);
               /* String account = usdtOrder.getAccount();

                //郵箱
                TlbAccountDTO tlbAccountDTO = tlbAccountService.findByAccount(account);
                tlbAccountDTO.setAccount(account);
                Double usd = usdt.doubleValue();   //实际要有汇率
                tlbAccountDTO.setBalance(usd);
                if(null != tlbCommissionService.updateCommissionBalance(account, usd)) {
                    tlbAccountService.updateAccountBalance(account, usd);
                    mailService.sendFundInnerMail(userService.getUserWithAuthorities(tlbAccountDTO.getUserId()), tlbAccountDTO);
                }*/
            }
        }else{
            log.debug("=========================验证失败===================");
        }

    }

    /**
     * 获取当前时间str，格式yyyyMMddHHmmss
     * @return
     * @author guoyx
     */
    public static String getCurrentDateTimeStr()
    {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }

    public String encryption(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}
