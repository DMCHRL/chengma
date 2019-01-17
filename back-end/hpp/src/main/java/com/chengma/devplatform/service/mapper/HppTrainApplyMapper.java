package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppStrategyTrade;
import com.chengma.devplatform.domain.HppTrainApply;
import com.chengma.devplatform.service.dto.HppStrategyTradeDTO;
import com.chengma.devplatform.service.dto.HppTrainApplyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppTrainApplyMapper extends EntityMapper<HppTrainApplyDTO, HppTrainApply> {

}
