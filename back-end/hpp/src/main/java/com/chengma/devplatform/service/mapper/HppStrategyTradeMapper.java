package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppStrategyTrade;
import com.chengma.devplatform.service.dto.HppStrategyTradeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppStrategyTradeMapper extends EntityMapper<HppStrategyTradeDTO, HppStrategyTrade> {

}
