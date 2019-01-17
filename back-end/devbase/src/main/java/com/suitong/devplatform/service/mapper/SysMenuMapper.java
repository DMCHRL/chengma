package com.suitong.devplatform.service.mapper;

import com.suitong.devplatform.domain.SysMenu;
import com.suitong.devplatform.service.dto.SysMenuDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysMenu and its DTO SysMenuDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysMenuMapper extends EntityMapper <SysMenuDTO, SysMenu> {
    
    
    default SysMenu fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        return sysMenu;
    }
}
