package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.TlbEaGroup;
import com.chengma.devplatform.service.dto.TlbEaGroupDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbEaGroupDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbEaGroupMapper extends EntityMapper <TlbEaGroupDTO, TlbEaGroup> {
}
