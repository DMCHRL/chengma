package com.suitong.devplatform.service;

import com.suitong.devplatform.service.dto.TlbRateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 */
public interface TlbRateService {

    /**
     * Save a sysComponent.
     *
     * @param tlbRateDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(TlbRateDTO tlbRateDTO);

    /**
     *  Get all the sysComponents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TlbRateDTO> pageList(Pageable pageable);

    List<TlbRateDTO> findAll(HashMap<String, Object> params);

    List<TlbRateDTO> queryComponentPage(Long formId, String visible);

    /**
     *  Get the "id" sysComponent.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TlbRateDTO findOne(Long id);


    /**
     * Save a sysUser.
     *
     * @param TlbRateDTO the entity to save
     * @return the persisted entity
     */
    TlbRateDTO update(TlbRateDTO TlbRateDTO);

    /**
     *  Delete the "id" sysComponent.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void deleteSysRoleComponent(Long id);
}
