package com.suitong.devplatform.service;

import com.suitong.devplatform.service.dto.SysComponentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface SysComponentService {

    /**
     * Save a sysComponent.
     *
     * @param sysComponentDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(SysComponentDTO sysComponentDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SysComponentDTO> findAll(Pageable pageable);

    List<SysComponentDTO> queryComponentPage(Long formId,String visible);

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysComponentDTO findOne(Long id);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void deleteSysRoleComponent(Long id);
}
