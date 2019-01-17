package com.chengma.devplatform.service;



import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.TranInfoDTO;

import java.util.HashMap;

/**
 * Service Interface for managing TranInfo.
 */
public interface TranInfoService {

    /**
     * Save
     */
    ResponseResult save(TranInfoDTO tranInfoDTO);

    /**
     * findAll
     */
    HashMap<String, Object> findAll(HashMap<String, Object> params);

    /**
     * Get
     */
    TranInfoDTO findOne(String id);

    /**
     * findByCode
     */
    TranInfoDTO findByCode(TranInfoDTO tranInfoDTO);


    TranInfoDTO findByParams(HashMap<String, Object> params);

    /**
     * delete
     */
    void delete(String id);
}
