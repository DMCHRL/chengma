package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.DigitalCurrency;
import com.chengma.devplatform.service.dto.DigitalCurrencyDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DigitalCurrencyMapper extends EntityMapper<DigitalCurrencyDTO, DigitalCurrency> {

}
