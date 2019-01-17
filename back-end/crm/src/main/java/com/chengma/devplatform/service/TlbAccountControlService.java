package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbAccountControlDTO;
import com.chengma.devplatform.service.dto.TlbAccountDTO;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbAccountControlService {

    /**
     * Save
     */
    TlbAccountDTO save(TlbAccountControlDTO tlbAccountControlDTO);


    //获取关联账号
    public List<TlbAccountDTO> loadRelationAccount(String userId) ;


    HashMap<String,Object> checkTlbAccountControlDTO(TlbAccountControlDTO tlbAccountControlDTO);
    /**
     * delete
     */
    void delete(String id);

    /**
     * 更改密码时，删除对应账号关联
     * @param account
     */
    void deleteByAccount(String account, String trade);


    /**
     * 根據賬號密碼類型查詢
     * @param userId
     * @param account
     * @param trade
     * @return
     */
    TlbAccountControlDTO findByUserIdEqualsAndAccountEqualsAndTradeEquals(String userId, String account, String trade);

}
