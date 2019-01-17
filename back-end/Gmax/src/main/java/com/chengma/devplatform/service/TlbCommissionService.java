package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbCommissionDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbCommissionService {

    /**
     * Save
     */
    TlbCommissionDTO save(TlbCommissionDTO tlbCommissionDTO);

    /**
     * findAll
     */
    List<TlbCommissionDTO> findAll(HashMap<String, Object> params);


    /**
     *
     * @param params
     * @return
     */
    Page<TlbCommissionDTO> pageList(HashMap<String, Object> params);

    /**
     * Get
     */
    TlbCommissionDTO findOne(String id);

    /**
     * findByCode
     */
    TlbCommissionDTO findByCode(TlbCommissionDTO tlbCommissionDTO);

    TlbCommissionDTO findByCommission(String account);

    TlbCommissionDTO updateCommissionMargin(String account, double lots);

    TlbCommissionDTO updateCommissionBalance(String account, double amount);

    boolean checkCommissionAmount(String account, double amount);

    HashMap<String, Object> checkInner(HashMap<String, Object> params);

    TlbCommissionDTO withdrawals(HashMap<String, Object> params);

    TlbCommissionDTO recharge(HashMap<String, Object> params);
    
    
    public TlbCommissionDTO findByUserId(String userId);

    /**
     * findByCode
     */
    TlbCommissionDTO findOneByParam(HashMap<String, Object> params);
    /**
     * delete
     */
    void delete(String id);
    
    /**
     * set
     */
    TlbCommissionDTO setConfiguration(TlbCommissionDTO tlbCommissionDTO);

    /**
     * get
     * @param userId
     * @return
     */
    TlbCommissionDTO getConfiguration(String userId);
    
    /**
     * update
     */
    void updateTotal(String accountId,double lots,Long orderNo);

    void updateCommission();
}
