package com.suitong.devplatform.service;

import com.suitong.devplatform.service.dto.TlbUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbUserService {

    /**
     * Save a sysComponent.
     *
     * @param tlbUserDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(TlbUserDTO tlbUserDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TlbUserDTO> pageList(Pageable pageable);

    List<TlbUserDTO> findAll(HashMap<String, Object> params);

    List<TlbUserDTO> queryComponentPage(Long formId, String visible);

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbUserDTO findOne(Long id);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void deleteSysRoleComponent(Long id);
}
