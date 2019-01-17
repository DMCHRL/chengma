package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbUserFeedback;
import com.chengma.devplatform.service.dto.TlbUserFeedbackDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbUserFeedbackMapper extends EntityMapper <TlbUserFeedbackDTO, TlbUserFeedback> {

    /*default TlbUserFeedback fromId(String id) {
        if (id == null) {
            return null;
        }
        TlbUserFeedback tlbUserFeedback = new TlbUserFeedback();
        tlbUserFeedback.setId(id);
        return tlbUserFeedback;
    }*/
}
