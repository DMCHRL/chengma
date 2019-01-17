package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.SysComponentDTO;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysComponent.
 *
 * @author administrator
 */
public interface SysComponentService {

    /**
     * Save a sysComponent.
     *
     * @param sysComponentDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String, Object> save(SysComponentDTO sysComponentDTO);

    /**
     * 根据表单id获取他下面的控件
     *
     * @param formId  表单id
     * @param visible 是否可见
     * @return List
     */
    List<SysComponentDTO> queryComponentPage(String formId, String visible);

    /**
     * Get the "id" sysComponent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SysComponentDTO findOne(String id);

    /**
     * Delete the "id" sysComponent.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    void deleteSysRoleComponent(String id);
}
