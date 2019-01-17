package com.suitong.devplatform.service.mapper;

import com.suitong.devplatform.domain.TlbRate;
import com.suitong.devplatform.service.dto.TlbRateDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbRateDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbRateMapper extends EntityMapper<TlbRateDTO, TlbRate> {

    default TlbRate fromId(Long id) {
        if (id == null) {
            return null;
        }
        TlbRate tlbRate = new TlbRate();
        tlbRate.setId(id);
        return tlbRate;
    }
}
