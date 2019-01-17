package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.SysRole;
import com.chengma.devplatform.service.dto.SysRoleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysRole and its DTO SysRoleDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysRoleMapper extends EntityMapper <SysRoleDTO, SysRole> {
    
//    default SysRole fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysRole sysRole = new SysRole();
//        sysRole.setId(id);
//        return sysRole;
//    }
}
