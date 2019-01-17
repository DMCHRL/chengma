package com.chengma.devplatform.service.impl;

import com.chengma.devplatform.common.constant.DevplatformConstants;
import com.chengma.devplatform.common.dao.BaseDao;
import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.common.page.PageCommon;
import com.chengma.devplatform.common.util.*;
import com.chengma.devplatform.config.UnionPayProperties;
import com.chengma.devplatform.domain.UnionPayInfo;
import com.chengma.devplatform.domain.User;
import com.chengma.devplatform.repository.UnionPayInfoRepository;
import com.chengma.devplatform.service.UnionPayInfoService;
import com.chengma.devplatform.service.UserService;
import com.chengma.devplatform.service.dto.UnionPayInfoDTO;
import com.chengma.devplatform.service.mapper.UnionPayInfoMapper;
import com.chengma.devplatform.xxpay.XXPayApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing UnionPayInfo.
 */
@Service
@Transactional
public class UnionPayInfoServiceImpl implements UnionPayInfoService {

    private final Logger log = LoggerFactory.getLogger(UnionPayInfoServiceImpl.class);

    private final UnionPayInfoRepository unionPayInfoRepository;

    private final UnionPayInfoMapper unionPayInfoMapper;

    @Autowired
    private PageCommon pageCommon;
    @Autowired
    private BaseDao baseDao;

    @Autowired
    private UnionPayProperties unionPayProperties;

    @Autowired
    private UserService userService;

    public UnionPayInfoServiceImpl(UnionPayInfoRepository unionPayInfoRepository, UnionPayInfoMapper unionPayInfoMapper) {
        this.unionPayInfoRepository = unionPayInfoRepository;
        this.unionPayInfoMapper = unionPayInfoMapper;
    }

    @Override
    public UnionPayInfoDTO createOrder(UnionPayInfoDTO unionPayInfoDTO) {
        Date now = new Date();
        unionPayInfoDTO.setMerchantOrderNo("tlb"+DateUtil.formatDate(new Date()));
        User user =userService.getUserWithAuthorities();
        unionPayInfoDTO.setMerchantUserId(user.getId());
        unionPayInfoDTO.setPaySuccess(DevplatformConstants.UNION_PAY_STATUS_N);
        unionPayInfoDTO.setCreateDt(now);
        unionPayInfoDTO.setUpdateAt(now);
        unionPayInfoDTO.setMerchantId(unionPayProperties.getMerchantId());
        UnionPayInfoDTO unionPayInfoDTO1 = unionPayInfoMapper.toDto(unionPayInfoRepository.save(unionPayInfoMapper.toEntity(unionPayInfoDTO)));

        unionPayInfoDTO1.setProductCode(unionPayProperties.getProductCode());
        unionPayInfoDTO1.setNotifyUrl(unionPayProperties.getNotifyUrl());
        unionPayInfoDTO1.setCallBackUrl(unionPayProperties.getCallBackUrl());
        String key = unionPayProperties.getKey();//91a1de7ff2f0bc567092ab5f368fab12
        unionPayInfoDTO1.setDescription("fundingIn"); //中文不通过，验签失败
        unionPayInfoDTO1.setPayAmount("10"); //测试用
        String sign1 = "callBackUrl="+unionPayInfoDTO1.getCallBackUrl()+
                "&description="+unionPayInfoDTO1.getDescription()+
                "&merchantId="+unionPayInfoDTO1.getMerchantId()+
                "&merchantOrderNo="+unionPayInfoDTO1.getMerchantOrderNo()+
                "&merchantUserId="+unionPayInfoDTO1.getMerchantUserId()+
                "&notifyUrl="+unionPayInfoDTO1.getNotifyUrl()+
                "&payAmount="+unionPayInfoDTO1.getPayAmount()+
                "&key="+key;
        unionPayInfoDTO1.setSign(SmallTools.MD5en(sign1));
        return unionPayInfoDTO1;
    }

