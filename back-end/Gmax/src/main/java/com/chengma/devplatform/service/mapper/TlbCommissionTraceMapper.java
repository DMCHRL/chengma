package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.TlbCommissionTrace;
import com.chengma.devplatform.service.dto.TlbCommissionTraceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TlbCommissionTraceMapper extends EntityMapper<TlbCommissionTraceDTO, TlbCommissionTrace> {
    
}
