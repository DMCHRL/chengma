package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.Mt4Configure;
import com.chengma.devplatform.service.dto.Mt4ConfigureDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Mt4ConfigureMapper extends EntityMapper <Mt4ConfigureDTO, Mt4Configure> {

}
