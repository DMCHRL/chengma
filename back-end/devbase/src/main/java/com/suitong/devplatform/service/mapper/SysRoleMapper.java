package com.suitong.devplatform.service.mapper;

import com.suitong.devplatform.domain.SysRole;
import com.suitong.devplatform.service.dto.SysRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity SysRole and its DTO SysRoleDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysRoleMapper extends EntityMapper <SysRoleDTO, SysRole> {
    
    default SysRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        return sysRole;
    }
}
