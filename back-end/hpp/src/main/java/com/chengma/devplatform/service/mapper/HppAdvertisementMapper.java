package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppAdvertisement;
import com.chengma.devplatform.domain.HppTrainApply;
import com.chengma.devplatform.service.dto.HppAdvertisementDTO;
import com.chengma.devplatform.service.dto.HppTrainApplyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppAdvertisementMapper extends EntityMapper<HppAdvertisementDTO, HppAdvertisement> {

}
