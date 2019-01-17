package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbTradeDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing BindBank.
 */
public interface TlbTradeService {

    /**
     * Save
     */
    TlbTradeDTO save(TlbTradeDTO tlbTradeDTO);

    /**
     * findAll
     */
    List<TlbTradeDTO> findAll(HashMap<String, Object> params);

    /**
     *
     * @param params
     * @return
     */
    Page<TlbTradeDTO> pageList(HashMap<String, Object> params);

    /**
     * Get
     */
    TlbTradeDTO findOne(String id);

    /**
     * findByCode
     */
    TlbTradeDTO findByCode(TlbTradeDTO tlbTradeDTO);

    /**
     * findByCode
     */
    TlbTradeDTO findOneByParam(HashMap<String, Object> params);
    /**
     * delete
     */
    void delete(String id);

    public HashMap<String, Object> checkLotsTrade(TlbTradeDTO tlbTradeDTO);

    public List<TlbTradeDTO> openTlbTrads(String account);

    public List<TlbTradeDTO> closedTlbTrads(String account);
    
    /**
     * findWithClosePosition
     * @return
     */
    public  List<TlbTradeDTO> findTlbTradesWithClosePosition();
}
