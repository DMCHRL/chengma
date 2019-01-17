package com.chengma.devplatform.service.mapper;

import com.chengma.devplatform.domain.TlbMtPrice;
import com.chengma.devplatform.domain.TlbMtPriceHistory;
import com.chengma.devplatform.service.dto.TlbMtPriceDTO;
import com.chengma.devplatform.service.dto.TlbMtPriceHistoryDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity User and its DTO called TlbUserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbMtPriceHistoryMapper extends EntityMapper <TlbMtPriceHistoryDTO, TlbMtPriceHistory> {

}
