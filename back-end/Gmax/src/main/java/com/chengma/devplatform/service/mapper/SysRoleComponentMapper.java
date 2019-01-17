package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.SysRoleComponent;
import com.chengma.devplatform.service.dto.SysRoleComponentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysRoleComponent and its DTO SysRoleComponentDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysRoleComponentMapper extends EntityMapper <SysRoleComponentDTO, SysRoleComponent> {

//    default SysRoleComponent fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysRoleComponent sysRoleComponent = new SysRoleComponent();
//        sysRoleComponent.setId(id);
//        return sysRoleComponent;
//    }
}
