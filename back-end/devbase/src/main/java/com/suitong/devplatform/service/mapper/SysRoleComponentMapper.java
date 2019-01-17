package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.SysRoleComponent;
import com.suitong.devplatform.service.dto.SysRoleComponentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity SysRoleComponent and its DTO SysRoleComponentDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {SysRoleMapper.class, SysComponentMapper.class, })
public interface SysRoleComponentMapper extends EntityMapper <SysRoleComponentDTO, SysRoleComponent> {

    @Override
    @Mapping(source = "sysRole.id", target = "sysRoleId")

    @Mapping(source = "sysComponent.id", target = "sysComponentId")
    SysRoleComponentDTO toDto(SysRoleComponent sysRoleComponent);

    @Override
    @Mapping(source = "sysRoleId", target = "sysRole")

    @Mapping(source = "sysComponentId", target = "sysComponent")
    SysRoleComponent toEntity(SysRoleComponentDTO sysRoleComponentDTO); 
    default SysRoleComponent fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysRoleComponent sysRoleComponent = new SysRoleComponent();
        sysRoleComponent.setId(id);
        return sysRoleComponent;
    }
}
