package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.AllotRuleDTO;
import com.chengma.devplatform.service.dto.FundRecordDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 */
public interface FundRecordService {

    Page<FundRecordDTO> pageList(HashMap<String, Object> params);

    FundRecordDTO save(FundRecordDTO hppVideoDTO);

    FundRecordDTO createFundRecordDTO(FundRecordDTO hppVideoDTO);

    HashMap<String, Object> checkCreateFundRecordDTO(FundRecordDTO hppVideoDTO);

    FundRecordDTO findOne(String id);

    List<FundRecordDTO> findAll();

    /*
    for app 基金入伙记录
     */
    HashMap<String, Object> findListByFundSignalId(String fundSignalId);

    /*
    我的入伙记录
     */
    List<FundRecordDTO> findMyFund(String userId);
    
    void delete(String id);

    /*
    计算总入伙金额
     */
    Double sumFundByFundSignalId(String id);
}

