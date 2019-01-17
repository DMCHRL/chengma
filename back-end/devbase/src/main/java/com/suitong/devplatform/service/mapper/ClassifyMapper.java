package com.suitong.devplatform.service.mapper;



import com.suitong.devplatform.domain.Classify;
import com.suitong.devplatform.service.dto.ClassifyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity SingleItemType and its DTO SingleItemTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassifyMapper extends EntityMapper <ClassifyDTO, Classify> {
    
    
    default Classify fromId(Long id) {
        if (id == null) {
            return null;
        }
        Classify classify = new Classify();
        classify.setId(id);
        return classify;
    }
}
