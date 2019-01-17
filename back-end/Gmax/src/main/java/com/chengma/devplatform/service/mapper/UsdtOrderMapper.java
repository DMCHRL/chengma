package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.UsdtOrder;
import com.chengma.devplatform.service.dto.UsdtOrderDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsdtOrderMapper extends EntityMapper<UsdtOrderDTO, UsdtOrder> {
    
}
