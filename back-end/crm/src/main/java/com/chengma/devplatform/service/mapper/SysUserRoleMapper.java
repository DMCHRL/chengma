package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.SysUserRole;
import com.chengma.devplatform.service.dto.SysUserRoleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysUserRole and its DTO SysUserRoleDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysUserRoleMapper extends EntityMapper <SysUserRoleDTO, SysUserRole> {

//    default SysUserRole fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysUserRole sysUserRole = new SysUserRole();
//        sysUserRole.setId(id);
//        return sysUserRole;
//    }
}
