package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppMobileValidate;
import com.chengma.devplatform.domain.MailValidate;
import com.chengma.devplatform.service.dto.HppMobileValidateDTO;
import com.chengma.devplatform.service.dto.MailValidateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity MobileValidate and its DTO MobileValidateDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MailValidateMapper extends EntityMapper <MailValidateDTO, MailValidate> {
    
//    default MobileValidate fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        MobileValidate mobileValidate = new MobileValidate();
//        mobileValidate.setId(id);
//        return mobileValidate;
//    }
}
