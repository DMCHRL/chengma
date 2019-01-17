package com.chengma.devplatform.service;



import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.domain.UnionPayInfo;
import com.chengma.devplatform.service.dto.UnionPayInfoDTO;

import java.util.HashMap;

/**
 * Service Interface for managing UnionPay.
 */
public interface UnionPayInfoService {

    /**
     * Save
     */
    UnionPayInfoDTO save(UnionPayInfoDTO unionPayInfoDTO);

    UnionPayInfoDTO createOrder(UnionPayInfoDTO unionPayInfoDTO);

    HashMap<String, Object> checkSave(UnionPayInfoDTO unionPayInfoDTO);

    /**
     * findAll
     */
    HashMap<String, Object> findAll(HashMap<String, Object> params);

    /**
     * Get
     */
    UnionPayInfoDTO findOne(String id);

    /**
     * 根据订单号获取实体
     * @param merchantOrderNo
     * @return
     */
    UnionPayInfoDTO findByMerchantOrderNoEquals(String merchantOrderNo);

    /**
     * findByCode
     */
    UnionPayInfoDTO findByCode(UnionPayInfoDTO unionPayInfoDTO);

    UnionPayInfoDTO findUnionPayByTransactionId(String transactionId);


    UnionPayInfoDTO findByParams(HashMap<String, Object> params);

    /**
     * delete
     */
    void delete(String id);
}
