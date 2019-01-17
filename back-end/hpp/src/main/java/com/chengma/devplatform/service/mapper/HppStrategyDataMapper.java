package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppStrategyData;
import com.chengma.devplatform.service.dto.HppStrategyDataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppStrategyDataMapper extends EntityMapper<HppStrategyDataDTO, HppStrategyData> {

}
