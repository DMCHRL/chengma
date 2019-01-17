package com.chengma.devplatform.service;


import com.chengma.devplatform.service.dto.TlbAccountDTO;
import com.chengma.devplatform.service.dto.TlbAccountDataDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface TlbAccountDataService {

    Page<TlbAccountDataDTO> pageList(HashMap<String, Object> params);

    TlbAccountDataDTO save(TlbAccountDataDTO tlbAccountDataDTO);

    TlbAccountDataDTO createTlbAccountDataDTO(TlbAccountDataDTO tlbAccountDataDTO);

    HashMap<String, Object> checkCreateTlbAccountDataDTO(TlbAccountDataDTO tlbAccountDataDTO);

    TlbAccountDataDTO findOne(String id);

    List<TlbAccountDataDTO> findAll();

    void delete(String id);
    /**
     * 读取策略数据
     * @param account
     * @return
     */
    TlbAccountDataDTO findByAccount(String account);

    /**
     * 读取账号数据
     * @param account
     * @return
     */
    TlbAccountDataDTO loadByAccount(String account);

    TlbAccountDataDTO initTlbAccountDataDTO(String account);


    /**
     * 删除账号数据
     * @param account
     */
    void deleteByAccount(String account);
}
