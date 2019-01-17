package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbHistoryKDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbHistoryKService {

    /**
     * Save a sysComponent.
     *
     * @param tlbHistoryKDTO the entity to save
     * @return the persisted entity
     */
    TlbHistoryKDTO save(TlbHistoryKDTO tlbHistoryKDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<TlbHistoryKDTO> pageList(HashMap<String, Object> params);


    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbHistoryKDTO findOne(String id);


    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);



}
