package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppSinksDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppSinksService {

    /**
     * Save a sysComponent.
     *
     * @param hppSinksDTO the entity to save
     * @return the persisted entity
     */
    HppSinksDTO save(HppSinksDTO hppSinksDTO);

    HashMap<String,Object> checkSave(HppSinksDTO hppSinksDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<HppSinksDTO> pageList(HashMap<String, Object> params);

    /**
     * 加载到app列表
     * @return
     */
    List<HppSinksDTO> findList();


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppSinksDTO findOne(String id);


    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);



}
