package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.Classify;
import com.chengma.devplatform.service.dto.ClassifyDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity SingleItemType and its DTO SingleItemTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassifyMapper extends EntityMapper <ClassifyDTO, Classify> {
    
//    default Classify fromId(String id) {
//        if (id == null) {
//            return null;
//        }
//        Classify classify = new Classify();
//        classify.setId(id);
//        return classify;
//    }
}
