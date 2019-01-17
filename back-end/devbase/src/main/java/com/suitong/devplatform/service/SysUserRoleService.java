package com.suitong.devplatform.service;

import com.suitong.devplatform.service.dto.SysRoleDTO;
import com.suitong.devplatform.service.dto.SysUserRoleDTO;
import com.suitong.devplatform.service.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
public interface SysUserRoleService {

    Page<UserDTO> findAll(HashMap<String,Object> params);

    void saveOrDeleteUserRole(HashMap<String,Object> params);

    SysUserRoleDTO findSysUserRoleByUserIdAndRoleId(Long userId, Long roleId);

    List<SysUserRoleDTO> getRoleIdsByUser(Long userId);

    List<SysRoleDTO> getRoleByUserId(Long userId, String type);

    void saveUserRole(HashMap<String,Object> params);
}
