package com.suitong.devplatform.service;

import com.suitong.devplatform.service.dto.SysFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysForm.
 */
public interface SysFormService {

    /**
     * Save a sysForm.
     *
     * @param sysFormDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(SysFormDTO sysFormDTO);

    /**
     *  Get all the sysForms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SysFormDTO> findAll(Pageable pageable);

    List<SysFormDTO> queryFormPage(String menuIds,String visible);

    /**
     *  Get the "id" sysForm.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysFormDTO findOne(Long id);

    /**
     *  Delete the "id" sysForm.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    void deleteSysRoleForm(Long id);

    int checkFormEnglishName(HashMap<String, Object> params);
}
