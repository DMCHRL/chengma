package com.suitong.devplatform.service.mapper;

import com.suitong.devplatform.domain.SysUserRole;
import com.suitong.devplatform.service.dto.SysUserRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity SysUserRole and its DTO SysUserRoleDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {SysRoleMapper.class, UserMapper.class, })
public interface SysUserRoleMapper extends EntityMapper <SysUserRoleDTO, SysUserRole> {

    @Override
    @Mapping(source = "sysRole.id", target = "sysRolesId")
    @Mapping(source = "user.id", target = "sysUsersId")
    SysUserRoleDTO toDto(SysUserRole sysUserRole);

    @Override
    @Mapping(source = "sysRolesId", target = "sysRole")
    @Mapping(source = "sysUsersId", target = "user")
    SysUserRole toEntity(SysUserRoleDTO sysUserRoleDTO);

    default SysUserRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setId(id);
        return sysUserRole;
    }
}
