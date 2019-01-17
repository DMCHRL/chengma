package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppMobileValidate;
import com.chengma.devplatform.service.dto.HppMobileValidateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity MobileValidate and its DTO MobileValidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HppMobileValidateMapper extends EntityMapper <HppMobileValidateDTO, HppMobileValidate> {
    
//    default MobileValidate fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        MobileValidate mobileValidate = new MobileValidate();
//        mobileValidate.setId(id);
//        return mobileValidate;
//    }
}
