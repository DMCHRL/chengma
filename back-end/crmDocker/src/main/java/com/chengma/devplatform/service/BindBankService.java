package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.BindBankDTO;

import java.util.HashMap;

/**
 * Service Interface for managing BindBank.
 */
public interface BindBankService {

    /**
     * Save
     */
    BindBankDTO save(BindBankDTO bindBankDTO);

    /**
     * findAll
     */
    HashMap<String, Object> findAll(HashMap<String, Object> params);

    /**
     * Get
     */
    BindBankDTO findOne(String id);

    /**
     * findByCode
     */
    BindBankDTO findByCode(BindBankDTO bindBankDTO);

    /**
     * findByCode
     */
    BindBankDTO findOneByParam(HashMap<String, Object> params);
    /**
     * delete
     */
    void delete(String id);
}
