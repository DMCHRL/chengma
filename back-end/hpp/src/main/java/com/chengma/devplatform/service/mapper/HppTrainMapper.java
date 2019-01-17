package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppTrain;
import com.chengma.devplatform.domain.HppTrainApply;
import com.chengma.devplatform.service.dto.HppTrainApplyDTO;
import com.chengma.devplatform.service.dto.HppTrainDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppTrainMapper extends EntityMapper<HppTrainDTO, HppTrain> {

}
