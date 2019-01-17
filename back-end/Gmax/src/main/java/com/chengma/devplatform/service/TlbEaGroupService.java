package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.TlbEaGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbEaGroupService {

    /**
     * Save a sysComponent.
     *
     * @param tlbEaGroupDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(TlbEaGroupDTO tlbEaGroupDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param params the pagination information
     *  @return the list of entities
     */
    Page<TlbEaGroupDTO> pageList(HashMap<String, Object> params);

    List<TlbEaGroupDTO> findAll();

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbEaGroupDTO findOne(String id);


    /**
     * Save a sysUser.
     *
     * @param TlbEaGroupDTO the entity to save
     * @return the persisted entity
     */
    TlbEaGroupDTO update(TlbEaGroupDTO TlbEaGroupDTO);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

}
