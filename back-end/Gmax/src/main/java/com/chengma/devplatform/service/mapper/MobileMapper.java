package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.Mobile;
import com.chengma.devplatform.service.dto.MobileDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Mobile and its DTO MobileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MobileMapper extends EntityMapper <MobileDTO, Mobile> {
    
//    default Mobile fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        Mobile mobileValidate = new Mobile();
//        mobileValidate.setId(id);
//        return mobileValidate;
//    }
}
