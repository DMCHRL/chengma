package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.SysComponent;
import com.chengma.devplatform.service.dto.SysComponentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysComponent and its DTO SysComponentDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysComponentMapper extends EntityMapper <SysComponentDTO, SysComponent> {
    
//    default SysComponent fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysComponent sysComponent = new SysComponent();
//        sysComponent.setId(id);
//        return sysComponent;
//    }
}
