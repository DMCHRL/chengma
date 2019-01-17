package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.BindBank;
import com.chengma.devplatform.domain.HppLive;
import com.chengma.devplatform.service.dto.BindBankDTO;
import com.chengma.devplatform.service.dto.HppLiveDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity TranInfo and its DTO TranInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HppLiveMapper extends EntityMapper<HppLiveDTO, HppLive> {
    
}
