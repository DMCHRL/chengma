package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.SysRoleForm;
import com.chengma.devplatform.service.dto.SysRoleFormDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysRoleForm and its DTO SysRoleFormDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysRoleFormMapper extends EntityMapper <SysRoleFormDTO, SysRoleForm> {

//    default SysRoleForm fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysRoleForm sysRoleForm = new SysRoleForm();
//        sysRoleForm.setId(id);
//        return sysRoleForm;
//    }
}
