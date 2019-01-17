package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.HppProtocolDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface HppProtocolService {

    /**
     * Save a sysComponent.
     *
     * @param hppProtocolDTO the entity to save
     * @return the persisted entity
     */
    HppProtocolDTO save(HppProtocolDTO hppProtocolDTO);

    HashMap<String,Object> checkSave(HppProtocolDTO hppProtocolDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<HppProtocolDTO> pageList(HashMap<String, Object> params);

    /**
     *  Get all the HppProtocolDTO.
     *
     *  @return the list of entities
     */
    List<HppProtocolDTO> findList();

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    HppProtocolDTO findOne(String id);

    HppProtocolDTO findType(String type);


    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);



}
