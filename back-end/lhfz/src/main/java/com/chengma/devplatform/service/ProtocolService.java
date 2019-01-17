package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.ProtocolDTO;
import com.chengma.devplatform.service.dto.ProtocolDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface ProtocolService {

    /**
     * Save a sysComponent.
     *
     * @param protocolDTO the entity to save
     * @return the persisted entity
     */
    ProtocolDTO save(ProtocolDTO protocolDTO);

    HashMap<String,Object> checkSave(ProtocolDTO protocolDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<ProtocolDTO> pageList(HashMap<String, Object> params);

    /**
     *  Get all the ProtocolDTO.
     *
     *  @return the list of entities
     */
    List<ProtocolDTO> findList();

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProtocolDTO findOne(String id);

    ProtocolDTO findType(String type);


    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);



}
