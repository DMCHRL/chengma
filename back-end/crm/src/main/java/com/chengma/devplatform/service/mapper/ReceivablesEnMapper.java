package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.ReceivablesEn;
import com.chengma.devplatform.service.dto.ReceivablesEnDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReceivablesEnMapper extends EntityMapper <ReceivablesEnDTO, ReceivablesEn> {

}
