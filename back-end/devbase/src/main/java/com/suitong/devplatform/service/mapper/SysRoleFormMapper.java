package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.SysRoleForm;
import com.suitong.devplatform.service.dto.SysRoleFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity SysRoleForm and its DTO SysRoleFormDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {SysRoleMapper.class, SysFormMapper.class, })
public interface SysRoleFormMapper extends EntityMapper <SysRoleFormDTO, SysRoleForm> {

    @Override
    @Mapping(source = "sysRole.id", target = "sysRoleId")

    @Mapping(source = "sysForm.id", target = "sysFormId")
    SysRoleFormDTO toDto(SysRoleForm sysRoleForm);

    @Override
    @Mapping(source = "sysRoleId", target = "sysRole")

    @Mapping(source = "sysFormId", target = "sysForm")
    SysRoleForm toEntity(SysRoleFormDTO sysRoleFormDTO); 
    default SysRoleForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysRoleForm sysRoleForm = new SysRoleForm();
        sysRoleForm.setId(id);
        return sysRoleForm;
    }
}