    @Override
    public HashMap<String, Object> checkSave(UnionPayInfoDTO unionPayInfoDTO) {
        HashMap<String, Object> checkMap = new HashMap<>();
        if(unionPayInfoDTO == null){
            checkMap.put("statusCode", ResponseResult.FAIL_CODE);
            checkMap.put("msg", "請輸入...");
            return checkMap;
        }
        if(unionPayInfoDTO.getPayAmountUsd() == null||Double.parseDouble(unionPayInfoDTO.getPayAmountUsd())<1000.0){
            checkMap.put("statusCode", ResponseResult.FAIL_CODE);
            checkMap.put("msg", "至少入金1000$");
            return checkMap;
        }
        checkMap.put("statusCode", ResponseResult.SUCCESS_CODE);
        return checkMap;
    }

    @Override
    public HashMap<String, Object> findAll(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public UnionPayInfoDTO findOne(String id) {
        return null;
    }

    @Override
    public UnionPayInfoDTO findByCode(UnionPayInfoDTO unionPayInfoDTO) {
        return null;
    }

    @Override
    public UnionPayInfoDTO findUnionPayByTransactionId(String transactionId) {

        String url = "https://open.heepay.com/"+unionPayProperties.getUrlPrefix()+"/transQuery.do";

        //请求参数
        String merchantId = unionPayProperties.getMerchantId();//商户号
        String merchantOrderNo = transactionId;//商户交易号，商户内部的交易ID
        String version = unionPayProperties.getVersion();//版本号
        String requestTime = SmallTools.getDate("yyyyMMddHHmmss");//请求时间，yyyyMMddHHmmss
        String sign = "";//签名结果
        String key = unionPayProperties.getQueryKey();//商户密钥 秘钥请使用对应类型的支付秘钥


        //拼接签名串
        String sign1 = "merchantId="+merchantId+
                "&merchantOrderNo="+merchantOrderNo+
                "&requestTime="+requestTime+
                "&version="+version+
                "&key="+key;

        System.out.println("签名参数："+sign1);
        //对签名参数进行MD5加密得到sign
        sign = SmallTools.MD5en(sign1);
        //拼接请求参数
        String parameter = "merchantId="+merchantId+
                "&merchantOrderNo="+merchantOrderNo+
                "&version="+version+
                "&requestTime="+requestTime+
                "&sign="+sign;
        System.out.println("请求参数："+parameter);
        String ret = HttpUtil.sendPost(url,parameter);
        System.out.println("返回的数据："+ret);
        SmallTools.checkSign(ret,key);

        //封装返回的数据
        Map<String,Object> params = null;
        try {
            params = (JSONUtils.json2map(ret));
        } catch (Exception e) {
            e.printStackTrace();
        }
        UnionPayInfoDTO unionPayInfoDTO = new UnionPayInfoDTO();
        unionPayInfoDTO.setResult(String.valueOf(params.get("result")));
        unionPayInfoDTO.setPayAmount((String)params.get("amount"));
        unionPayInfoDTO.setMerchantId(String.valueOf(params.get("merchantId")));
        unionPayInfoDTO.setTransNo((String)params.get("transNo"));
        unionPayInfoDTO.setMerchantOrderNo((String)params.get("merchantOrderNo"));
        unionPayInfoDTO.setRetMsg((String)params.get("retMsg"));
        return unionPayInfoDTO;
    }

    @Override
    public UnionPayInfoDTO findByParams(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public UnionPayInfoDTO findByMerchantOrderNoEquals(String merchantOrderNo) {
        return unionPayInfoMapper.toDto(unionPayInfoRepository.findByMerchantOrderNoEquals(merchantOrderNo));
    }

    @Override
    public UnionPayInfoDTO save(UnionPayInfoDTO unionPayInfoDTO) {
        return unionPayInfoMapper.toDto(unionPayInfoRepository.save(unionPayInfoMapper.toEntity(unionPayInfoDTO)));
    }
}

