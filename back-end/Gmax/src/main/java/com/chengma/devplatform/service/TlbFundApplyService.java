package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbFundApplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing TlbFundApply.
 */
public interface TlbFundApplyService {

    /**
     * Save
     */
    TlbFundApplyDTO save(TlbFundApplyDTO bindBankDTO);

    /**
     * findAll
     */
    List<TlbFundApplyDTO> findAll(HashMap<String, Object> params);

    /**
     *
     * @param params
     * @return
     */
    Page<TlbFundApplyDTO> pageList(HashMap<String, Object> params);

    /**
     * Get
     */
    TlbFundApplyDTO findOne(String id);


    List<TlbFundApplyDTO> findTlbFundApplyList(String account, String fundType, String status);


    List<TlbFundApplyDTO> findTlbFundHistoryList(String account, String status);

    /**
     * delete
     */
    void delete(String id);

    /**
     * 出金申请
     * @param params
     * @return
     */
    TlbFundApplyDTO withdrawals(HashMap<String, Object> params);

    /**
     * 入金申请
     * @param params
     * @return
     */
    TlbFundApplyDTO recharge(HashMap<String, Object> params);

    /**
     * 检查入金额度
     * @param params
     * @return
     */
    HashMap<String, Object> checkRecharge(HashMap<String, Object> params);


    /**
     * 检查出金额度
     * @param params
     * @return
     */
    HashMap<String, Object> checkWithdrawals(HashMap<String, Object> params);
    /**
     * 检查內轉额度
     * @param params
     * @return
     */
    HashMap<String, Object> checkInner(HashMap<String, Object> params);

    /**
     * 批核入金
     * @param id
     * @return
     */
    TlbFundApplyDTO passInner(String id);

    /**
     * 拒绝入金
     * @param id
     * @return
     */
    TlbFundApplyDTO rejectInner(String id);

    /**
     * 批核入金
     * @param id
     * @return
     */
    TlbFundApplyDTO passRecharge(String id);
    /**
     * 拒绝入金
     * @param id
     * @return
     */
    TlbFundApplyDTO rejectRecharge(String id);
    /**
     * 批核出金
     * @param id
     * @return
     */
    TlbFundApplyDTO passWithdrawals(String id);
    /**
     * 拒绝出金
     * @param id
     * @return
     */
    TlbFundApplyDTO rejectWithdrawals(String id);

    /**
     * 出金申请
     * @param params
     * @return
     */

    /**
     * 批核入金/出金/内转
     * @param id
     * @return
     */
    TlbFundApplyDTO pass(String id);

    /**
     * 檢查審核人身份
     * @return
     */
    HashMap<String,Object> checkPassOrReject();

    /**
     * 拒绝入金/出金/内转
     * @param id
     * @return
     */
    TlbFundApplyDTO reject(String id);

    TlbFundApplyDTO inner(HashMap<String, Object> params);
}
