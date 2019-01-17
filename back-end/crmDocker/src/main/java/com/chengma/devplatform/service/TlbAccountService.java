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
     * findByCode
     */
    TlbAccountDTO findOneByParam(HashMap<String, Object> params);
    /**
     * delete
     */
    void delete(String id);

    public String genAccountNo(String group);

    /**
     * 修改密码
     * @param params
     * @return
     */
    public TlbAccountDTO editPassword(HashMap<String, Object> params);

    public String tradeStr();
}
