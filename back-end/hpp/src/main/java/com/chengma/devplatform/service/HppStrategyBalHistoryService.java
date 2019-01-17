package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppStrategyBalHistoryDTO;
import com.chengma.devplatform.service.dto.HppStrategyViewDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppStrategyBalHistoryService {

    /**
     * Save a sysComponent.
     *
     * @param hppStrategyBalHistoryDTO the entity to save
     * @return the persisted entity
     */
    HppStrategyBalHistoryDTO save(HppStrategyBalHistoryDTO hppStrategyBalHistoryDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<HppStrategyBalHistoryDTO> pageList(HashMap<String, Object> params);


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppStrategyBalHistoryDTO findOne(String id);



    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    /**
     * 计算最大回撤
     */
    void countHistoryFundBack();

    List<HppStrategyBalHistoryDTO> findHistoryFundBack(String account,String type);

    /**
     * 增长率(app)
     * @param params
     * @return
     */
    List<HppStrategyViewDTO> findGrowthRate(HashMap<String,Object> params);

    /**
     * 收益率(app)
     * @param params
     * @return
     */
    List<HppStrategyViewDTO> findProfitRate(HashMap<String,Object> params);




}
