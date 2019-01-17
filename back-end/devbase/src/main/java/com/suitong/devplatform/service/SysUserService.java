package com.suitong.devplatform.service;

import com.suitong.devplatform.domain.SysRole;
import com.suitong.devplatform.service.dto.SysUserDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Collection;
import java.util.Map;

/**
 * Service Interface for managing SysUser.
 */
public interface SysUserService {

    /**
     * Save a sysUser.
     *
     * @param SysUserDTO the entity to save
     * @return the persisted entity
     */
    SysUserDTO save(SysUserDTO SysUserDTO);

    /**
     *  Get all the sysUsers.
     *
     *  @return the list of entities
     */
    Page<SysUserDTO> findAll(HashMap<String,Object> params);

    /**
     *  Get the "id" sysUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysUserDTO findOne(Long id);

    /**
     *  Delete the "id" sysUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Map<String, Collection<SysRole>> getSysUserRoles(HashMap<String, Object> params);
}
