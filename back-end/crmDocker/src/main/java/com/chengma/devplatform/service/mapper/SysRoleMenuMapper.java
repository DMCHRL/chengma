package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.SysRoleMenu;
import com.chengma.devplatform.service.dto.SysRoleMenuDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysRoleMenu and its DTO SysRoleMenuDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysRoleMenuMapper extends EntityMapper <SysRoleMenuDTO, SysRoleMenu> {

//    default SysRoleMenu fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysRoleMenu sysRoleMenu = new SysRoleMenu();
//        sysRoleMenu.setId(id);
//        return sysRoleMenu;
//    }
}
