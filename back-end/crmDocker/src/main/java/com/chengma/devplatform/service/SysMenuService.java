package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.MenuTreeNode;
import com.chengma.devplatform.service.dto.SysMenuDTO;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysMenu.
 *
 * @author administrator
 */
public interface SysMenuService {

    /**
     * Save a sysMenu.
     *
     * @param sysMenuDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String, Object> save(SysMenuDTO sysMenuDTO);

    /**
     * Get all the sysMenus.
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{queryTerm:"",parentId:""}}
     * @return the list of entities
     */
    ResponseResult findAll(HashMap<String, Object> params);

    /**
     * 获取菜单列表
     *
     * @param params 参数：{}
     * @return List
     */
    List<SysMenuDTO> getAllSysMenu(HashMap<String, Object> params);

    /**
     * Get the "id" sysMenu.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SysMenuDTO findOne(String id);

    /**
     * Delete the "id" sysMenu.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * 获取树状菜单
     *
     * @param params 参数：{visible:"",menuName:"",parentId:""}
     * @return List
     */
    List<MenuTreeNode> getMenuTreeList(HashMap<String, Object> params);

    /**
     * 删除角色菜单
     *
     * @param id 菜单id
     */
    void deleteSysRoleMenu(String id);

    /**
     * 删除子菜单
     *
     * @param id 菜单id
     */
    void deleteChildrenMenu(String id);

    /**
     * 启用、禁用菜单
     *
     * @param params 参数：{menuId:"",visible:""}
     * @return HashMap
     */
    HashMap<String, Object> enableSysMenu(HashMap<String, Object> params);

    /**
     * 删除表单和组件
     *
     * @param id 菜单id
     */
    void deleteFormComByMenuId(String id);
}
