package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppStrategyOrder;
import com.chengma.devplatform.service.dto.HppStrategyOrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppStrategyOrderMapper extends EntityMapper<HppStrategyOrderDTO, HppStrategyOrder> {

}
