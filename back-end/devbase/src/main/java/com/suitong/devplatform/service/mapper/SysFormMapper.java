package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.SysForm;
import com.suitong.devplatform.service.dto.SysFormDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity SysForm and its DTO SysFormDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysFormMapper extends EntityMapper <SysFormDTO, SysForm> {
    
    
    default SysForm fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysForm sysForm = new SysForm();
        sysForm.setId(id);
        return sysForm;
    }
}
