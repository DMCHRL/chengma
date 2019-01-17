package com.suitong.devplatform.service;

import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.service.dto.MenuTreeNode;
import com.suitong.devplatform.service.dto.SysMenuDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysMenu.
 */
public interface SysMenuService {

    /**
     * Save a sysMenu.
     *
     * @param sysMenuDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(SysMenuDTO sysMenuDTO);

    /**
     *  Get all the sysMenus.
     *
     *  @return the list of entities
     */
    ResponseResult findAll(HashMap<String,Object> params);

    List<SysMenuDTO> getAllSysMenu(HashMap<String,Object> params);

    /**
     *  Get the "id" sysMenu.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysMenuDTO findOne(Long id);

    /**
     *  Delete the "id" sysMenu.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<MenuTreeNode> getMenuTreeList(HashMap<String,Object> params);

    void deleteSysRoleMenu(Long id);

    void deleteChildrenMenu(Long id);

    HashMap<String,Object> enableSysMenu(HashMap<String, Object> params);

    void deleteFormComByMenuId(Long id);
}
