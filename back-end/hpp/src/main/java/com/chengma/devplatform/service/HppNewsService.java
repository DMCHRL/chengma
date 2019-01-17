package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppNewsDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppNewsService {

    /**
     * Save a sysComponent.
     *
     * @param hppNewsDTO the entity to save
     * @return the persisted entity
     */
    HppNewsDTO save(HppNewsDTO hppNewsDTO);

    HashMap<String,Object> checkSave(HppNewsDTO hppNewsDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<HppNewsDTO> pageList(HashMap<String, Object> params);

    /**
     *  Get all the HppNewsDTO.
     *
     *  @return the list of entities
     */
    List<HppNewsDTO> findList();

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppNewsDTO findOne(String id);

    /**
     * 设置在app显示
     * @param id
     * @return
     */
    HppNewsDTO showOne(String id);

    /**
     *  阅读新闻
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppNewsDTO readOne(String id);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    void sendNews(String id);

}
