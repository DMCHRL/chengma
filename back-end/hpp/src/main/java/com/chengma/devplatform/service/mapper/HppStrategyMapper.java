package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppStrategy;
import com.chengma.devplatform.service.dto.HppStrategyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppStrategyMapper extends EntityMapper<HppStrategyDTO, HppStrategy> {

}
