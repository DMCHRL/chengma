package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbDataDTO;
import com.chengma.devplatform.service.dto.TlbTradeDTO;
import com.chengma.devplatform.service.dto.TradeDataDTO;
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
     * 从manage中批量保存
     * Save
     */
    void saveList(List<TlbTradeDTO> tlbTradeDTOList);



    /**
     * findAll
     */
    List<TlbTradeDTO> findAll();

    /**
     *
     * @param params
     * @return
     */
    HashMap<String, Object>  pageList(HashMap<String, Object> params);

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

    /**
     * 统计用户数据
     * @return
     */
    public TlbDataDTO getTlbData();


    //收益率
    List<TradeDataDTO> getTlbTradeProfit(HashMap<String, Object> params);

    //账号胜率
    TradeDataDTO getTlbTradeWin(String account);

    List<TradeDataDTO> getTlbTradeSymbol(String account);

}
