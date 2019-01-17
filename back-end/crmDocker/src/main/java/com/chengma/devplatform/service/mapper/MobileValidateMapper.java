package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.MobileValidate;
import com.chengma.devplatform.service.dto.MobileValidateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity MobileValidate and its DTO MobileValidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MobileValidateMapper extends EntityMapper <MobileValidateDTO, MobileValidate> {
    
//    default MobileValidate fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        MobileValidate mobileValidate = new MobileValidate();
//        mobileValidate.setId(id);
//        return mobileValidate;
//    }
}
