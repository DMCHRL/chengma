package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.SysMenu;
import com.chengma.devplatform.service.dto.SysMenuDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysMenu and its DTO SysMenuDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysMenuMapper extends EntityMapper <SysMenuDTO, SysMenu> {
    
//    default SysMenu fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysMenu sysMenu = new SysMenu();
//        sysMenu.setId(id);
//        return sysMenu;
//    }
}
