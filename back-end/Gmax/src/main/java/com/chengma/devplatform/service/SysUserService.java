package com.chengma.devplatform.service;

import com.chengma.devplatform.domain.SysRole;
import com.chengma.devplatform.service.dto.SysUserDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Service Interface for managing SysUser.
 *
 * @author administrator
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
     * 获取所有的账户
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{}}
     * @return Page
     */
    Page<SysUserDTO> findAll(HashMap<String, Object> params);

    /**
     * Get the "id" sysUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SysUserDTO findOne(String id);

    /**
     * Delete the "id" sysUser.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * 获取用户角色
     *
     * @param params 参数：{userId:""}
     * @return Map
     */
    Map<String, Collection<SysRole>> getSysUserRoles(HashMap<String, Object> params);
}
