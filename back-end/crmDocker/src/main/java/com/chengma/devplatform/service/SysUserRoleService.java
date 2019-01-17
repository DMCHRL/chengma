package com.chengma.devplatform.service;

import com.chengma.devplatform.service.dto.SysRoleDTO;
import com.chengma.devplatform.service.dto.SysUserRoleDTO;
import com.chengma.devplatform.service.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 *
 * @author administrator
 */
public interface SysUserRoleService {

    /**
     * 获取用户列表
     *
     * @param params 参数：{page_number:"",page_size:"",formParams{roleId:"",userName:""}}
     * @return Page
     */
    Page<UserDTO> findAll(HashMap<String, Object> params);

    /**
     * 保存用户角色关系
     *
     * @param params 参数：{sysRolesId:"",addSysUsersId:{},delSysUsersId:{}}
     */
    void saveOrDeleteUserRole(HashMap<String, Object> params);

    /**
     * 查找用户角色关系信息
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return SysUserRoleDTO
     */
    SysUserRoleDTO findSysUserRoleByUserIdAndRoleId(String userId, String roleId);

    /**
     * 根据用户查找用户角色关系信息
     *
     * @param userId 用户id
     * @return List
     */
    List<SysUserRoleDTO> getRoleIdsByUser(String userId);

    /**
     * 根据用户查找角色列表
     *
     * @param userId 用户id
     * @param type   类型：Y用户属于的角色，N用户不属于的角色
     * @return List
     */
    List<SysRoleDTO> getRoleByUserId(String userId, String type);

    /**
     * 保存用户角色关系
     *
     * @param params 参数：{userId:"",roleIds:{}}
     */
    void saveUserRole(HashMap<String, Object> params);
}
