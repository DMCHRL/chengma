package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.SysComponent;
import com.suitong.devplatform.service.dto.SysComponentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity SysComponent and its DTO SysComponentDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysComponentMapper extends EntityMapper <SysComponentDTO, SysComponent> {
    
    
    default SysComponent fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysComponent sysComponent = new SysComponent();
        sysComponent.setId(id);
        return sysComponent;
    }
}
