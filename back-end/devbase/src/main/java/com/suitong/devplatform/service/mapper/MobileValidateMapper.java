package com.suitong.devplatform.service.mapper;


import com.suitong.devplatform.domain.MobileValidate;
import com.suitong.devplatform.service.dto.MobileValidateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity MobileValidate and its DTO MobileValidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MobileValidateMapper extends EntityMapper <MobileValidateDTO, MobileValidate> {
    
    
    default MobileValidate fromId(Long id) {
        if (id == null) {
            return null;
        }
        MobileValidate mobileValidate = new MobileValidate();
        mobileValidate.setId(id);
        return mobileValidate;
    }
}
