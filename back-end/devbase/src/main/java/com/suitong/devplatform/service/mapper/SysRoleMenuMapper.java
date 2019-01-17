package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.SysRoleMenu;
import com.suitong.devplatform.service.dto.SysRoleMenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity SysRoleMenu and its DTO SysRoleMenuDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {SysRoleMapper.class, SysMenuMapper.class, })
public interface SysRoleMenuMapper extends EntityMapper <SysRoleMenuDTO, SysRoleMenu> {

    @Mapping(source = "sysRole.id", target = "sysRoleId")

    @Mapping(source = "sysMenu.id", target = "sysMenuId")
    SysRoleMenuDTO toDto(SysRoleMenu sysRoleMenu);

    @Mapping(source = "sysRoleId", target = "sysRole")

    @Mapping(source = "sysMenuId", target = "sysMenu")
    SysRoleMenu toEntity(SysRoleMenuDTO sysRoleMenuDTO); 
    default SysRoleMenu fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setId(id);
        return sysRoleMenu;
    }
}
