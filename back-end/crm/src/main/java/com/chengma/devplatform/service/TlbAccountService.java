package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbAccountService {

    /**
     * Save
     */
    TlbAccountDTO save(TlbAccountDTO tlbAccountDTO);


    TlbAccountDTO createCrmTlb(HashMap<String, Object> params);

    HashMap<String, Object> checkAccountInfo(HashMap<String, Object> params);

    HashMap<String, Object> checkCrmMobile(HashMap<String, Object> params);

    /**
     * findAll
     */
    List<TlbAccountDTO> findAll(HashMap<String, Object> params);


    /**
     * findEmptyAccounts
     */
    List<TlbAccountDTO> findEmptyAccounts(String userId);

    /**
     * 获取关联账号
     */
    List<TlbAccountDTO> findByUserId(String userId);

    /**
     *
     * @param params
     * @return
     */
    Page<TlbAccountDTO> pageList(HashMap<String, Object> params);

    /**
     * Get
     */
    TlbAccountDTO findOne(String id);

    /**
     * findByCode
     */
    TlbAccountDTO findByCode(TlbAccountDTO tlbAccountDTO);

    TlbAccountDTO findByAccount(String account);

    TlbAccountDTO updateAccountMargin(String account, double lots);

    TlbAccountDTO updateAccountBalance(String account, double amount);

    boolean checkAccountAmount(String account, double amount);

    HashMap<String, Object> checkWithdrawals(HashMap<String, Object> params);

    TlbAccountDTO withdrawals(HashMap<String, Object> params);

    TlbAccountDTO recharge(HashMap<String, Object> params);

    TlbAccountDTO findByAccountAndPassword(String account, String password);

    /**
     * 根據觀摩密碼找賬戶
     * @param account
     * @param seePassword
     * @return
     */
    TlbAccountDTO findByAccountEqualsAndSeePasswordEquals(String account, String seePassword);

    /**
     * findByCode
     */
    TlbAccountDTO findOneByParam(HashMap<String, Object> params);
    /**
     * delete
     */
    void delete(String id);

    public String genAccountNo(String group);

    /**
     * 修改交易密码
     * @param params
     * @return
     */
    public TlbAccountDTO editTradePassword(HashMap<String, Object> params);

    /**
     * 修改觀摩密码
     * @param params
     * @return
     */
    public TlbAccountDTO editSeePassword(HashMap<String, Object> params);

    public void resetPassword(String id);

    public String tradeStr();

    /**
     * 确认账号信息
     * @param tlbAccountDTO
     * @return
     */
    public HashMap<String,Object> confirmAccount(TlbAccountDTO tlbAccountDTO);
}
