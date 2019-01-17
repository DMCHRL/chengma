package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppExchange;
import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.service.dto.HppExchangeDTO;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppIntegralMapper extends EntityMapper<HppIntegralDTO, HppIntegral> {

}
