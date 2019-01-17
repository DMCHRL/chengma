package com.chengma.devplatform.service;

import com.chengma.devplatform.common.dao.ResponseResult;
import com.chengma.devplatform.service.dto.*;

import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysRole.
 *
 * @author administrator
 */
public interface SysRoleService {

    /**
     * Save a sysRole.
     *
     * @param sysRoleDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String, Object> save(SysRoleDTO sysRoleDTO);

    /**
     * 获取角色page
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{roleName:"",roleNo:"",delFlag:"",description:""}}
     * @return ResponseResult
     */
    ResponseResult findAll(HashMap<String, Object> params);

    /**
     * Get the "id" sysRole.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SysRoleDTO findOne(String id);

    /**
     * Delete the "id" sysRole.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * 获取该角色授权菜单。
     *
     * @param params 参数：{roleId:""}
     * @return HashMap
     */
    HashMap<String, Object> allocatePrivilegeMenuTree(HashMap<String, Object> params);

    /**
     * 根据角色和菜单获取所有表单以及被勾选的表单
     *
     * @param roleIds 角色id，id之间用“,”隔开
     * @param menuIds 菜单id，id之间用“,”隔开
     * @return List
     */
    List<SysFormDTO> getFormsByRoleAndMenu(String roleIds, String menuIds);

    /**
     * 获取所有控件以及被勾选的控件按钮
     *
     * @param roleIds 角色id，id之间用“,”隔开
     * @param formId 表单id
     * @return List
     */
    List<SysComponentDTO> getComponentsByRoleAndForm(String roleIds, String formId);

    /**
     * 根据页面选中的菜单，表单，控件，保存用户权限
     *
     * @param params 参数：{menuList：{},formList:{},componentList:{},roleId:""}
     * @return HashMap
     */
    HashMap<String, Object> saveRolePrivilege(HashMap<String, Object> params);

    /**
     * 启用、禁用角色。
     *
     * @param params 参数：{roleId:"",delFlag:""}
     * @return HashMap
     */
    HashMap<String, Object> enableSysRole(HashMap<String, Object> params);

    /**
     * 删除角色时，同时删除所有相关的权限信息
     *
     * @param roleId 角色id
     */
    void deletePrivilegeByRole(String roleId);

    /**
     * 根据角色以及菜单id，获取该菜单下已授权给角色的表单页面
     *
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return List
     */
    List<SysRoleFormDTO> queryFormPrivilege(String roleId, String menuId);

    /**
     * 根据角色，表单id，获取他下面所授权的控件
     *
     * @param roleId 角色id
     * @param formId 表单id
     * @return List
     */
    List<SysRoleComponentDTO> queryComponentPrivilege(String roleId, String formId);

    /**
     * 通过登录用户以及所选菜单，得到授权显示页面和按钮。
     *
     * @param params 参数：{menuId:""}
     * @return HashMap
     */
    HashMap<String, Object> getFormRightList(HashMap<String, Object> params);

    /**
     * 判断角色编号是否已经存在。
     *
     * @param params 参数：{roleNo:""}
     * @return 同名角色编号的数量
     */
    int checkRoleNo(HashMap<String, Object> params);

    /**
     * 获取菜单树形结构
     *
     * @param params 参数：{visible:"",menuName:"",parentId:""}
     * @return List
     */
    List<MenuTreeNode> getMenuRightList(HashMap<String, Object> params);

}
