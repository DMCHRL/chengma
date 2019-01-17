package com.chengma.devplatform.service.mapper;


import com.chengma.devplatform.domain.HppIntegral;
import com.chengma.devplatform.domain.HppIntegralDetail;
import com.chengma.devplatform.service.dto.HppIntegralDTO;
import com.chengma.devplatform.service.dto.HppIntegralDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HppIntegralDetailMapper extends EntityMapper<HppIntegralDetailDTO, HppIntegralDetail> {

}
