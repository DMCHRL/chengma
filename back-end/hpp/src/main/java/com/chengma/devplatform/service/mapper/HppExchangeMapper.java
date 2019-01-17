package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppExchange;
import com.chengma.devplatform.service.dto.HppExchangeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppExchangeMapper extends EntityMapper<HppExchangeDTO, HppExchange> {

}
