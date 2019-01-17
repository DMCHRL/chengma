package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.SysForm;
import com.chengma.devplatform.service.dto.SysFormDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SysForm and its DTO SysFormDTO.
 * @author ddgui
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysFormMapper extends EntityMapper <SysFormDTO, SysForm> {

//    default SysForm fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        SysForm sysForm = new SysForm();
//        sysForm.setId(id);
//        return sysForm;
//    }
}
