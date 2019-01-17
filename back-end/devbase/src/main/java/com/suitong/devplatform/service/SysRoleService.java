package com.suitong.devplatform.service;

import com.suitong.devplatform.common.dao.ResponseResult;
import com.suitong.devplatform.domain.SysForm;
import com.suitong.devplatform.domain.SysRole;
import com.suitong.devplatform.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Service Interface for managing SysRole.
 */
public interface SysRoleService {

    /**
     * Save a sysRole.
     *
     * @param sysRoleDTO the entity to save
     * @return the persisted entity
     */
    HashMap<String,Object> save(SysRoleDTO sysRoleDTO);

    /**
     *  Get all the sysRoles.
     *
     *  @return the list of entities
     */
    ResponseResult findAll(HashMap<String,Object> params);

    /**
     *  Get the "id" sysRole.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysRoleDTO findOne(Long id);

    /**
     *  Delete the "id" sysRole.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    HashMap<String, Object> allocatePrivilegeMenuTree(HashMap<String, Object> params);

    List<SysFormDTO> getFormsByRoleAndMenu(String roleIds, String menuIds);

    List<SysComponentDTO> getComponentsByRoleAndForm(String roleIds, Long menuId);

    HashMap<String,Object> saveRolePrivilege(HashMap<String, Object> params);

    HashMap<String,Object> enableSysRole(HashMap<String, Object> params);

    void deletePrivilegeByRole(Long roleId);

    List<SysRoleFormDTO> queryFormPrivilege(Long roleId, Long menuId);

    List<SysRoleComponentDTO> queryComponentPrivilege(Long roleId, Long formId);

    HashMap<String, Object> getFormRightList(HashMap<String, Object> params);

    int checkRoleNo(HashMap<String, Object> params);

    List<MenuTreeNode> getMenuRightList(HashMap<String,Object> params);

    public SysRole querySysRoleByNo(String roleNo);

}
